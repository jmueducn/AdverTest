"""
Main orchestrator for AdverTest Python/SWE-Rebench pipeline.

Full augmentation loop per instance:
  clone repo → gen tests → gen mutants → run mutants → coverage →
  TC/MT enhance (up to 4 rounds) → build diff → save prediction

Parallel to generate.py / generate_40ablation.py in the Java pipeline.

Usage:
    python generate_tests.py                              # all 12 instances
    python generate_tests.py --instance docling-project__docling-2983
    python generate_tests.py -n 4                         # 4 parallel workers
"""
import argparse
import ast
import json
import logging
import os
import re
import subprocess
import sys
import time
from concurrent.futures import ThreadPoolExecutor, as_completed

from prompt import test_generation_prompt
from mutant import initial_generate_mutant_python, running_mutants_python
from enhance import tc_enhance_python, mt_enhance_python
from coverage_py import coverage_process_python
from diff_builder import build_diff, save_prediction
from validate import validate_prediction, check_syntax

# ---------------------------------------------------------------------------
# Paths
# ---------------------------------------------------------------------------
SCRIPT_DIR = os.path.dirname(os.path.abspath(__file__))
CONTEXTS_JSONL = os.path.join(
    SCRIPT_DIR, "data",
    "resolved_rebench_leaderboard_gpt54mini_2026_02_top30_contexts.jsonl"
)
DATASET_JSONL = os.path.join(
    SCRIPT_DIR, "data",
    "rebench_leaderboard_2026_02_top30.jsonl"
)
WORKDIRS = os.path.join(SCRIPT_DIR, "workdirs")
MUTANTS_RAW = os.path.join(SCRIPT_DIR, "Mutants", "raw")
MUTANTS_TESTED = os.path.join(SCRIPT_DIR, "Mutants", "tested")
PREDICTIONS = os.path.join(SCRIPT_DIR, "outputs", "predictions.jsonl")
LOG_FILE = os.path.join(SCRIPT_DIR, "logs", "generation.log")

# Convergence thresholds (same as Java pipeline)
MS_TARGET = 70.0
COV_TARGET = 95.0
MAX_ROUNDS = 4

# ---------------------------------------------------------------------------
# Logging
# ---------------------------------------------------------------------------
os.makedirs(os.path.join(SCRIPT_DIR, "logs"), exist_ok=True)
logging.basicConfig(
    level=logging.INFO,
    format="%(asctime)s - %(levelname)s - %(message)s",
    handlers=[
        logging.FileHandler(LOG_FILE, mode="a"),
        logging.StreamHandler(),
    ],
)
logger = logging.getLogger(__name__)


# ---------------------------------------------------------------------------
# LLM wrapper — thin OpenAI client (no langchain dependency)
# ---------------------------------------------------------------------------
def make_llm_call(model: str, api_key: str, base_url: str,
                  temperature: float = 0.7, max_tokens: int = 8192):
    """Return a callable that sends a prompt to the LLM and returns the text."""
    import openai

    client = openai.OpenAI(api_key=api_key, base_url=base_url)

    def call(prompt: str) -> str:
        for attempt in range(3):
            try:
                logger.debug("LLM call attempt %d (prompt %d chars)", attempt + 1, len(prompt))
                resp = client.chat.completions.create(
                    model=model,
                    messages=[{"role": "user", "content": prompt}],
                    temperature=temperature,
                    max_tokens=max_tokens,
                )
                text = resp.choices[0].message.content or ""
                finish = resp.choices[0].finish_reason if resp.choices else "unknown"
                logger.info("LLM response: %d chars (finish_reason=%s)", len(text), finish)
                if not text:
                    logger.warning("LLM returned empty content (finish_reason=%s, model=%s)",
                                   finish, getattr(resp, 'model', 'unknown'))
                return text
            except Exception as e:
                logger.warning("LLM call attempt %d failed: %s", attempt + 1, e)
                wait = min(2 ** attempt * 5, 30)  # 5s, 10s, 20s for free tier
                time.sleep(wait)
        return ""

    return call


# ---------------------------------------------------------------------------
# Data loading
# ---------------------------------------------------------------------------
def load_instances(contexts_path: str = CONTEXTS_JSONL,
                   dataset_path: str = DATASET_JSONL) -> list[dict]:
    """Load and merge context + dataset info for each instance."""
    contexts = {}
    with open(contexts_path) as f:
        for line in f:
            d = json.loads(line)
            contexts[d["instance_id"]] = d

    dataset = {}
    with open(dataset_path) as f:
        for line in f:
            d = json.loads(line)
            dataset[d["instance_id"]] = d

    instances = []
    for iid, ctx in contexts.items():
        ds = dataset.get(iid, {})
        merged = {**ctx, **ds}
        # Ensure we have context fields
        merged["context"] = ctx
        instances.append(merged)

    return instances


# ---------------------------------------------------------------------------
# Repo setup
# ---------------------------------------------------------------------------
def clone_repo(instance: dict, base_dir: str = WORKDIRS) -> str:
    """Clone the repo at base_commit, then apply golden_patch (fixed version).

    Same as Java pipeline: all test gen/mutation runs on fixed code.
    """
    iid = instance["instance_id"]
    repo = instance["repo"]
    base_commit = instance["base_commit"]
    golden_patch = instance.get("golden_patch", "")
    workdir = os.path.join(base_dir, iid)

    if os.path.isdir(workdir):
        # Already cloned — just reset
        subprocess.run(
            ["git", "checkout", base_commit, "--force"],
            cwd=workdir, capture_output=True, timeout=60,
        )
        subprocess.run(
            ["git", "clean", "-fdx", "-e", ".venv"],
            cwd=workdir, capture_output=True, timeout=60,
        )
        logger.info("[%s] Reset existing workdir", iid)
    else:
        os.makedirs(base_dir, exist_ok=True)
        url = f"https://github.com/{repo}.git"

        subprocess.run(
            ["git", "clone", url, workdir],
            capture_output=True, text=True, timeout=600,
        )
        subprocess.run(
            ["git", "checkout", base_commit],
            cwd=workdir, capture_output=True, timeout=60,
        )
        logger.info("[%s] Cloned %s @ %s", iid, repo, base_commit[:10])

    # Apply golden patch to get the fixed version
    if golden_patch:
        proc = subprocess.run(
            ["git", "apply", "-"],
            input=golden_patch, cwd=workdir,
            capture_output=True, text=True, timeout=60,
        )
        if proc.returncode != 0:
            logger.error("[%s] Failed to apply golden patch: %s", iid, proc.stderr[:300])
        else:
            logger.info("[%s] Applied golden patch (fixed version)", iid)

    return workdir


def install_project(instance: dict, workdir: str) -> str | None:
    """Create a virtualenv in the workdir and install the project + test deps.

    Returns the path to the venv's python binary, or None on failure.
    """
    iid = instance["instance_id"]
    venv_dir = os.path.join(workdir, ".venv")
    venv_python = os.path.join(venv_dir, "bin", "python")
    venv_pip = os.path.join(venv_dir, "bin", "pip")

    venv_exists = os.path.isfile(venv_python)

    if not venv_exists:
        try:
            logger.info("[%s] Creating venv at %s", iid, venv_dir)
            subprocess.run(
                [sys.executable, "-m", "venv", venv_dir, "--clear"],
                capture_output=True, text=True, timeout=120,
            )
        except subprocess.TimeoutExpired:
            logger.error("[%s] Venv creation timed out", iid)
            return None

    # Always install core test deps (fast, idempotent)
    # Note: do NOT install pytest-asyncio here — it can conflict with
    # project-pinned pytest versions (e.g., conan's FixtureDef issue)
    subprocess.run(
        f"{venv_pip} install pytest pytest-cov pytest-timeout pytest-xdist "
        f"--quiet 2>/dev/null",
        shell=True, capture_output=True, text=True, timeout=120,
    )

    # Always run pip install -e . (idempotent, ensures deps are present
    # even if a previous run's venv was incomplete)
    install_cmd = instance.get("install_config", {}).get(
        "install", "pip install -e ."
    )
    if install_cmd.startswith("pip "):
        install_cmd = venv_pip + install_cmd[3:]
    else:
        install_cmd = f"{venv_pip} install -e ."

    try:
        proc = subprocess.run(
            install_cmd, shell=True, cwd=workdir,
            capture_output=True, text=True, timeout=900,
        )
        if proc.returncode != 0:
            logger.warning("[%s] Full install failed (rc=%d): %s",
                           iid, proc.returncode, (proc.stderr or "")[-500:])
            # Try with --no-deps as fallback
            subprocess.run(
                f"{venv_pip} install -e . --no-deps --quiet",
                shell=True, cwd=workdir,
                capture_output=True, text=True, timeout=60,
            )
            # Then install deps from requirements files if available
            for req_file in ["requirements.txt", "requirements-dev.txt",
                             "requirements-test.txt"]:
                req_path = os.path.join(workdir, req_file)
                if os.path.exists(req_path):
                    logger.info("[%s] Installing from %s", iid, req_file)
                    subprocess.run(
                        f"{venv_pip} install -r {req_path} --quiet 2>/dev/null",
                        shell=True, cwd=workdir,
                        capture_output=True, text=True, timeout=300,
                    )
        else:
            logger.info("[%s] pip install -e . succeeded", iid)
    except subprocess.TimeoutExpired:
        logger.warning("[%s] Full install timed out, trying --no-deps", iid)
        subprocess.run(
            f"{venv_pip} install -e . --no-deps --quiet",
            shell=True, cwd=workdir,
            capture_output=True, text=True, timeout=60,
        )

    # Install test extras and dependency groups from pyproject.toml
    # This is best-effort — must not kill the pipeline on failure
    try:
        _install_test_deps(venv_pip, workdir, iid)
    except Exception as e:
        logger.warning("[%s] _install_test_deps failed (non-fatal): %s", iid, e)

    logger.info("[%s] Venv ready: %s", iid, venv_python)
    return venv_python


def _install_test_deps(venv_pip: str, workdir: str, iid: str):
    """Try installing test/dev extras and dependency groups from pyproject.toml."""
    pyproject = os.path.join(workdir, "pyproject.toml")
    if not os.path.exists(pyproject):
        return

    try:
        import tomllib
    except ImportError:
        try:
            import tomli as tomllib
        except ImportError:
            return

    try:
        with open(pyproject, "rb") as f:
            t = tomllib.load(f)
    except Exception:
        return

    # 1. Try optional-dependencies extras (e.g., [test], [dev])
    extras = t.get("project", {}).get("optional-dependencies", {})
    test_extras = [k for k in extras if k.lower() in (
        "test", "testing", "tests", "dev", "all",
    )]
    if test_extras:
        extras_str = ",".join(test_extras)
        proc = subprocess.run(
            f'{venv_pip} install -e ".[{extras_str}]" --quiet 2>/dev/null',
            shell=True, cwd=workdir,
            capture_output=True, text=True, timeout=300,
        )
        if proc.returncode == 0:
            logger.info("[%s] Installed extras: [%s]", iid, extras_str)
        else:
            # Try each extra individually
            for ext in test_extras:
                subprocess.run(
                    f'{venv_pip} install -e ".[{ext}]" --quiet 2>/dev/null',
                    shell=True, cwd=workdir,
                    capture_output=True, text=True, timeout=300,
                )

    # 2. Try dependency-groups (PEP 735 — e.g., [dependency-groups.dev])
    dep_groups = t.get("dependency-groups", {})
    test_groups = [k for k in dep_groups if k.lower() in ("test", "dev", "testing")]
    for group in test_groups:
        deps = []
        for item in dep_groups[group]:
            if isinstance(item, str):
                deps.append(item)
            elif isinstance(item, dict) and "include-group" in item:
                # Resolve included groups
                included = dep_groups.get(item["include-group"], [])
                deps.extend(d for d in included if isinstance(d, str))
        if deps:
            # Filter out path-based deps and keep only PyPI packages
            pip_deps = [d for d in deps if not d.startswith(".") and "://" not in d]
            if pip_deps:
                cmd = f'{venv_pip} install {" ".join(pip_deps)} --quiet 2>/dev/null'
                proc = subprocess.run(
                    cmd, shell=True, cwd=workdir,
                    capture_output=True, text=True, timeout=300,
                )
                if proc.returncode == 0:
                    logger.info("[%s] Installed dep-group [%s]: %d packages",
                                iid, group, len(pip_deps))

    # 3. Install common pytest plugins that projects often need
    subprocess.run(
        f"{venv_pip} install pytest-django pytest-mock --quiet 2>/dev/null",
        shell=True, capture_output=True, text=True, timeout=60,
    )


# ---------------------------------------------------------------------------
# Source extraction
# ---------------------------------------------------------------------------
def extract_bug_location(instance: dict, workdir: str) -> tuple[str, str, list[int]]:
    """
    Extract source file, code, and target line range from bug_location_code.

    Returns (rel_source_path, source_code, target_lines).
    target_lines covers the enclosing function(s) that contain the hunk lines,
    matching the Java pipeline which sends the entire target method.
    """
    blc = instance.get("bug_location_code", [])
    if not blc:
        raise ValueError(f"No bug_location_code for {instance['instance_id']}")

    # Use first file's location
    loc = blc[0]
    file_path = loc["file_path"]
    abs_path = os.path.join(workdir, file_path)

    with open(abs_path) as f:
        source_code = f.read()

    source_lines = source_code.splitlines()

    # Collect hunk lines from patch
    hunk_lines = set()
    for hunk in loc.get("hunks", []):
        start = hunk["old_start"]
        count = hunk["old_count"]
        hunk_lines.update(range(start, start + count))

    if not hunk_lines:
        # Fallback: all lines
        return file_path, source_code, list(range(1, len(source_lines) + 1))

    # Expand each hunk line to cover its enclosing function/method
    # (mirrors Java pipeline which sends the full target method)
    target_lines = set()
    for hunk_line in hunk_lines:
        func_start, func_end = _find_enclosing_function(source_lines, hunk_line)
        target_lines.update(range(func_start, func_end + 1))

    return file_path, source_code, sorted(target_lines)


def _find_enclosing_function(source_lines: list[str], line_no: int) -> tuple[int, int]:
    """
    Find the start and end line numbers (1-indexed) of the function/method
    enclosing the given line. Falls back to a context window if no function found.
    """
    # Search backwards for 'def ' at same or lower indentation
    target_indent = len(source_lines[line_no - 1]) - len(source_lines[line_no - 1].lstrip())
    func_start = line_no

    for i in range(line_no - 1, -1, -1):
        stripped = source_lines[i].strip()
        if not stripped or stripped.startswith("#"):
            continue
        line_indent = len(source_lines[i]) - len(source_lines[i].lstrip())
        if stripped.startswith("def ") or stripped.startswith("async def "):
            func_start = i + 1  # 1-indexed
            target_indent = line_indent
            break
        # Also accept class-level if we hit it
        if stripped.startswith("class ") and line_indent < target_indent:
            func_start = i + 1
            target_indent = line_indent
            break

    # Search forwards for the end of the function (next def/class at same or lower indent)
    func_end = len(source_lines)
    for i in range(line_no, len(source_lines)):
        stripped = source_lines[i].strip()
        if not stripped or stripped.startswith("#"):
            continue
        line_indent = len(source_lines[i]) - len(source_lines[i].lstrip())
        if i > line_no - 1 and line_indent <= target_indent:
            if stripped.startswith(("def ", "async def ", "class ")):
                func_end = i  # 1-indexed (exclusive)
                break

    return func_start, func_end


def extract_module_context(instance: dict) -> str:
    """Pull relevant context from context_code outputs."""
    parts = []
    for cc in instance.get("context_code", []):
        out = cc.get("output", "")
        if out:
            parts.append(out[:2000])
    return "\n...\n".join(parts)[:4000]


# ---------------------------------------------------------------------------
# Initial test generation
# ---------------------------------------------------------------------------
def initial_generate_testcase_python(
    instance_id: str,
    source_file: str,
    source_code: str,
    module_context: str,
    llm_call,
    workdir: str,
    test_rel_path: str = "tests/test_advertest_generated.py",
    python_path: str = "python",
) -> str | None:
    """
    Generate initial test file via LLM. Validates syntax and that tests run.

    Returns the absolute path to the test file, or None on failure.
    """
    prompt = test_generation_prompt(source_code, source_file, module_context)
    logger.info("[%s] Test gen: calling LLM (prompt %d chars)", instance_id, len(prompt))
    response = llm_call(prompt)

    code = _extract_code_block(response)
    if not code:
        logger.error("[%s] LLM returned no code block for test gen (response %d chars)", instance_id, len(response))
        return None

    logger.info("[%s] Test gen: extracted %d chars, checking syntax", instance_id, len(code))
    ok, err = check_syntax(code)
    if not ok:
        logger.warning("[%s] Test gen syntax error: %s", instance_id, err)
        # Try once more with error feedback
        retry_prompt = (
            prompt + f"\n\nYour previous response had a syntax error:\n{err}\n"
            "Please fix and return the corrected code."
        )
        response = llm_call(retry_prompt)
        code = _extract_code_block(response)
        if not code:
            return None
        ok, err = check_syntax(code)
        if not ok:
            logger.error("[%s] Test gen still has syntax error: %s", instance_id, err)
            return None

    test_abs = os.path.join(workdir, test_rel_path)
    os.makedirs(os.path.dirname(test_abs), exist_ok=True)
    with open(test_abs, "w") as f:
        f.write(code)

    # Verify tests actually run (some may fail, that's OK — we just need no import errors)
    logger.info("[%s] Test gen: verifying test collection", instance_id)
    collect_cmd = [python_path, "-m", "pytest", test_abs, "--co", "-q",
                   "-o", "addopts=", "--no-header"]
    proc = subprocess.run(
        collect_cmd,
        cwd=workdir, capture_output=True, text=True, timeout=120,
    )
    combined = (proc.stderr or "") + "\n" + (proc.stdout or "")
    collection_failed = _check_collection_failed(proc.returncode, combined)

    # --- Fix 1: auto-install missing modules ---
    if collection_failed:
        missing = _extract_missing_module(combined)
        if missing:
            venv_pip = os.path.join(os.path.dirname(python_path), "pip")
            logger.info("[%s] Attempting to install missing module: %s", instance_id, missing)
            try:
                subprocess.run(
                    f"{venv_pip} install {missing} --quiet 2>/dev/null",
                    shell=True, cwd=workdir,
                    capture_output=True, text=True, timeout=300,
                )
            except subprocess.TimeoutExpired:
                logger.warning("[%s] Install of %s timed out", instance_id, missing)
            proc = subprocess.run(
                collect_cmd,
                cwd=workdir, capture_output=True, text=True, timeout=120,
            )
            combined = (proc.stderr or "") + "\n" + (proc.stdout or "")
            collection_failed = _check_collection_failed(proc.returncode, combined)
            # Keep installing if more modules are missing (chain imports)
            for _ in range(5):
                if not collection_failed:
                    break
                missing2 = _extract_missing_module(combined)
                if not missing2 or missing2 == missing:
                    break
                missing = missing2
                logger.info("[%s] Installing another missing module: %s", instance_id, missing)
                try:
                    subprocess.run(
                        f"{venv_pip} install {missing} --quiet 2>/dev/null",
                        shell=True, cwd=workdir,
                        capture_output=True, text=True, timeout=300,
                    )
                except subprocess.TimeoutExpired:
                    logger.warning("[%s] Install of %s timed out", instance_id, missing)
                    break
                proc = subprocess.run(
                    collect_cmd,
                    cwd=workdir, capture_output=True, text=True, timeout=120,
                )
                combined = (proc.stderr or "") + "\n" + (proc.stdout or "")
                collection_failed = _check_collection_failed(proc.returncode, combined)

    # --- Fix 2: try --noconftest if conftest causes the import error ---
    if collection_failed and "conftest" in combined.lower():
        logger.info("[%s] Trying --noconftest to bypass conftest imports", instance_id)
        noconftest_cmd = collect_cmd + ["--noconftest"]
        proc_nc = subprocess.run(
            noconftest_cmd,
            cwd=workdir, capture_output=True, text=True, timeout=120,
        )
        combined_nc = (proc_nc.stderr or "") + "\n" + (proc_nc.stdout or "")
        if not _check_collection_failed(proc_nc.returncode, combined_nc):
            logger.info("[%s] Collection OK with --noconftest", instance_id)
            # Mark that this instance needs --noconftest for all future pytest calls
            _noconftest_flag = os.path.join(workdir, ".noconftest")
            with open(_noconftest_flag, "w") as f:
                f.write("1")
            collection_failed = False

    if collection_failed:
        # Collection failed — try LLM-based fix once
        logger.warning(
            "[%s] Test collection failed (rc=%d), attempting LLM fix",
            instance_id, proc.returncode,
        )
        fix_prompt = (
            prompt
            + f"\n\nYour previous response caused a collection/import error "
            f"when running pytest:\n```\n{combined[-1500:]}\n```\n"
            "Please fix and return the corrected complete test file. "
            "IMPORTANT: If the error is an ImportError/ModuleNotFoundError, "
            "import ONLY the specific function or class you need to test, "
            "not the full package. You can use importlib to load a specific file."
        )
        fix_response = llm_call(fix_prompt)
        fix_code = _extract_code_block(fix_response)
        if fix_code:
            fix_ok, fix_err = check_syntax(fix_code)
            if fix_ok:
                with open(test_abs, "w") as f:
                    f.write(fix_code)
                proc2 = subprocess.run(
                    collect_cmd,
                    cwd=workdir, capture_output=True, text=True, timeout=120,
                )
                combined2 = (proc2.stderr or "") + "\n" + (proc2.stdout or "")
                still_failed = _check_collection_failed(proc2.returncode, combined2)
                if still_failed:
                    logger.warning(
                        "[%s] Test collection still fails after LLM fix (rc=%d)",
                        instance_id, proc2.returncode,
                    )
                    return None
                logger.info("[%s] Test collection fixed by LLM retry", instance_id)
            else:
                logger.warning("[%s] LLM fix has syntax error: %s", instance_id, fix_err)
                return None
        else:
            logger.warning("[%s] LLM fix returned no code block", instance_id)
            return None

    logger.info("[%s] Initial test gen: %s", instance_id, test_abs)
    return test_abs


def _check_collection_failed(returncode: int, combined: str) -> bool:
    """Check if pytest collection failed based on return code and output."""
    return (
        returncode >= 2
        or "ImportError" in combined
        or "ModuleNotFoundError" in combined
        or "PackageNotFoundError" in combined
        or "unrecognized arguments" in combined
    )


def _extract_missing_module(combined: str) -> str | None:
    """Extract the top-level missing module name from pytest error output."""
    import re as _re
    m = _re.search(r"(?:ModuleNotFoundError|ImportError): No module named ['\"]?([a-zA-Z0-9_]+)", combined)
    if m:
        return m.group(1)
    return None


def _extract_code_block(response: str) -> str:
    m = re.search(r"```python\s*\n(.*?)```", response, re.DOTALL)
    if m:
        return m.group(1).strip()
    m = re.search(r"```\s*\n(.*?)```", response, re.DOTALL)
    if m:
        return m.group(1).strip()
    # If no code block, try the whole response
    if "def test_" in response:
        return response.strip()
    return ""


# ---------------------------------------------------------------------------
# Main pipeline per instance
# ---------------------------------------------------------------------------
def run_one_instance(instance: dict, llm_call, skip_clone: bool = False) -> dict:
    """
    Full AdverTest pipeline for one SWE-Rebench instance.

    Returns a result dict with metrics.
    """
    iid = instance["instance_id"]
    logger.info("=" * 60)
    logger.info("[%s] Starting pipeline", iid)
    start = time.time()

    result = {
        "instance_id": iid,
        "success": False,
        "mutation_score": 0.0,
        "line_coverage": 0.0,
        "rounds": 0,
        "tests_added": 0,
        "mutants_added": 0,
        "bug_detected": False,
        "error": None,
    }

    test_rel_path = "tests/test_advertest_generated.py"

    try:
        # 1. Clone + install (always install — venv is reused if it exists)
        if not skip_clone:
            workdir = clone_repo(instance)
        else:
            workdir = os.path.join(WORKDIRS, iid)

        python_path = install_project(instance, workdir)
        if not python_path:
            result["error"] = "install_failed"
            return result

        # 2. Extract source
        source_file, source_code, target_lines = extract_bug_location(instance, workdir)
        module_context = extract_module_context(instance)
        abs_source = os.path.join(workdir, source_file)

        # 3. Initial test generation (retry up to 3 times, matching Java pipeline)
        test_file = None
        for _tc_attempt in range(3):
            test_file = initial_generate_testcase_python(
                iid, source_file, source_code, module_context, llm_call, workdir,
                test_rel_path=test_rel_path,
                python_path=python_path,
            )
            if test_file:
                break
            logger.warning("[%s] Test gen attempt %d/3 failed, retrying",
                           iid, _tc_attempt + 1)
        if not test_file:
            result["error"] = "test_gen_failed"
            return result

        # Helper: run bug detection and record the stage if first detected
        detection_history = []

        def _check_bug_detection(stage: str):
            det = bug_detection_python(
                instance, workdir, test_file, python_path=python_path,
            )
            detection_history.append({
                "stage": stage,
                "detected": det["detected"],
                "fixed_fails": len(det["fixed_fails"]),
                "buggy_fails": len(det["buggy_fails"]),
                "new_failures": sorted(
                    set(det["buggy_fails"]) - set(det["fixed_fails"])
                ),
            })
            logger.info(
                "[%s] Bug detection @ %s: detected=%s (fixed_fails=%d, buggy_fails=%d, new=%d)",
                iid, stage, det["detected"],
                len(det["fixed_fails"]), len(det["buggy_fails"]),
                len(set(det["buggy_fails"]) - set(det["fixed_fails"])),
            )

        # Check after initial test generation
        _check_bug_detection("initial_testgen")

        # 4. Initial mutant generation
        t_mutgen_start = time.time()
        mutant_path = initial_generate_mutant_python(
            iid, source_file, source_code, target_lines, llm_call,
            output_dir=MUTANTS_RAW,
        )
        logger.info("[%s] Initial mutant gen: %.1fs", iid, time.time() - t_mutgen_start)
        if not mutant_path:
            result["error"] = "mutant_gen_failed"
            # Still save the test diff even without mutants
            _finalize(iid, test_file, test_rel_path, workdir, result)
            return result

        # 5. Run mutants
        ms, killed, total = running_mutants_python(
            iid, workdir, test_file, mutant_path,
            tested_output_dir=MUTANTS_TESTED,
            python_path=python_path,
        )
        result["mutation_score"] = ms

        # 6. Coverage
        uncovered, line_cov = coverage_process_python(
            workdir, test_file, abs_source, target_lines,
            python_path=python_path,
        )
        result["line_coverage"] = line_cov

        # 7. Iterative enhancement (TC → MT → TC → MT)
        #    Java pipeline intentionally runs all 4 rounds — no early convergence.
        tested_path = os.path.join(MUTANTS_TESTED, f"{iid}.json")
        cnt_T, cnt_M = 0, 0

        while cnt_T + cnt_M < MAX_ROUNDS:
            # No early convergence break — always run all rounds (matching Java)

            t_round_start = time.time()
            if cnt_T <= cnt_M:
                # TC_ENHANCE — new tests for survived mutants
                added = tc_enhance_python(
                    iid, test_file, tested_path, source_file,
                    source_code, llm_call, workdir,
                    python_path=python_path,
                )
                cnt_T += 1
                result["tests_added"] += added
                logger.info("[%s] TC_ENHANCE round %d: +%d tests (%.1fs)",
                            iid, cnt_T, added, time.time() - t_round_start)
            else:
                # MT_ENHANCE — new mutants on uncovered lines + survived lines
                logger.info("[%s] MT_ENHANCE starting: %d uncovered lines, mutant_path=%s",
                            iid, len(uncovered), mutant_path)
                added = mt_enhance_python(
                    iid, source_code, source_file, uncovered,
                    mutant_path, tested_path, llm_call,
                    target_lines=target_lines,
                )
                cnt_M += 1
                result["mutants_added"] += added
                logger.info("[%s] MT_ENHANCE round %d: +%d mutants (%.1fs)",
                            iid, cnt_M, added, time.time() - t_round_start)

            # Re-run mutation testing
            ms, killed, total = running_mutants_python(
                iid, workdir, test_file, mutant_path,
                tested_output_dir=MUTANTS_TESTED,
                python_path=python_path,
            )
            result["mutation_score"] = ms

            # Re-run coverage
            uncovered, line_cov = coverage_process_python(
                workdir, test_file, abs_source, target_lines,
                python_path=python_path,
            )
            result["line_coverage"] = line_cov

            logger.info(
                "[%s] Round %d — MS=%.1f%% Cov=%.1f%% TC=%d MT=%d",
                iid, cnt_T + cnt_M, ms, line_cov, cnt_T, cnt_M,
            )

            # Check bug detection after each round
            _check_bug_detection(f"round_{cnt_T + cnt_M}_TC{cnt_T}_MT{cnt_M}")

        # Bonus TC round if last step was MT (matching Java generate.py lines 93-101)
        if cnt_T < cnt_M:
            logger.info("[%s] Bonus TC round (last step was MT)", iid)
            added = tc_enhance_python(
                iid, test_file, tested_path, source_file,
                source_code, llm_call, workdir,
                python_path=python_path,
            )
            cnt_T += 1
            result["tests_added"] += added

            ms, killed, total = running_mutants_python(
                iid, workdir, test_file, mutant_path,
                tested_output_dir=MUTANTS_TESTED,
                python_path=python_path,
            )
            result["mutation_score"] = ms

            uncovered, line_cov = coverage_process_python(
                workdir, test_file, abs_source, target_lines,
                python_path=python_path,
            )
            result["line_coverage"] = line_cov

            logger.info(
                "[%s] Bonus TC round — MS=%.1f%% Cov=%.1f%% TC=%d MT=%d",
                iid, ms, line_cov, cnt_T, cnt_M,
            )

        result["rounds"] = cnt_T + cnt_M

        # 8. Determine bug detection results from history
        # Final detection = last entry in history
        result["bug_detected"] = detection_history[-1]["detected"]
        # Find earliest stage where bug was detected
        first_detected = None
        for entry in detection_history:
            if entry["detected"]:
                first_detected = entry["stage"]
                break
        result["first_detected_at"] = first_detected
        result["detection_history"] = detection_history
        logger.info(
            "[%s] Bug detection summary: detected=%s, first_at=%s, stages=%s",
            iid, result["bug_detected"], first_detected,
            [(h["stage"], h["detected"]) for h in detection_history],
        )

        # 9. Finalize: build diff and save prediction
        _finalize(iid, test_file, test_rel_path, workdir, result)
        result["success"] = True

    except Exception as e:
        logger.exception("[%s] Pipeline error: %s", iid, e)
        result["error"] = str(e)

    elapsed = time.time() - start
    logger.info(
        "[%s] Done in %.0fs — MS=%.1f%% Cov=%.1f%% rounds=%d bug_detected=%s success=%s",
        iid, elapsed, result["mutation_score"], result["line_coverage"],
        result["rounds"], result.get("bug_detected"), result["success"],
    )
    return result


# ---------------------------------------------------------------------------
# Bug detection — run tests on fixed vs buggy version
# ---------------------------------------------------------------------------
def bug_detection_python(
    instance: dict,
    workdir: str,
    test_file: str,
    python_path: str = "python",
    timeout: int = 120,
) -> dict:
    """
    Run generated tests on the fixed version (current state) and the buggy
    version (golden_patch reverted).  Compare results to determine if the
    tests can detect the bug.

    Mirrors test.py:bug_detection_ourgen() from the Java pipeline.

    Returns dict with keys: detected (bool), fixed_fails, buggy_fails,
    fixed_rc, buggy_rc, error.
    """
    iid = instance["instance_id"]
    golden_patch = instance.get("golden_patch", "")
    result = {
        "detected": False,
        "fixed_fails": [],
        "buggy_fails": [],
        "fixed_rc": -1,
        "buggy_rc": -1,
        "error": None,
    }

    test_cmd = [
        python_path, "-m", "pytest",
        test_file,
        "--tb=short", "-q", "--color=no",
        "-o", "addopts=",
        "--no-header",
        f"--timeout={timeout}",
    ]
    # If this instance needs --noconftest (detected during collection)
    noconftest_flag = os.path.join(workdir, ".noconftest")
    if os.path.exists(noconftest_flag):
        test_cmd.append("--noconftest")

    # --- 1. Run tests on FIXED version (current state) ---
    try:
        fixed_proc = subprocess.run(
            test_cmd, cwd=workdir,
            capture_output=True, text=True,
            timeout=timeout + 30,
        )
        result["fixed_rc"] = fixed_proc.returncode
        fixed_output = fixed_proc.stdout or ""
    except subprocess.TimeoutExpired:
        result["error"] = "fixed_timeout"
        logger.error("[%s] Bug detection: fixed version timed out", iid)
        return result

    # --- 2. Revert golden_patch to get BUGGY version ---
    if not golden_patch:
        result["error"] = "no_golden_patch"
        logger.warning("[%s] Bug detection: no golden_patch to revert", iid)
        return result

    revert = subprocess.run(
        ["git", "apply", "-R", "-"],
        input=golden_patch, cwd=workdir,
        capture_output=True, text=True, timeout=30,
    )
    if revert.returncode != 0:
        result["error"] = f"revert_failed: {revert.stderr[:200]}"
        logger.error("[%s] Bug detection: failed to revert golden patch: %s",
                     iid, revert.stderr[:200])
        return result

    # --- 3. Run tests on BUGGY version ---
    try:
        buggy_proc = subprocess.run(
            test_cmd, cwd=workdir,
            capture_output=True, text=True,
            timeout=timeout + 30,
        )
        result["buggy_rc"] = buggy_proc.returncode
        buggy_output = buggy_proc.stdout or ""
    except subprocess.TimeoutExpired:
        buggy_output = ""
        result["buggy_rc"] = -1
        logger.warning("[%s] Bug detection: buggy version timed out", iid)
    finally:
        # --- 4. Re-apply golden_patch to restore FIXED version ---
        restore = subprocess.run(
            ["git", "apply", "-"],
            input=golden_patch, cwd=workdir,
            capture_output=True, text=True, timeout=30,
        )
        if restore.returncode != 0:
            logger.error("[%s] Bug detection: CRITICAL — failed to restore golden patch: %s",
                         iid, restore.stderr[:200])

    # --- 5. Compare results ---
    import re
    def _strip_ansi(s):
        return re.sub(r"\x1b\[[0-9;]*m", "", s)

    def _extract_fails(output):
        fails = set()
        for line in output.splitlines():
            s = _strip_ansi(line).strip()
            if s.startswith("FAILED "):
                test_id = s[7:].split(" - ")[0].strip()
                fails.add(test_id)
            elif s.startswith("ERROR "):
                fails.add("ERROR:" + s[6:].strip())
        return sorted(fails)

    result["fixed_fails"] = _extract_fails(fixed_output)
    result["buggy_fails"] = _extract_fails(buggy_output)

    fixed_set = set(result["fixed_fails"])
    buggy_set = set(result["buggy_fails"])

    # Bug detected if:
    # - buggy has MORE/DIFFERENT failures than fixed
    # - OR buggy crashes (rc>=2) while fixed doesn't
    # - OR buggy times out while fixed doesn't
    if result["buggy_rc"] == -1 and result["fixed_rc"] != -1:
        # Buggy timed out, fixed didn't → detected
        result["detected"] = True
    elif result["buggy_rc"] >= 2 and result["fixed_rc"] < 2:
        # Buggy has collection error, fixed doesn't → detected
        result["detected"] = True
    elif buggy_set != fixed_set:
        # Different set of failing tests → detected
        result["detected"] = True

    new_failures = sorted(buggy_set - fixed_set)
    logger.info(
        "[%s] Bug detection: detected=%s fixed_rc=%d buggy_rc=%d "
        "fixed_fails=%d buggy_fails=%d new_failures=%s",
        iid, result["detected"], result["fixed_rc"], result["buggy_rc"],
        len(result["fixed_fails"]), len(result["buggy_fails"]),
        new_failures[:5] if new_failures else "none",
    )
    return result


def _finalize(iid, test_file, test_rel_path, workdir, result):
    """Build diff, validate, and save prediction."""
    with open(test_file) as f:
        test_code = f.read()

    diff = build_diff(test_code, test_rel_path)
    validation = validate_prediction(test_code, diff, workdir)

    if not validation["all_ok"]:
        logger.warning("[%s] Validation issues: %s", iid, validation)

    save_prediction(iid, diff, output_path=PREDICTIONS)
    logger.info("[%s] Prediction saved to %s", iid, PREDICTIONS)


# ---------------------------------------------------------------------------
# CLI
# ---------------------------------------------------------------------------
def main():
    parser = argparse.ArgumentParser(
        description="AdverTest Python/SWE-Rebench pipeline"
    )
    parser.add_argument("-n", "--workers", type=int, default=1,
                        help="Number of parallel workers (default: 1)")
    parser.add_argument("--instance", type=str, default=None,
                        help="Run only this instance ID")
    parser.add_argument("--skip-clone", action="store_true",
                        help="Skip cloning (use existing workdirs)")
    parser.add_argument("--model", default="deepseek-chat",
                        help="LLM model name (e.g., deepseek-chat, gpt-4)")
    parser.add_argument("--api-key", default=None,
                        help="API key (or set LLM_API_KEY env var)")
    parser.add_argument("--base-url", default=None,
                        help="LLM API base URL (or set LLM_BASE_URL env var)")
    args = parser.parse_args()

    # Clear previous predictions if running all
    if not args.instance and os.path.exists(PREDICTIONS):
        os.remove(PREDICTIONS)

    model = args.model
    api_key = args.api_key or os.getenv("LLM_API_KEY", "")
    base_url = args.base_url or os.getenv("LLM_BASE_URL", "https://api.deepseek.com")

    llm_call = make_llm_call(model, api_key, base_url)

    instances = load_instances()
    if args.instance:
        instances = [i for i in instances if i["instance_id"] == args.instance]
        if not instances:
            logger.error("Instance %s not found", args.instance)
            sys.exit(1)

    logger.info("Running %d instances with %d worker(s)", len(instances), args.workers)

    if args.workers <= 1:
        results = []
        for inst in instances:
            r = run_one_instance(inst, llm_call, skip_clone=args.skip_clone)
            results.append(r)
    else:
        results = []
        with ThreadPoolExecutor(max_workers=args.workers) as pool:
            futures = {
                pool.submit(run_one_instance, inst, llm_call, args.skip_clone): inst
                for inst in instances
            }
            for fut in as_completed(futures):
                results.append(fut.result())

    # Summary
    logger.info("=" * 60)
    logger.info("SUMMARY")
    logger.info("=" * 60)
    success = sum(1 for r in results if r["success"])
    for r in results:
        logger.info(
            "  %s — success=%s MS=%.1f%% Cov=%.1f%% rounds=%d bug=%s error=%s",
            r["instance_id"], r["success"], r["mutation_score"],
            r["line_coverage"], r["rounds"], r.get("bug_detected"), r["error"],
        )
    logger.info("Total: %d/%d succeeded", success, len(results))

    # Save results JSON (merge with existing if running single instance)
    results_path = os.path.join(SCRIPT_DIR, "outputs", "results.json")
    os.makedirs(os.path.dirname(results_path), exist_ok=True)
    if args.instance and os.path.exists(results_path):
        with open(results_path) as f:
            existing_results = json.load(f)
        # Replace matching instance, keep others
        new_ids = {r["instance_id"] for r in results}
        merged = [r for r in existing_results if r["instance_id"] not in new_ids]
        merged.extend(results)
        results = merged
    with open(results_path, "w") as f:
        json.dump(results, f, indent=2)
    logger.info("Results saved to %s", results_path)


if __name__ == "__main__":
    main()

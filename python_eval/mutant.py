"""
Mutant generation (via LLM) and mutation testing (apply + pytest) for Python.

Parallel to function.py:initial_generate_mutant_def4j and function.py:running_mutants.
"""
import json
import os
import re
import subprocess
import logging
import copy

from prompt import mutant_generation_prompt

logger = logging.getLogger(__name__)


# ---------------------------------------------------------------------------
# LLM mutant generation
# ---------------------------------------------------------------------------
def initial_generate_mutant_python(
    instance_id: str,
    source_file: str,
    source_code: str,
    target_lines: list[int] | None,
    llm_call,
    output_dir: str = "Mutants/raw",
    num_mutants: int = 20,
) -> str | None:
    """
    Ask the LLM to generate mutants for *source_code*, validate them,
    and save to ``<output_dir>/<instance_id>.json``.

    Parameters
    ----------
    llm_call : callable(str) -> str
        A function that sends a prompt string to the LLM and returns the response.
    """
    prompt = mutant_generation_prompt(
        source_code, source_file, target_lines, num_mutants
    )

    mutants = []
    for attempt in range(3):
        response = llm_call(prompt)
        mutants = _parse_mutant_json(response, source_code, source_file)
        if mutants:
            break
        logger.warning(
            "[%s] Mutant gen attempt %d/3: 0 valid mutants, retrying",
            instance_id, attempt + 1,
        )

    if not mutants:
        logger.warning("[%s] LLM returned 0 valid mutants after 3 attempts", instance_id)
        return None

    os.makedirs(output_dir, exist_ok=True)
    out_path = os.path.join(output_dir, f"{instance_id}.json")
    with open(out_path, "w") as f:
        json.dump(mutants, f, indent=2, ensure_ascii=False)

    logger.info("[%s] Generated %d mutants -> %s", instance_id, len(mutants), out_path)
    return out_path


def _parse_mutant_json(response: str, source_code: str, file_path: str) -> list[dict]:
    """Extract and validate mutant JSON from LLM response."""
    # Try to find JSON array in the response
    text = response.strip()

    # Strip markdown fences
    m = re.search(r"```(?:json)?\s*(\[.*?\])\s*```", text, re.DOTALL)
    if m:
        text = m.group(1)
    else:
        # Try bare JSON array
        start = text.find("[")
        end = text.rfind("]")
        if start != -1 and end != -1:
            text = text[start : end + 1]

    try:
        raw = json.loads(text)
    except json.JSONDecodeError:
        logger.error("Failed to parse mutant JSON from LLM response")
        return []

    if not isinstance(raw, list):
        return []

    source_lines = source_code.splitlines()
    valid = []
    for item in raw:
        if not isinstance(item, dict):
            continue
        line = item.get("line")
        pre = item.get("precode", "")
        after = item.get("aftercode", "")
        if not (line and pre and after):
            continue
        if not isinstance(line, int) or line < 1:
            continue
        if pre.strip() == after.strip():
            continue  # equivalent mutant

        # --- Precode line correction (matches Java pipeline) ---
        # First check the LLM-reported line number
        corrected = False
        if line <= len(source_lines):
            actual = source_lines[line - 1]
            if pre.strip() in actual:
                corrected = True

        # If precode doesn't match at reported line, scan ALL source lines
        if not corrected:
            found = False
            for k in range(len(source_lines)):
                if pre.strip() in source_lines[k]:
                    item["line"] = k + 1  # update to actual matching line
                    found = True
                    break
            if not found:
                # Precode not found anywhere in source — discard mutant
                continue

        item["filepath"] = file_path
        valid.append(item)

    return valid


def _fuzzy_match(a: str, b: str) -> bool:
    """Check if strings share >50% of tokens."""
    ta = set(a.split())
    tb = set(b.split())
    if not ta or not tb:
        return False
    return len(ta & tb) / min(len(ta), len(tb)) > 0.5


def _strip_ansi(s: str) -> str:
    """Remove ANSI escape sequences from a string."""
    return re.sub(r"\x1b\[[0-9;]*m", "", s)


def _extract_failed_tests(output: str) -> frozenset[str]:
    """Extract the set of FAILED test names from pytest output.

    Matches lines like:
        FAILED tests/test_foo.py::test_bar - AssertionError
        ERROR tests/test_foo.py::test_baz

    Returns a frozenset of test identifiers (comparable, order-independent).
    This mirrors the Java pipeline which compares fail_test sets, not raw output.
    """
    if not output or output.startswith("__"):
        return frozenset(["__SPECIAL__:" + output[:100]])

    fails = set()
    for line in output.splitlines():
        stripped = _strip_ansi(line).strip()
        if stripped.startswith("FAILED "):
            # "FAILED tests/foo.py::test_bar - reason"
            test_id = stripped[7:].split(" - ")[0].strip()
            fails.add(test_id)
        elif stripped.startswith("ERROR "):
            test_id = stripped[6:].strip()
            fails.add("ERROR:" + test_id)

    return frozenset(fails)


# ---------------------------------------------------------------------------
# Running mutants (mutation testing)
# ---------------------------------------------------------------------------
def running_mutants_python(
    instance_id: str,
    workdir: str,
    test_file: str,
    mutant_json_path: str,
    tested_output_dir: str = "Mutants/tested",
    timeout: int = 60,
    python_path: str = "python",
) -> tuple[float, int, int]:
    """
    Apply each mutant to the source, run pytest, check killed/survived.

    Logic mirrors function.py:running_mutants — a mutant is "killed" only if
    it produces DIFFERENT test failures from the baseline (original code).
    Generated tests may already fail on the original, so we compare against
    the baseline output, not against "all pass."

    Returns (mutation_score, killed, total).
    """
    with open(mutant_json_path) as f:
        mutants = json.load(f)

    if not mutants:
        return (0.0, 0, 0)

    test_cmd = [
        python_path, "-m", "pytest",
        test_file,
        "--tb=short", "-q",
        "--color=no",
        "-o", "addopts=",
        "--no-header",
        f"--timeout={timeout}",
    ]
    # If this instance needs --noconftest (detected during collection)
    noconftest_flag = os.path.join(workdir, ".noconftest")
    if os.path.exists(noconftest_flag):
        test_cmd.append("--noconftest")

    results = []
    total = 0

    for mut in mutants:
        filepath = mut["filepath"]
        abs_path = (
            filepath
            if os.path.isabs(filepath)
            else os.path.join(workdir, filepath)
        )

        if not os.path.exists(abs_path):
            logger.warning("Source file not found: %s", abs_path)
            mut["survived"] = True
            results.append(mut)
            total += 1
            continue

        line_no = mut["line"]
        aftercode = mut["aftercode"].strip()

        # Read original
        with open(abs_path, "r") as f:
            original_lines = f.readlines()

        if line_no < 1 or line_no > len(original_lines):
            mut["survived"] = True
            results.append(mut)
            total += 1
            continue

        # Apply mutant
        mutated_lines = list(original_lines)
        orig_line = original_lines[line_no - 1]
        indent = orig_line[: len(orig_line) - len(orig_line.lstrip())]
        mutated_lines[line_no - 1] = indent + aftercode + "\n"

        try:
            with open(abs_path, "w") as f:
                f.writelines(mutated_lines)

            proc = subprocess.run(
                test_cmd, cwd=workdir,
                capture_output=True, text=True,
                timeout=timeout + 10,
            )
            mut["fail_output"] = proc.stdout
            mut["returncode"] = proc.returncode

        except subprocess.TimeoutExpired:
            mut["fail_output"] = "__TIMEOUT__"
            mut["returncode"] = -1
        except Exception as e:
            logger.error("Error running mutant %s: %s", mut.get("id"), e)
            mut["fail_output"] = f"__ERROR__: {e}"
            mut["returncode"] = -2
        finally:
            # Restore original
            with open(abs_path, "w") as f:
                f.writelines(original_lines)

        total += 1
        results.append(mut)

    # --- Baseline run: run tests on original (unmodified) code ---
    try:
        baseline_proc = subprocess.run(
            test_cmd, cwd=workdir,
            capture_output=True, text=True,
            timeout=timeout + 10,
        )
        baseline_output = baseline_proc.stdout
        baseline_rc = baseline_proc.returncode
    except subprocess.TimeoutExpired:
        baseline_output = "__TIMEOUT__"
        baseline_rc = -1
    except Exception as e:
        logger.error("[%s] Baseline test run error: %s", instance_id, e)
        baseline_output = f"__ERROR__: {e}"
        baseline_rc = -1

    # --- Compare each mutant against baseline (by failed test set, not raw string) ---
    baseline_fails = _extract_failed_tests(baseline_output)
    killed = 0
    for mut in results:
        if "fail_output" not in mut:
            # Already marked survived (missing file / bad line)
            continue
        mut_fails = _extract_failed_tests(mut["fail_output"])
        if mut_fails == baseline_fails:
            mut["survived"] = True
        else:
            mut["survived"] = False
            killed += 1

    ms = (killed / total * 100) if total > 0 else 0.0

    os.makedirs(tested_output_dir, exist_ok=True)
    out_path = os.path.join(tested_output_dir, f"{instance_id}.json")
    with open(out_path, "w") as f:
        json.dump(results, f, indent=2, ensure_ascii=False)

    logger.info(
        "[%s] Mutation testing: %d/%d killed (%.1f%%)",
        instance_id, killed, total, ms,
    )
    return (round(ms, 1), killed, total)

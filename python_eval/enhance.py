"""
TC_ENHANCE and MT_ENHANCE for Python / pytest.

Parallel to enhance_testcase.py (TC_ENHANCE) and enhance_mutants.py (MT_ENHANCE)
in the Java/Defects4J pipeline.
"""
import ast
import json
import os
import re
import subprocess
import logging

from prompt import tc_enhance_prompt, mt_enhance_prompt, mt_enhance_survived_prompt

logger = logging.getLogger(__name__)


# ---------------------------------------------------------------------------
# TC_ENHANCE — generate new tests for survived mutants
# ---------------------------------------------------------------------------
def tc_enhance_python(
    instance_id: str,
    test_file: str,
    tested_mutant_path: str,
    source_file: str,
    source_code: str,
    llm_call,
    workdir: str,
    max_attempts: int = 5,
    timeout: int = 60,
    python_path: str = "python",
) -> int:
    """
    For each survived mutant, ask the LLM for a new test to kill it.
    Validates syntax, appends to test_file, verifies it passes on original code.

    Returns the number of new test functions added.
    """
    with open(tested_mutant_path) as f:
        mutants = json.load(f)

    survived = [m for m in mutants if m.get("survived")]
    if not survived:
        logger.info("[%s] TC_ENHANCE: no survived mutants", instance_id)
        return 0

    with open(test_file) as f:
        existing_tests = f.read()

    added = 0
    # Process ALL survived mutants (matching Java behavior — no cap)
    for mut in survived:
        prompt = tc_enhance_prompt(source_code, source_file, mut, existing_tests)
        response = llm_call(prompt)

        new_code = _extract_code_block(response)
        if not new_code:
            logger.debug("TC_ENHANCE: empty code block for mutant %s", mut.get("id"))
            continue

        # Extract only the test function(s) and imports
        imports, funcs = _split_imports_and_functions(new_code)

        if not funcs:
            logger.debug("TC_ENHANCE: no test function found for mutant %s", mut.get("id"))
            continue

        # Build candidate: append to existing file
        addition = "\n\n" + "\n".join(imports) + "\n\n" + "\n".join(funcs) + "\n"
        candidate = existing_tests + addition

        # Syntax check
        try:
            ast.parse(candidate)
        except SyntaxError as e:
            logger.debug("TC_ENHANCE: syntax error: %s", e)
            continue

        # Write candidate, run tests
        with open(test_file, "w") as f:
            f.write(candidate)

        cmd = [
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
            cmd.append("--noconftest")
        try:
            proc = subprocess.run(
                cmd, cwd=workdir,
                capture_output=True, text=True,
                timeout=timeout + 10,
            )
        except subprocess.TimeoutExpired:
            # Revert on timeout
            with open(test_file, "w") as f:
                f.write(existing_tests)
            continue

        # Accept if returncode < 2 (0 = all pass, 1 = some fail — both OK).
        # Only revert on returncode >= 2 (collection error / import failure).
        if proc.returncode < 2:
            existing_tests = candidate
            added += 1
            logger.info("[%s] TC_ENHANCE: added test for mutant %s (rc=%d)",
                        instance_id, mut.get("id"), proc.returncode)
        else:
            # Collection error — try LLM-based fix once before reverting
            error_output = (proc.stderr or "") + "\n" + (proc.stdout or "")
            fix_prompt = (
                prompt
                + f"\n\nYour previous test caused a collection/import error:\n"
                f"```\n{error_output[-1500:]}\n```\n"
                "Please fix and return the corrected test function."
            )
            fix_response = llm_call(fix_prompt)
            fix_code = _extract_code_block(fix_response)
            if fix_code:
                fix_imports, fix_funcs = _split_imports_and_functions(fix_code)
                if fix_funcs:
                    fix_addition = "\n\n" + "\n".join(fix_imports) + "\n\n" + "\n".join(fix_funcs) + "\n"
                    fix_candidate = existing_tests + fix_addition
                    try:
                        ast.parse(fix_candidate)
                    except SyntaxError:
                        fix_candidate = None
                    if fix_candidate:
                        with open(test_file, "w") as f:
                            f.write(fix_candidate)
                        try:
                            proc2 = subprocess.run(
                                cmd, cwd=workdir,
                                capture_output=True, text=True,
                                timeout=timeout + 10,
                            )
                        except subprocess.TimeoutExpired:
                            proc2 = None
                        if proc2 and proc2.returncode < 2:
                            existing_tests = fix_candidate
                            added += 1
                            logger.info("[%s] TC_ENHANCE: added test (after fix) for mutant %s",
                                        instance_id, mut.get("id"))
                            continue

            # Fix failed or not applicable — revert
            with open(test_file, "w") as f:
                f.write(existing_tests)
            logger.debug(
                "TC_ENHANCE: new test causes collection error for mutant %s (rc=%d):\n%s",
                mut.get("id"), proc.returncode, (proc.stdout or "")[-300:],
            )

    logger.info("[%s] TC_ENHANCE: added %d/%d tests", instance_id, added, len(survived))
    return added


# ---------------------------------------------------------------------------
# MT_ENHANCE — generate new mutants (uncovered lines + survived mutant lines)
# ---------------------------------------------------------------------------
def mt_enhance_python(
    instance_id: str,
    source_code: str,
    source_file: str,
    uncovered_lines: list[int],
    mutant_json_path: str,
    tested_mutant_path: str,
    llm_call,
    target_lines: list[int] | None = None,
    num_new: int = 10,
) -> int:
    """
    Generate new mutants from THREE sources:
      1. Survived mutant lines — lines where existing mutants survived
         (generate harder/different mutants on those lines)
      2. Uncovered lines — lines not touched by any mutant yet
      3. Untouched lines — lines in target_lines that have no existing mutant
         (random sample, ~2 mutants each, to explore unmutated code)

    Appends to the raw mutant JSON. Returns total new mutants added.
    """
    import random

    with open(mutant_json_path) as f:
        existing = json.load(f)

    all_new = []

    # --- Source 1: survived mutant lines FIRST (matching Java order) ---
    if os.path.exists(tested_mutant_path):
        with open(tested_mutant_path) as f:
            tested = json.load(f)
        survived = [m for m in tested if m.get("survived")]
        if survived:
            # Group survived mutants by line
            groups = {}
            for m in survived:
                ln = m.get("line")
                if ln is not None:
                    groups.setdefault(ln, []).append(m)

            if groups:
                prompt = mt_enhance_survived_prompt(
                    source_code, source_file, groups,
                    existing_mutant_count=len(existing) + len(all_new),
                )
                for attempt in range(3):
                    response = llm_call(prompt)
                    new = _parse_mutant_json_from_response(response, source_code, source_file)
                    if new:
                        logger.info("[%s] MT_ENHANCE (survived): %d mutants (attempt %d)",
                                    instance_id, len(new), attempt + 1)
                        all_new.extend(new)
                        break
                    logger.debug("[%s] MT_ENHANCE (survived) attempt %d: 0 mutants",
                                 instance_id, attempt + 1)

    # --- Source 2: uncovered lines ---
    if uncovered_lines:
        prompt = mt_enhance_prompt(
            source_code, source_file, uncovered_lines,
            existing_mutant_count=len(existing) + len(all_new), num_new=num_new,
        )
        for attempt in range(3):
            response = llm_call(prompt)
            new = _parse_mutant_json_from_response(response, source_code, source_file)
            if new:
                logger.info("[%s] MT_ENHANCE (uncovered): %d mutants (attempt %d)",
                            instance_id, len(new), attempt + 1)
                all_new.extend(new)
                break
            logger.debug("[%s] MT_ENHANCE (uncovered) attempt %d: 0 mutants",
                         instance_id, attempt + 1)

    # --- Source 3: untouched lines (covered but never mutated) ---
    if target_lines:
        # Lines already touched by any existing mutant (including newly generated)
        mutated_lines = set(m.get("line") for m in existing if m.get("line"))
        mutated_lines.update(m.get("line") for m in all_new if m.get("line"))

        # Filter target_lines to only mutable lines (non-blank, non-comment, non-decorator)
        source_lines = source_code.splitlines()
        untouched = []
        for ln in target_lines:
            if ln in mutated_lines:
                continue
            if ln < 1 or ln > len(source_lines):
                continue
            stripped = source_lines[ln - 1].strip()
            # Skip blank, comments, decorators, pure 'pass', docstrings
            if (not stripped or stripped.startswith("#") or
                stripped.startswith("@") or stripped == "pass" or
                stripped.startswith('"""') or stripped.startswith("'''")):
                continue
            untouched.append(ln)

        if untouched:
            # Randomly sample up to 5 untouched lines, generate ~2 mutants each
            random.seed(len(existing) + len(all_new))  # Reproducible per round
            sample_count = min(5, len(untouched))
            sampled_lines = random.sample(untouched, sample_count)

            prompt = mt_enhance_prompt(
                source_code, source_file, sampled_lines,
                existing_mutant_count=len(existing) + len(all_new),
                num_new=sample_count * 2,
            )
            for attempt in range(3):
                response = llm_call(prompt)
                new = _parse_mutant_json_from_response(response, source_code, source_file)
                if new:
                    logger.info("[%s] MT_ENHANCE (untouched): %d mutants on %d lines (attempt %d)",
                                instance_id, len(new), len(sampled_lines), attempt + 1)
                    all_new.extend(new)
                    break
                logger.debug("[%s] MT_ENHANCE (untouched) attempt %d: 0 mutants",
                             instance_id, attempt + 1)

    if not all_new:
        logger.info("[%s] MT_ENHANCE: 0 new mutants from all sources", instance_id)
        return 0

    # Re-id to avoid collisions
    max_id = max((m.get("id", 0) for m in existing), default=0)
    for i, m in enumerate(all_new, start=max_id + 1):
        m["id"] = i

    existing.extend(all_new)
    with open(mutant_json_path, "w") as f:
        json.dump(existing, f, indent=2, ensure_ascii=False)

    logger.info("[%s] MT_ENHANCE: added %d total mutants", instance_id, len(all_new))
    return len(all_new)


# ---------------------------------------------------------------------------
# Helpers
# ---------------------------------------------------------------------------
def _extract_code_block(response: str) -> str:
    """Extract first Python code block from LLM response."""
    m = re.search(r"```python\s*\n(.*?)```", response, re.DOTALL)
    if m:
        return m.group(1).strip()
    m = re.search(r"```\s*\n(.*?)```", response, re.DOTALL)
    if m:
        return m.group(1).strip()
    return ""


def _split_imports_and_functions(code: str) -> tuple[list[str], list[str]]:
    """
    Split code into import lines and function definitions.
    Returns (import_lines, function_blocks).
    """
    imports = []
    funcs = []
    current_func = []
    in_func = False

    for line in code.splitlines():
        stripped = line.strip()
        if stripped.startswith(("import ", "from ")):
            imports.append(line)
        elif stripped.startswith("def test_"):
            if in_func and current_func:
                funcs.append("\n".join(current_func))
            current_func = [line]
            in_func = True
        elif in_func:
            current_func.append(line)

    if in_func and current_func:
        funcs.append("\n".join(current_func))

    return imports, funcs


def _parse_mutant_json_from_response(
    response: str, source_code: str, file_path: str
) -> list[dict]:
    """Parse mutant JSON from LLM response (same logic as mutant.py)."""
    text = response.strip()

    m = re.search(r"```(?:json)?\s*(\[.*?\])\s*```", text, re.DOTALL)
    if m:
        text = m.group(1)
    else:
        start = text.find("[")
        end = text.rfind("]")
        if start != -1 and end != -1:
            text = text[start : end + 1]

    try:
        raw = json.loads(text)
    except json.JSONDecodeError:
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
        if not isinstance(line, int) or line < 1 or line > len(source_lines):
            continue
        if pre.strip() == after.strip():
            continue
        item["filepath"] = file_path
        valid.append(item)

    return valid

"""
Run coverage.py via pytest, parse the JSON report, and return uncovered lines.

Parallel to coverage.py in the Java/Defects4J pipeline (Cobertura XML parsing).
"""
import json
import os
import subprocess
import logging

logger = logging.getLogger(__name__)


def coverage_process_python(
    workdir: str,
    test_file: str,
    source_file: str,
    target_lines: list[int] | None = None,
    timeout: int = 300,
    python_path: str = "python",
) -> tuple[list[int], float]:
    """
    Run pytest with coverage on *test_file*, then parse the JSON report
    to find which lines in *source_file* are covered.

    Returns
    -------
    (uncovered_lines, line_coverage_pct)
        uncovered_lines : sorted list of line numbers not executed
        line_coverage_pct : 0-100 float
    """
    cov_json = os.path.join(workdir, "coverage.json")
    # Clean stale report
    if os.path.exists(cov_json):
        os.remove(cov_json)

    # Determine the source module/package path relative to workdir
    rel_source = os.path.relpath(source_file, workdir)
    # --cov wants a module/directory path (e.g. "docling/backend")
    cov_target = os.path.dirname(rel_source) or "."

    cmd = [
        python_path, "-m", "pytest",
        test_file,
        f"--cov={cov_target}",
        "--cov-report", f"json:{cov_json}",
        "--cov-report", "term:skip-covered",
        "--no-header", "-q",
        "--tb=no",
        "-o", "addopts=",
    ]
    # If this instance needs --noconftest (detected during collection)
    noconftest_flag = os.path.join(workdir, ".noconftest")
    if os.path.exists(noconftest_flag):
        cmd.append("--noconftest")

    try:
        result = subprocess.run(
            cmd, cwd=workdir,
            capture_output=True, text=True,
            timeout=timeout,
        )
        logger.debug("coverage stdout:\n%s", result.stdout[-500:])
        if result.returncode not in (0, 1):
            # 0 = all pass, 1 = some fail, 2+ = error
            logger.warning("pytest-cov returned %d: %s",
                           result.returncode, result.stderr[-300:])
    except subprocess.TimeoutExpired:
        logger.error("Coverage run timed out (%ds)", timeout)
        return (target_lines or [], 0.0)

    if not os.path.exists(cov_json):
        logger.error("coverage.json not produced at %s", cov_json)
        return (target_lines or [], 0.0)

    # Parse coverage.json
    with open(cov_json) as f:
        cov_data = json.load(f)

    # Find the entry for our source file
    # coverage.json keys are relative paths from workdir
    file_cov = None
    for fpath, data in cov_data.get("files", {}).items():
        # Match by suffix since keys may vary (./src/foo.py vs src/foo.py)
        if fpath.endswith(rel_source) or rel_source.endswith(fpath):
            file_cov = data
            break

    if file_cov is None:
        logger.warning("Source file %s not found in coverage report", rel_source)
        return (target_lines or [], 0.0)

    executed = set(file_cov.get("executed_lines", []))
    missing = set(file_cov.get("missing_lines", []))
    # Lines that coverage.py considers executable (excludes blank/comment/else:)
    executable = executed | missing

    if target_lines:
        # Filter target to only executable lines — blank lines, comments,
        # and bare keywords (else:) are non-executable and inflate the uncovered count
        target_set = set(target_lines) & executable
        if not target_set:
            # All target lines are non-executable → treat as fully covered
            logger.info("All %d target lines are non-executable", len(target_lines))
            return ([], 100.0)
        covered_in_scope = target_set & executed
        uncovered_in_scope = sorted(target_set - executed)
        total = len(target_set)
        pct = (len(covered_in_scope) / total * 100) if total > 0 else 0.0
    else:
        uncovered_in_scope = sorted(missing)
        total = len(executed) + len(missing)
        pct = (len(executed) / total * 100) if total > 0 else 0.0

    return (uncovered_in_scope, round(pct, 1))

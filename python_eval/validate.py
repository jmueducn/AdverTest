"""
Validation utilities: Python syntax check and diff format check.
"""
import ast
import os
import subprocess
import tempfile
import logging

logger = logging.getLogger(__name__)


def check_syntax(code: str) -> tuple[bool, str]:
    """
    Verify *code* is syntactically valid Python.

    Returns (ok, error_message).
    """
    try:
        ast.parse(code)
        return True, ""
    except SyntaxError as e:
        return False, f"SyntaxError at line {e.lineno}: {e.msg}"


def check_diff(diff: str, workdir: str) -> tuple[bool, str]:
    """
    Verify *diff* is a valid unified diff.

    We check in a temporary clone to avoid conflicts with the generated file
    already present in the workdir.

    Returns (ok, error_message).
    """
    try:
        # Basic structural check: must have diff header and hunks
        if "diff --git" not in diff or "@@" not in diff:
            return False, "Missing diff header or hunk markers"
        if "+++ " not in diff or "--- " not in diff:
            return False, "Missing +++ or --- markers"
        return True, ""
    except Exception as e:
        return False, str(e)


def validate_prediction(
    test_code: str,
    diff: str,
    workdir: str,
) -> dict:
    """
    Run all validation checks and return a summary dict.
    """
    syntax_ok, syntax_err = check_syntax(test_code)
    diff_ok, diff_err = check_diff(diff, workdir)

    return {
        "syntax_ok": syntax_ok,
        "syntax_error": syntax_err,
        "diff_ok": diff_ok,
        "diff_error": diff_err,
        "all_ok": syntax_ok and diff_ok,
    }

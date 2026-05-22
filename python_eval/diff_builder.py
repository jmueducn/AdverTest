"""
Convert a generated test file into a unified diff suitable for SWE-bench
predictions.jsonl.

The diff creates a NEW file in the repo (e.g. tests/test_advertest_generated.py)
so it never conflicts with existing test files.
"""
import os


def build_diff(test_file_content: str, test_file_path: str) -> str:
    """
    Build a unified diff that creates *test_file_path* from scratch.

    Parameters
    ----------
    test_file_content : str
        The full content of the generated test file.
    test_file_path : str
        Repo-relative path, e.g. ``tests/test_advertest_generated.py``.

    Returns
    -------
    str
        A ``diff --git`` unified diff string.
    """
    lines = test_file_content.splitlines(keepends=True)
    # Ensure trailing newline
    if lines and not lines[-1].endswith("\n"):
        lines[-1] += "\n"

    n = len(lines)
    plus_lines = "".join("+" + l for l in lines)

    a_path = f"a/{test_file_path}"
    b_path = f"b/{test_file_path}"

    diff = (
        f"diff --git {a_path} {b_path}\n"
        f"new file mode 100644\n"
        f"--- /dev/null\n"
        f"+++ {b_path}\n"
        f"@@ -0,0 +1,{n} @@\n"
        f"{plus_lines}"
    )
    return diff


def save_prediction(
    instance_id: str,
    model_patch: str,
    model_name: str = "advertest_python",
    output_path: str = "outputs/predictions.jsonl",
) -> None:
    """Append one prediction entry to the JSONL file."""
    import json

    entry = {
        "instance_id": instance_id,
        "model_name_or_path": model_name,
        "model_patch": model_patch,
    }

    os.makedirs(os.path.dirname(output_path) or ".", exist_ok=True)
    with open(output_path, "a") as f:
        f.write(json.dumps(entry, ensure_ascii=False) + "\n")

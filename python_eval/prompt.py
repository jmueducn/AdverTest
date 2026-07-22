"""
Prompt templates for the Python/SWE-Rebench AdverTest pipeline.

Adapted from the Java/Defects4J prompts in testIniGenPrompt.py, function.py,
enhance_testcase.py, and enhance_mutants.py.  All prompts give ONLY source code
-- no bug descriptions / problem_statements.
"""


# ---------------------------------------------------------------------------
# 1. Initial test generation
# ---------------------------------------------------------------------------
def test_generation_prompt(source_code: str, file_path: str,
                           module_context: str = "") -> str:
    """Build the prompt for initial pytest test generation."""
    ctx_block = ""
    if module_context:
        ctx_block = (
            "\n\n## Additional module context (imports, related classes/functions)\n"
            "```python\n" + module_context + "\n```"
        )

    return f"""\
You are an expert Python developer and software tester.
Generate comprehensive pytest test functions for the code below.

## Source file: `{file_path}`
```python
{source_code}
```{ctx_block}

## Requirements
1. Analyze every function/method: parameters, return types, branches, edge cases.
2. Cover normal behaviour, boundary values, error/exception paths, and corner cases.
3. Each test function must be independent and self-contained.
4. Use descriptive names: `test_<function>_<scenario>`.
5. Use `pytest.raises` for expected exceptions.
6. Do NOT mock internal implementation details unless absolutely necessary.
7. Do NOT import anything that is not available in the repository.
8. All tests must be deterministic (no random, no network).

## Output format
Return ONLY a single Python code block (```python ... ```) containing the
complete test file, starting with the necessary imports.
Do NOT include any explanation outside the code block.
"""


# ---------------------------------------------------------------------------
# 2. Mutant generation
# ---------------------------------------------------------------------------
def mutant_generation_prompt(source_code: str, file_path: str,
                              target_lines: list[int] | None = None,
                              num_mutants: int = 20) -> str:
    """Build the prompt to generate code mutants for Python source.

    Only includes the code around target_lines (with context) to keep
    the prompt short, matching the Java pipeline's approach of sending
    only the affected method code.
    """
    source_lines = source_code.splitlines()

    if target_lines:
        # Show the target function(s) with some context
        min_line = max(1, min(target_lines) - 5)
        max_line = min(len(source_lines), max(target_lines) + 5)
        snippet_lines = []
        for i in range(min_line - 1, max_line):
            snippet_lines.append(f"{i + 1}: {source_lines[i]}")
        snippet = "\n".join(snippet_lines)
        line_hint = (
            f"\nFocus mutants on lines {min(target_lines)}-{max(target_lines)} "
            f"(the target function/method).\n"
        )
    else:
        snippet = "\n".join(f"{i + 1}: {line}" for i, line in enumerate(source_lines))
        line_hint = ""

    return f"""\
You are an expert in mutation testing for Python code.
Generate {num_mutants} distinct mutants for the code below.

## Source file: `{file_path}`
```python
{snippet}
```
{line_hint}
## Rules
1. Each mutant is a single-line change that could represent a real bug.
2. Mutants must be non-equivalent (the change must be observable by some test).
3. Distribute mutants across different functions and lines.
4. Use varied mutation operators: arithmetic, relational, logical, boundary,
   statement deletion, return value, exception handling, off-by-one, etc.
5. Each mutant must compile (be syntactically valid Python).

## Output format
Return a JSON array (and NOTHING else) where each element is:
```json
{{
  "id": <int>,
  "line": <original line number>,
  "filepath": "{file_path}",
  "precode": "<exact original source line, stripped>",
  "aftercode": "<mutated source line, stripped>"
}}
```
"""


# ---------------------------------------------------------------------------
# 3. TC_ENHANCE — new tests targeting survived mutants
# ---------------------------------------------------------------------------
def tc_enhance_prompt(source_code: str, file_path: str,
                      survived_mutant: dict,
                      existing_tests: str) -> str:
    """Prompt for generating a new test to kill a survived mutant."""
    return f"""\
You are an expert Python tester specialising in mutation testing.

A mutant SURVIVED — none of the existing tests detected the change below.
Your job: write ONE new pytest test function that KILLS this mutant (i.e. the
test passes on the original code but fails on the mutated code).

## Original source (`{file_path}`)
```python
{source_code}
```

## The survived mutant (line {survived_mutant['line']})
- **Original line:** `{survived_mutant['precode']}`
- **Mutated line:**  `{survived_mutant['aftercode']}`

## Existing tests (for reference — do NOT duplicate)
```python
{existing_tests}
```

## Requirements
1. Analyse exactly what behaviour changes when the mutation is applied.
2. Design an input that produces a DIFFERENT output/side-effect under the mutant.
3. Write a single `def test_...():` function with a clear assertion.
4. The test MUST pass on the original code.

## Output format
Return ONLY a single Python code block with the new test function
(including any extra imports it needs).
"""


# ---------------------------------------------------------------------------
# 4. MT_ENHANCE — new mutants for uncovered lines
# ---------------------------------------------------------------------------
def mt_enhance_prompt(source_code: str, file_path: str,
                      uncovered_lines: list[int],
                      existing_mutant_count: int,
                      num_new: int = 10) -> str:
    """Prompt for generating new mutants targeting uncovered lines."""
    lines_str = ", ".join(str(l) for l in uncovered_lines[:30])
    return f"""\
You are an expert in mutation testing for Python code.

The following source lines are NOT yet covered by any existing mutant.
Generate {num_new} new mutants specifically targeting these uncovered lines.

## Source file: `{file_path}`
```python
{source_code}
```

## Uncovered lines (no mutant touches these yet)
Lines: {lines_str}

## Rules
1. Each mutant must modify exactly one of the uncovered lines listed above.
2. Single-line change only; must be syntactically valid Python.
3. Non-equivalent (observable by some test).
4. Varied mutation operators.

## Output format
Return a JSON array where each element is:
```json
{{
  "id": <int, starting from {existing_mutant_count + 1}>,
  "line": <line number>,
  "filepath": "{file_path}",
  "precode": "<exact original source line, stripped>",
  "aftercode": "<mutated source line, stripped>"
}}
```
Return ONLY the JSON array, no explanation.
"""


# ---------------------------------------------------------------------------
# 4b. MT_ENHANCE_SURVIVED — harder mutants on survived-mutant lines
# ---------------------------------------------------------------------------
def mt_enhance_survived_prompt(source_code: str, file_path: str,
                                survived_groups: dict[int, list[dict]],
                                existing_mutant_count: int,
                                num_per_line: int = 2) -> str:
    """
    Prompt for generating additional mutants on lines where existing mutants
    survived.  The LLM sees the existing (weak) mutants and must produce
    different, harder-to-survive ones.
    """
    group_blocks = []
    for line_no, mutants in list(survived_groups.items())[:10]:
        existing_str = ", ".join(
            f'`{m.get("aftercode", "")}`' for m in mutants
        )
        group_blocks.append(
            f"- **Line {line_no}**: already tried: {existing_str}"
        )
    groups_text = "\n".join(group_blocks)
    total_new = min(len(survived_groups) * num_per_line, 20)

    return f"""\
You are an expert Software Test Engineer specialising in Mutation Testing.

Existing mutants on the lines below all SURVIVED (tests did not catch them).
Generate {total_new} *additional* distinct mutants on these same lines that are
**different** from the ones already tried.

## Source file: `{file_path}`
```python
{source_code}
```

## Survived mutant lines (existing weak mutants shown)
{groups_text}

## Rules
1. Each mutant must target one of the lines listed above.
2. Must be different from the already-tried mutants shown.
3. Single-line change; syntactically valid Python; non-equivalent.
4. Be creative — use different mutation operators than the ones that survived.

## Output format
Return a JSON array:
```json
{{
  "id": <int, starting from {existing_mutant_count + 1}>,
  "line": <line number>,
  "filepath": "{file_path}",
  "precode": "<exact original source line, stripped>",
  "aftercode": "<mutated source line, stripped>"
}}
```
Return ONLY the JSON array, no explanation.
"""



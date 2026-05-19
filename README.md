# AdverTest: Replication Package

Replication package for **AdverTest**, an LLM-based mutation testing and test generation framework. AdverTest uses large language models to automatically generate unit tests and code mutants, then iteratively enhances them to maximize mutation score and code coverage.

## Pipeline Overview

```
initial_generate_testcase()  ->  initial_generate_mutant()
        |                                |
    Compile & run tests            Run mutation testing
        |                                |
              +--- Iterative Enhancement Loop (up to 4 rounds) ---+
              |  TC_ENHANCE  (new tests for survived mutants)      |
              |  MT_ENHANCE  (new mutants for uncovered lines)     |
              |  Alternating: TC -> MT -> TC -> MT                 |
              +----------------------------------------------------+
        |
    bug_detection_ourgen()  ->  Compare fixed vs buggy version
```

**Convergence targets:** Mutation Score >= 70%, Coverage >= 95%, max 4 enhancement rounds.

---

## 1. Requirements

### System
- Java JDK 8+
- [Defects4J v2.0.1](https://github.com/rjust/defects4j) (install and add to `PATH`)
- Ant / Maven (for Defects4J builds)
- Git

### Python
```bash
pip install openai langchain javalang tenacity httpx tqdm
```

### API Keys (set as environment variables)
```bash
# For the main Defects4J pipeline (model.py):
export DEEPSEEK_API_KEY="your-deepseek-api-key"
# Or use any OpenAI-compatible API by passing api_key and base_url to LLMWrapper

# For the SWE-Rebench pipeline (swebench_eval/):
export LLM_API_KEY="your-api-key"
export LLM_BASE_URL="https://api.deepseek.com"
export LLM_MODEL="deepseek-chat"
```

---

## 2. Setup

### 2.1 Install Defects4J

Follow the [Defects4J installation guide](https://github.com/rjust/defects4j#setting-up-defects4j). Ensure `defects4j` is on your `PATH`.

### 2.2 Add JUnit/Mockito JARs to Defects4J

Edit `defects4j/framework/projects/defects4j.build.xml` and add these properties pointing to the JARs in `lib/`:

```xml
<property name="mock-junit.jar" value="/path/to/lib/mockito-junit-jupiter-4.11.0.jar"/>
<property name="objnesis.jar" value="/path/to/lib/objenesis-3.2.jar"/>
<property name="mockito.jar" value="/path/to/lib/mockito-core-4.11.0.jar"/>
<property name="byte-buddy.jar" value="/path/to/lib/byte-buddy-1.14.4.jar"/>
```

### 2.3 Download Defects4J Projects

Edit `download.py` to specify the projects and bug counts you need, then run:

```bash
python download.py
```

This checks out fixed versions to `./defects4j_fixed/{Project}/{Project}_{id}_fixed/` and buggy versions to `./defects4j_bug/{Project}/{Project}_{id}_bug/`.

### 2.4 Configure the LLM

Edit `generate.py` line 15 to set your LLM:

```python
llm = Deepseek(api_key="your-api-key", model="deepseek-chat")
```

The `model.py` file provides `GPT` and `Deepseek` wrapper classes. Both read API keys from environment variables if available.

---

## 3. Running the Main Pipeline (Defects4J)

```bash
# Edit generate.py to set project name and bug range, then:
python generate.py
```

Key parameters in `generate.py`:
- `projname` -- Defects4J project (e.g., `'Math'`, `'Chart'`, `'Closure'`)
- `max_workers` -- number of parallel threads
- Bug range in `ThreadPoolExecutor` -- e.g., `range(0, 106)` for Math

### Supported Defects4J Projects (17)

Chart, Cli, Closure, Codec, Collections, Compress, Csv, Gson, JacksonCore, JacksonDatabind, JacksonXml, Jsoup, JxPath, Lang, Math, Mockito, Time

### Output

- Logs: `AdverTest_*.log`
- Mutants (raw): `./Mutants/{source}/raw/{Project}-{id}.json`
- Mutants (tested): `./Mutants/{source}/tested/{Project}-{id}_test.json`
- Test archives: `./defects4j_fixed/{Project}/{Project}_{id}_fixed/gentest/`

---

## 4. Running the SWE-Rebench Pipeline (Python)

The `swebench_eval/` directory contains a port of AdverTest for Python projects, targeting [SWE-Rebench](https://github.com/swe-bench/SWE-ReBench) instances.

### Setup

1. Place the SWE-Rebench dataset files in `swebench_eval/data/`:
   - `rebench_leaderboard_2026_02_top30.jsonl`
   - `resolved_rebench_leaderboard_gpt54mini_2026_02_top30_contexts.jsonl`

2. Set API keys:
   ```bash
   export LLM_API_KEY="your-api-key"
   export LLM_BASE_URL="https://api.deepseek.com"
   ```

### Run

```bash
cd swebench_eval
python generate_tests.py --model deepseek-chat --instance <instance_id>
```

### Evaluate

```bash
bash run_harness.sh
```

---

## 5. File Reference

### Core Pipeline

| File | Description |
|---|---|
| `generate.py` | Main orchestrator with parallel execution |
| `function.py` | Patch parsing, test/mutant generation, mutation testing |
| `model.py` | LLM wrappers (Deepseek, GPT) with retry logic |
| `enhance_testcase.py` | TC_ENHANCE: generates new tests for survived mutants |
| `enhance_mutants.py` | MT_ENHANCE: generates new mutants for uncovered lines |
| `test.py` | Bug detection: runs tests on fixed vs buggy versions |
| `coverage.py` | Parses Cobertura XML for coverage metrics |
| `extract.py` | Java class structure parsing via javalang AST |
| `testIniGenPrompt.py` | Prompt construction, test extraction, compile & run |
| `compress.py` | Archives generated test directories |
| `config.py` | Shared logging configuration |
| `copy_files.py` | Copies generated test archives to output directories |
| `download.py` | Downloads Defects4J fixed and buggy project versions |

### SWE-Rebench Pipeline (`swebench_eval/`)

| File | Description |
|---|---|
| `generate_tests.py` | Main test generation for Python projects |
| `prompt.py` | Prompt templates for Python test generation |
| `mutant.py` | Mutation logic for Python code |
| `enhance.py` | Enhancement rounds (TC + MT) |
| `coverage_py.py` | Python coverage parsing |
| `diff_builder.py` | Builds diffs for SWE-bench predictions |
| `validate.py` | Test validation and syntax checking |
| `compile_results.py` | Results compilation |
| `run_extra_rounds.py` | Run additional enhancement rounds |
| `run_harness.sh` | SWE-bench evaluation harness |

### Libraries (`lib/`)

JUnit 5 (5.9.3), Mockito 4 (4.11.0), ByteBuddy (1.14.4), Objenesis (3.2) -- added to Defects4J build classpath for test compilation.

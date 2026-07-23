# AdverTest: Replication Package

Replication package for paper **Test vs Mutant: Adversarial LLM Agents for Robust Unit Test Generation**. (Accepted by ISSTA 2026)
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
- [Defects4J v2.1.0](https://github.com/rjust/defects4j/tree/v2.1.0) (install and add to `PATH`)
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

# For the Python evaluation pipeline (python_eval/):
export LLM_API_KEY="your-api-key"
export LLM_BASE_URL="https://api.deepseek.com"
export LLM_MODEL="deepseek-chat"
```

---

## 2. Setup

### 2.1 Install Defects4J

Follow the [Defects4J installation guide](https://github.com/rjust/defects4j#setting-up-defects4j) to install **v2.1.0**. Ensure `defects4j` is on your `PATH`.

```bash
git clone https://github.com/rjust/defects4j.git
cd defects4j
git checkout v2.1.0
cpanm --installdeps .
./init.sh
export PATH=$PATH:$(pwd)/framework/bin
```

### 2.2 Apply Defects4J Patch

Apply the included patch to add JUnit 5, Mockito, and other required changes to Defects4J:

```bash
cd /path/to/defects4j
git apply /path/to/defects4j_setup.patch
```

This patch makes the following changes:

1. **`framework/projects/defects4j.build.xml`** -- Adds 7 JAR dependencies to the build classpath (compilation and test execution):

   ```xml
   <property name="junit5-api.jar" value="/path/to/lib/junit-jupiter-api-5.9.3.jar"/>
   <property name="junit5-engine.jar" value="/path/to/lib/junit-jupiter-engine-5.9.3.jar"/>
   <property name="junit5-platform.jar" value="/path/to/lib/junit-platform-commons-1.9.3.jar"/>
   <property name="mock-junit.jar" value="/path/to/lib/mockito-junit-jupiter-4.11.0.jar"/>
   <property name="objnesis.jar" value="/path/to/lib/objenesis-3.2.jar"/>
   <property name="mockito.jar" value="/path/to/lib/mockito-core-4.11.0.jar"/>
   <property name="byte-buddy.jar" value="/path/to/lib/byte-buddy-1.14.4.jar"/>
   ```

   **Important:** After applying the patch, update the JAR paths in `defects4j.build.xml` to point to the absolute path of the `lib/` directory in your replication package.

2. **`framework/core/Utils.pm`** -- Adds `-m` flag to tar extraction to prevent permission errors (`Cannot utime: Operation not permitted`) when the working directory is not owned by the current user.

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

## 4. Running the Python Evaluation Pipeline

The `python_eval/` directory contains a port of AdverTest for Python projects, targeting SWE-bench instances.

### Setup

1. Place the dataset files in `python_eval/data/`.

2. Set API keys:
   ```bash
   export LLM_API_KEY="your-api-key"
   export LLM_BASE_URL="https://api.deepseek.com"
   ```

### Run

```bash
cd python_eval
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

### Python Evaluation Pipeline (`python_eval/`)

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

### Case Study (`case/`)

Generated tests from baseline methods and AdverTest for the case study section in the paper. Contains test examples from five tools:

- `AdverTest/` -- Tests generated by AdverTest
- `ChatUniTest/` -- Tests generated by ChatUniTest
- `Evosuite/` -- Tests generated by EvoSuite
- `HITS/` -- Tests generated by HITS
- `Randoop/` -- Tests generated by Randoop

### Libraries (`lib/`)

JUnit 5 (5.9.3), Mockito 4 (4.11.0), ByteBuddy (1.14.4), Objenesis (3.2) -- added to Defects4J build classpath for test compilation.

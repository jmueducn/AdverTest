"""
Run additional TC_ENHANCE / MT_ENHANCE rounds for a specific instance.

Usage:
    python run_extra_rounds.py --instance deepset-ai__haystack-10547 --extra-rounds 4
"""
import argparse
import json
import logging
import os
import sys
import time

# Re-use existing pipeline modules
from generate_tests import (
    load_instances, make_llm_call, bug_detection_python,
    WORKDIRS, MUTANTS_RAW, MUTANTS_TESTED, SCRIPT_DIR,
)
from mutant import running_mutants_python
from enhance import tc_enhance_python, mt_enhance_python
from coverage_py import coverage_process_python

LOG_FILE = os.path.join(SCRIPT_DIR, "logs", "extra_rounds.log")
os.makedirs(os.path.dirname(LOG_FILE), exist_ok=True)
logging.basicConfig(
    level=logging.INFO,
    format="%(asctime)s - %(levelname)s - %(message)s",
    handlers=[
        logging.FileHandler(LOG_FILE, mode="a"),
        logging.StreamHandler(),
    ],
)
logger = logging.getLogger(__name__)


def run_extra_rounds(instance: dict, llm_call, extra_rounds: int = 4):
    iid = instance["instance_id"]
    workdir = os.path.join(WORKDIRS, iid)
    venv_python = os.path.join(workdir, ".venv", "bin", "python")
    python_path = venv_python if os.path.exists(venv_python) else "python"

    test_file = os.path.join(workdir, "tests", "test_advertest_generated.py")
    mutant_path = os.path.join(MUTANTS_RAW, f"{iid}.json")
    tested_path = os.path.join(MUTANTS_TESTED, f"{iid}.json")

    # Extract source info from the instance context
    ctx = instance.get("context", instance)
    intf = ctx.get("interface", {})
    if isinstance(intf, str):
        intf = json.loads(intf) if intf else {}
    source_file = intf.get("source_file", "")
    target_lines = intf.get("target_lines", [])

    if not source_file:
        # Try to get from mutant JSON
        with open(mutant_path) as f:
            mutants = json.load(f)
        if mutants:
            source_file = mutants[0].get("filepath", "")

    abs_source = os.path.join(workdir, source_file)
    with open(abs_source) as f:
        source_code = f.read()

    logger.info("=" * 60)
    logger.info("[%s] Starting %d extra TC/MT rounds", iid, extra_rounds)
    logger.info("[%s] Source: %s (%d lines)", iid, source_file, len(source_code.splitlines()))
    logger.info("[%s] Test file: %s", iid, test_file)
    logger.info("[%s] Mutant path: %s", iid, mutant_path)

    # Current state
    with open(mutant_path) as f:
        all_mutants = json.load(f)
    with open(tested_path) as f:
        tested_mutants = json.load(f)
    survived = [m for m in tested_mutants if m.get("survived")]
    killed = [m for m in tested_mutants if not m.get("survived")]
    logger.info("[%s] Current: %d total mutants, %d killed, %d survived",
                iid, len(all_mutants), len(killed), len(survived))

    # Get current coverage
    uncovered, line_cov = coverage_process_python(
        workdir, test_file, abs_source, target_lines,
        python_path=python_path,
    )
    logger.info("[%s] Current coverage: %.1f%%, %d uncovered lines", iid, line_cov, len(uncovered))

    # Run extra rounds: TC → MT → TC → MT ...
    detection_history = []
    cnt_T, cnt_M = 0, 0

    for round_num in range(extra_rounds):
        t_start = time.time()

        if cnt_T <= cnt_M:
            # TC_ENHANCE
            added = tc_enhance_python(
                iid, test_file, tested_path, source_file,
                source_code, llm_call, workdir,
                python_path=python_path,
            )
            cnt_T += 1
            logger.info("[%s] Extra TC round %d: +%d tests (%.1fs)",
                        iid, cnt_T, added, time.time() - t_start)
        else:
            # MT_ENHANCE
            added = mt_enhance_python(
                iid, source_code, source_file, uncovered,
                mutant_path, tested_path, llm_call,
                target_lines=target_lines,
            )
            cnt_M += 1
            logger.info("[%s] Extra MT round %d: +%d mutants (%.1fs)",
                        iid, cnt_M, added, time.time() - t_start)

        # Re-run mutation testing
        ms, killed_cnt, total = running_mutants_python(
            iid, workdir, test_file, mutant_path,
            tested_output_dir=MUTANTS_TESTED,
            python_path=python_path,
        )

        # Re-run coverage
        uncovered, line_cov = coverage_process_python(
            workdir, test_file, abs_source, target_lines,
            python_path=python_path,
        )

        logger.info("[%s] After extra round %d — MS=%.1f%% Cov=%.1f%% TC=%d MT=%d",
                    iid, round_num + 1, ms, line_cov, cnt_T, cnt_M)

        # Check bug detection
        det = bug_detection_python(
            instance, workdir, test_file, python_path=python_path,
        )
        entry = {
            "stage": f"extra_round_{round_num+1}_TC{cnt_T}_MT{cnt_M}",
            "detected": det["detected"],
            "fixed_fails": len(det["fixed_fails"]),
            "buggy_fails": len(det["buggy_fails"]),
            "new_failures": sorted(
                set(det["buggy_fails"]) - set(det["fixed_fails"])
            ),
        }
        detection_history.append(entry)
        logger.info(
            "[%s] Bug detection @ extra round %d: detected=%s (fixed=%d, buggy=%d, new=%d)",
            iid, round_num + 1, det["detected"],
            len(det["fixed_fails"]), len(det["buggy_fails"]),
            len(set(det["buggy_fails"]) - set(det["fixed_fails"])),
        )

        if det["detected"]:
            logger.info("[%s] BUG DETECTED at extra round %d!", iid, round_num + 1)
            break

    # Summary
    logger.info("=" * 60)
    logger.info("[%s] Extra rounds complete. Detection history:", iid)
    for entry in detection_history:
        logger.info("  %s: detected=%s, fixed=%d, buggy=%d, new=%s",
                    entry["stage"], entry["detected"],
                    entry["fixed_fails"], entry["buggy_fails"],
                    entry["new_failures"][:3])

    # Save extended results
    out_path = os.path.join(SCRIPT_DIR, "outputs", f"extra_rounds_{iid}.json")
    with open(out_path, "w") as f:
        json.dump({
            "instance_id": iid,
            "extra_rounds": extra_rounds,
            "detection_history": detection_history,
            "final_ms": ms,
            "final_cov": line_cov,
        }, f, indent=2)
    logger.info("[%s] Results saved to %s", iid, out_path)


def main():
    parser = argparse.ArgumentParser()
    parser.add_argument("--instance", required=True)
    parser.add_argument("--extra-rounds", type=int, default=4)
    args = parser.parse_args()

    instances = load_instances()
    instance = None
    for inst in instances:
        if inst["instance_id"] == args.instance:
            instance = inst
            break
    if not instance:
        logger.error("Instance %s not found", args.instance)
        sys.exit(1)

    # LLM setup — configure via environment variables
    model = os.getenv("LLM_MODEL", "deepseek-chat")
    api_key = os.getenv("LLM_API_KEY", "")
    base_url = os.getenv("LLM_BASE_URL", "https://api.deepseek.com")
    llm_call = make_llm_call(model, api_key, base_url)

    run_extra_rounds(instance, llm_call, extra_rounds=args.extra_rounds)


if __name__ == "__main__":
    main()

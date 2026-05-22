#!/usr/bin/env python3
"""
Compile final results.json by parsing the generation log.

For each of the 12 instances, finds the LAST successful run and extracts:
- Summary metrics (MS, Cov, rounds, bug_detected)
- Detection history (from "Bug detection @" log lines)
- Tests/mutants added (from "TC_ENHANCE round" and "MT_ENHANCE round" lines)
- Round details (from "Round N" lines)

Outputs to outputs/results.json.
"""

import json
import os
import re
import sys
from collections import defaultdict

SCRIPT_DIR = os.path.dirname(os.path.abspath(__file__))
LOG_PATH = os.path.join(SCRIPT_DIR, "logs", "generation.log")
OUTPUT_PATH = os.path.join(SCRIPT_DIR, "outputs", "results.json")

INSTANCES = [
    "a2aproject__a2a-python-682_interface",
    "aws__bedrock-agentcore-sdk-python-279_interface",
    "conan-io__conan-19604",
    "deepset-ai__haystack-10547",
    "docling-project__docling-2983",
    "getmoto__moto-9727",
    "jmcgeheeiv__pyfakefs-1286",
    "joshuadavidthomas__django-bird-239",
    "keras-team__keras-22138",
    "litestar-org__advanced-alchemy-673_interface",
    "marimo-team__marimo-8387_interface",
    "milvus-io__pymilvus-3273",
]

# Regex for the "Done in" line
# [iid] Done in 657s — MS=83.3% Cov=100.0% rounds=4 bug_detected=True success=True
RE_DONE = re.compile(
    r"\[(?P<iid>[^\]]+)\] Done in (?P<time>\d+)s — "
    r"MS=(?P<ms>[\d.]+)% Cov=(?P<cov>[\d.]+)% "
    r"rounds=(?P<rounds>\d+) "
    r"(?:bug_detected=(?P<bug>\w+) )?"
    r"success=(?P<success>\w+)"
)

# Regex for "Bug detection @" lines
# [iid] Bug detection @ stage: detected=X (fixed_fails=X, buggy_fails=X, new=X)
RE_BUG_DET = re.compile(
    r"\[(?P<iid>[^\]]+)\] Bug detection @ (?P<stage>\S+): "
    r"detected=(?P<detected>\w+) "
    r"\(fixed_fails=(?P<fixed>\d+), buggy_fails=(?P<buggy>\d+), new=(?P<new>\d+)\)"
)

# Regex for "Bug detection:" lines (has new_failures list)
# [iid] Bug detection: detected=X fixed_rc=X buggy_rc=X fixed_fails=X buggy_fails=X new_failures=[...]
RE_BUG_DET_FULL = re.compile(
    r"\[(?P<iid>[^\]]+)\] Bug detection: detected=(?P<detected>\w+) "
    r"fixed_rc=\d+ buggy_rc=\d+ "
    r"fixed_fails=(?P<fixed>\d+) buggy_fails=(?P<buggy>\d+) "
    r"new_failures=(?P<new_failures>.+)"
)

# Regex for "Bug detection summary" line
# [iid] Bug detection summary: detected=X, first_at=X, stages=[...]
RE_BUG_SUMMARY = re.compile(
    r"\[(?P<iid>[^\]]+)\] Bug detection summary: "
    r"detected=(?P<detected>\w+), first_at=(?P<first>\S+),"
)

# Regex for "TC_ENHANCE round N: +X tests"
RE_TC_ROUND = re.compile(
    r"\[(?P<iid>[^\]]+)\] TC_ENHANCE round (?P<round>\d+): \+(?P<count>\d+) tests"
)

# Regex for "MT_ENHANCE round N: +X mutants"
RE_MT_ROUND = re.compile(
    r"\[(?P<iid>[^\]]+)\] MT_ENHANCE round (?P<round>\d+): \+(?P<count>\d+) mutants"
)

# Regex for "Starting pipeline"
RE_START = re.compile(r"\[(?P<iid>[^\]]+)\] Starting pipeline")

# Regex for timestamp
RE_TIMESTAMP = re.compile(r"^(\d{4}-\d{2}-\d{2} \d{2}:\d{2}:\d{2})")


def parse_new_failures(text):
    """Parse new_failures from log text."""
    text = text.strip()
    if text == "none" or text == "[]":
        return []
    # It's a Python list repr like ['a', 'b']
    try:
        return eval(text)
    except Exception:
        return []


def main():
    with open(LOG_PATH, "r") as f:
        lines = f.readlines()

    # We need to identify "runs" -- blocks from "Starting pipeline" to "Done in"
    # for each instance. Multiple instances can be interleaved in batch runs.
    #
    # Strategy: track per-instance state. Each time we see "Starting pipeline"
    # for an instance, we reset its accumulator. When we see "Done in" with
    # success=True, we save the accumulated data as the latest good run.

    # Per-instance accumulators (reset on each "Starting pipeline")
    current = {}  # iid -> accumulator dict
    # Per-instance best results (overwritten each time a successful run completes)
    best = {}  # iid -> result dict

    for line in lines:
        # Check for Starting pipeline
        m = RE_START.search(line)
        if m:
            iid = m.group("iid")
            current[iid] = {
                "detection_entries": [],      # (stage, detected, fixed, buggy, new_failures)
                "detection_at_entries": [],    # (stage, detected, fixed, buggy, new_count)
                "tc_added": 0,
                "mt_added": 0,
                "new_failures_by_stage": {},   # stage -> new_failures list
            }
            continue

        # Check for Bug detection full line (has new_failures list)
        m = RE_BUG_DET_FULL.search(line)
        if m:
            iid = m.group("iid")
            if iid in current:
                nf = parse_new_failures(m.group("new_failures"))
                current[iid]["_last_new_failures"] = nf
            continue

        # Check for Bug detection @ stage line
        m = RE_BUG_DET.search(line)
        if m:
            iid = m.group("iid")
            if iid in current:
                stage = m.group("stage")
                detected = m.group("detected") == "True"
                fixed = int(m.group("fixed"))
                buggy = int(m.group("buggy"))
                new_count = int(m.group("new"))
                # Get new_failures from the preceding full detection line
                nf = current[iid].get("_last_new_failures", [])
                current[iid]["detection_at_entries"].append({
                    "stage": stage,
                    "detected": detected,
                    "fixed_fails": fixed,
                    "buggy_fails": buggy,
                    "new_failures": nf if nf else [],
                })
            continue

        # Check for TC_ENHANCE round
        m = RE_TC_ROUND.search(line)
        if m:
            iid = m.group("iid")
            if iid in current:
                current[iid]["tc_added"] += int(m.group("count"))
            continue

        # Check for MT_ENHANCE round
        m = RE_MT_ROUND.search(line)
        if m:
            iid = m.group("iid")
            if iid in current:
                current[iid]["mt_added"] += int(m.group("count"))
            continue

        # Check for Done line
        m = RE_DONE.search(line)
        if m:
            iid = m.group("iid")
            success = m.group("success") == "True"
            if success and iid in current:
                acc = current[iid]
                ms = float(m.group("ms"))
                cov = float(m.group("cov"))
                rounds = int(m.group("rounds"))
                bug_str = m.group("bug")
                bug_detected = bug_str == "True" if bug_str else False

                # Build detection_history
                detection_history = acc["detection_at_entries"]

                # Find first_detected_at
                first_detected = None
                for entry in detection_history:
                    if entry["detected"]:
                        first_detected = entry["stage"]
                        break

                result = {
                    "instance_id": iid,
                    "success": True,
                    "mutation_score": ms,
                    "line_coverage": cov,
                    "rounds": rounds,
                    "tests_added": acc["tc_added"],
                    "mutants_added": acc["mt_added"],
                    "bug_detected": bug_detected,
                    "error": None,
                    "first_detected_at": first_detected,
                    "detection_history": detection_history,
                }
                best[iid] = result
            continue

    # Build final results list in the order of INSTANCES
    results = []
    for iid in INSTANCES:
        if iid in best:
            results.append(best[iid])
        else:
            print(f"WARNING: No successful run found for {iid}", file=sys.stderr)
            results.append({
                "instance_id": iid,
                "success": False,
                "mutation_score": 0.0,
                "line_coverage": 0.0,
                "rounds": 0,
                "tests_added": 0,
                "mutants_added": 0,
                "bug_detected": False,
                "error": "no_successful_run",
                "first_detected_at": None,
                "detection_history": [],
            })

    # Save
    os.makedirs(os.path.dirname(OUTPUT_PATH), exist_ok=True)
    with open(OUTPUT_PATH, "w") as f:
        json.dump(results, f, indent=2)

    # Print summary
    print(f"{'='*60}")
    print(f"COMPILED RESULTS")
    print(f"{'='*60}")
    success_count = sum(1 for r in results if r["success"])
    bug_count = sum(1 for r in results if r["bug_detected"])
    for r in results:
        det_stages = len(r["detection_history"])
        print(
            f"  {r['instance_id']:55s} "
            f"success={r['success']!s:5s} "
            f"MS={r['mutation_score']:5.1f}% "
            f"Cov={r['line_coverage']:5.1f}% "
            f"rounds={r['rounds']} "
            f"bug={r['bug_detected']!s:5s} "
            f"TC+={r['tests_added']:2d} MT+={r['mutants_added']:2d} "
            f"det_stages={det_stages}"
        )
    print(f"{'='*60}")
    print(f"Total: {success_count}/{len(results)} succeeded, {bug_count}/{len(results)} bugs detected")
    print(f"Results saved to {OUTPUT_PATH}")


if __name__ == "__main__":
    main()

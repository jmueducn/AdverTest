#!/bin/bash
# Run SWE-bench evaluation on AdverTest-generated test patches.
#
# Usage:
#   bash run_harness.sh                    # evaluate all predictions
#   bash run_harness.sh --max_workers 8    # with more parallelism

set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
SWEBENCH_DIR="${SCRIPT_DIR}"
DATASET="${SCRIPT_DIR}/data/rebench_leaderboard_2026_02_top30.jsonl"
PREDICTIONS="${SCRIPT_DIR}/outputs/predictions.jsonl"
RUN_ID="advertest_python"

if [ ! -f "$PREDICTIONS" ]; then
    echo "ERROR: predictions file not found: $PREDICTIONS"
    echo "Run generate_tests.py first."
    exit 1
fi

echo "=== AdverTest SWE-Rebench Evaluation ==="
echo "Predictions: $PREDICTIONS"
echo "Dataset: $DATASET"
echo "Run ID: $RUN_ID"
echo ""

cd "$SWEBENCH_DIR"

python -m swebench.harness.run_evaluation \
    --evaluation_mode test_patch \
    --dataset_name "$DATASET" \
    --predictions_path "$PREDICTIONS" \
    --max_workers "${1:-4}" \
    --run_id "$RUN_ID" \
    --timeout 1800 \
    --cache_level instance \
    --namespace "swerebench" \
    "$@"

echo ""
echo "=== Evaluation complete ==="
echo "Check logs in: ${SWEBENCH_DIR}/logs/run_evaluation/${RUN_ID}/"

import re
from datetime import datetime
import pandas as pd
import matplotlib.pyplot as plt

LOG_FILE = "Ablation_Math_all.log"

# ------- 1. pattern: timestamp, project-id, round -------
result_pat = re.compile(
    r"^(\d{4}-\d{2}-\d{2} \d{2}:\d{2}:\d{2},\d{3})\s+- "
    r"Project name:\s*\w+,\s*Project ID:\s*(\d+),\s*Round:(\d+),\s*result:"
)

records = []
with open(LOG_FILE, encoding="utf-8") as fh:
    for line in fh:
        m = result_pat.search(line)
        if m:
            ts_str, pid, rnd = m.groups()
            records.append(
                {
                    "timestamp": datetime.strptime(ts_str, "%Y-%m-%d %H:%M:%S,%f"),
                    "project_id": int(pid),
                    "round": int(rnd),
                }
            )

df = pd.DataFrame(records)
if df.empty:
    raise SystemExit("No matching result lines found.")

# ------- 2.  Δt per project, then average by transition -------
df = df.sort_values(["project_id", "round"])
df["delta_sec"] = (
    df.groupby("project_id")["timestamp"]
      .diff()
      .dt.total_seconds()
)

# drop the NaN for each project's first round
df = df.dropna(subset=["delta_sec"])

# tag each delta with its transition label (0→1, 1→2, …)
df["transition"] = df["round"] - 1

avg_intervals = (
    df.groupby("transition")["delta_sec"]
      .mean()
      .reset_index()
      .rename(columns={"transition": "round_k_to_k+1",
                       "delta_sec": "avg_seconds"})
)

avg_intervals.to_csv("avg_round_intervals.csv", index=False)
print("Average inter-round intervals (sec):")
print(avg_intervals.to_string(index=False))
plt.figure(figsize=(6, 4))
plt.bar(avg_intervals['round_k_to_k+1'], avg_intervals['avg_seconds'])
plt.xticks(avg_intervals['round_k_to_k+1'],
           [f"{int(k)}→{int(k+1)}" for k in avg_intervals['round_k_to_k+1']])
plt.xlabel('Round Transition')
plt.ylabel('Average Time (seconds)')
plt.title('Average Inter-Round Time Per Transition')
plt.grid(axis='y', linestyle='--', linewidth=0.5)
plt.tight_layout()
png_file = "avg_inter_round_time.png"
plt.savefig(png_file, dpi=300, bbox_inches='tight')
plt.figure(figsize=(6, 4))
plt.scatter(avg_intervals["round_k_to_k+1"], avg_intervals["avg_seconds"])

# Pretty x-tick labels: “0→1”, “1→2”, …
plt.xticks(
    avg_intervals["round_k_to_k+1"],
    [f"{k}→{k+1}" for k in avg_intervals["round_k_to_k+1"]]
)

plt.xlabel("Round Transition")
plt.ylabel("Average Time (seconds)")
plt.title("Average Inter-Round Time (Scatter)")
plt.grid(axis="y", linestyle="--", linewidth=0.5)
plt.tight_layout()

png_scatter = "avg_inter_round_time_scatter.png"
plt.savefig(png_scatter, dpi=300, bbox_inches="tight")
print(f"scatter saved: {png_scatter}")
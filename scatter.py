import re, matplotlib.pyplot as plt
from datetime import datetime
import pandas as pd

LOG_FILE = "Ablation_Math_all.log"

pat = re.compile(
    r"^(\d{4}-\d{2}-\d{2} \d{2}:\d{2}:\d{2},\d{3})\s+- "
    r"Project name:\s*\w+,\s*Project ID:\s*(\d+),\s*Round:(\d+),\s*result:"
)

rows = []
with open(LOG_FILE, encoding="utf-8") as f:
    for line in f:
        m = pat.search(line)
        if m:
            ts_str, pid, rnd = m.groups()
            rows.append(
                {
                    "timestamp": datetime.strptime(ts_str, "%Y-%m-%d %H:%M:%S,%f"),
                    "project_id": int(pid),
                    "round": int(rnd),
                }
            )

df = pd.DataFrame(rows).sort_values(["project_id", "round"])
df["delta_sec"] = (
    df.groupby("project_id")["timestamp"]
      .diff()
      .dt.total_seconds()
)
df = df.dropna(subset=["delta_sec"])
df["transition"] = df["round"] - 1

plt.figure(figsize=(7, 4.5))
jitter = (df.groupby(["project_id", "transition"]).ngroup() % 7 - 3) * 0.05
plt.scatter(
    df["transition"] + jitter,
    df["delta_sec"],
    color='steelblue',
    edgecolors='k',
    alpha=0.8
)

transitions = sorted(df["transition"].unique())
plt.xticks(
    transitions,
    [f"{k}→{k+1}" for k in transitions]
)
plt.yscale('log')  # log scale for y-axis
plt.xlabel("Round Transition")
plt.ylabel("Time per Round (seconds, log scale)")
plt.title("Round Time per Project (log scale)")
plt.grid(axis="y", linestyle="--", linewidth=0.5)
plt.tight_layout()

scatter_png = "inter_round_time_per_project_scatter_log.png"
plt.savefig(scatter_png, dpi=300, bbox_inches="tight")
scatter_png

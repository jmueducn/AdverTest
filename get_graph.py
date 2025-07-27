import pandas as pd
import re
import matplotlib.pyplot as plt

# 1. 读日志
with open('Ablation_Math_all.log', 'r', encoding='utf-8') as f:
    lines = f.readlines()

# 2. 提取
metrics_pat = re.compile(r'Project ID:\s*\d+,\s*MS:([\d\.]+)\s*CV:([\d\.]+),\s*Round:(\d+)')
results_pat = re.compile(r'Project ID:\s*\d+,\s*Round:(\d+),\s*result:\s*(\d)')

metrics, results = [], []
for L in lines:
    m = metrics_pat.search(L)
    if m:
        ms_str, cv_str, rnd_str = m.groups()
        # 原始 round 减 1
        rnd = int(rnd_str) - 1
        metrics.append({
            'round': rnd,
            'MS': float(ms_str),
            'CV': float(cv_str)
        })
    r = results_pat.search(L)
    if r:
        rnd, res = r.groups()
        results.append({'round': int(rnd), 'result': int(res)})

metrics_df = pd.DataFrame(metrics)
results_df = pd.DataFrame(results)

# 3. 分 round 计算
avg_metrics = metrics_df.groupby('round').agg(avg_MS=('MS','mean'),
                                              avg_CV=('CV','mean')).reset_index()
success_prop = results_df.groupby('round').agg(success_rate=('result','mean')).reset_index()

# **外连接** 并填 0
summary_df = pd.merge(avg_metrics, success_prop, on='round', how='outer').sort_values('round')
summary_df[['avg_MS','avg_CV']] = summary_df[['avg_MS','avg_CV']].fillna(0)

# …前面数据准备同上…

# 把 success_rate 转成百分比
summary_df['success_pct'] = summary_df['success_rate'] * 100
csv_name = f'round_summary_Math.csv'
summary_df.to_csv(csv_name, index=False)
print(f"已将汇总表保存为：{csv_name}")
plt.figure()
plt.plot(summary_df['round'], summary_df['avg_MS'], marker='o', label='Average MS')
plt.plot(summary_df['round'], summary_df['avg_CV'], marker='o', label='Average CV')
plt.plot(summary_df['round'], summary_df['success_pct'], marker='o', label='Success Rate (%)')

# 强制只用整数刻度
plt.xticks(summary_df['round'])
# 在每个点上添加数值标签
for x, y in zip(summary_df['round'], summary_df['avg_MS']):
    plt.text(x, y, f"{y:.1f}", ha='center', va='bottom')
for x, y in zip(summary_df['round'], summary_df['avg_CV']):
    plt.text(x, y, f"{y:.1f}", ha='center', va='bottom')
for x, y in zip(summary_df['round'], summary_df['success_pct']):
    plt.text(x, y, f"{y:.1f}", ha='center', va='bottom')
plt.xlabel('Round')
plt.ylabel('Value (%)')
plt.title('Metrics by Iteration Rounds')
plt.legend()
plt.grid(True)
plt.tight_layout()
plt.savefig('metrics_success_pct_by_round.png')
plt.show()

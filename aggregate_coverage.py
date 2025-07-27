#!/usr/bin/env python3
# -*- coding: utf-8 -*-

import os
import sys
import json
import argparse
import re


def main():
    parser = argparse.ArgumentParser(
        description="根据已生成的 coverage_report.json，汇总一个项目组下所有实例的覆盖率报告并输出聚合结果")
    parser.add_argument('gen_method',
                        
                        help="测试用例生成方法，对应 coverage_result 下的子目录")
    parser.add_argument('project_prefix',
                        help="项目组前缀，如 Chart、Cli")
    parser.add_argument('--cov-dir', default='coverage_result',
                        help="存放各项目 coverage_report.json 的根目录")
    parser.add_argument('--report-name', default='coverage_report.json',
                        help="每个实例的单项目报告文件名，默认 coverage_report.json")
    args = parser.parse_args()

    base_dir = os.path.join(args.cov_dir, args.gen_method)
    if not os.path.isdir(base_dir):
        print(f"[ERROR] 找不到目录: {base_dir}", file=sys.stderr)
        sys.exit(1)

    # 匹配所有实例目录
    pattern = re.compile(rf"^{re.escape(args.project_prefix)}(\d+)$")
    ids = []
    for name in os.listdir(base_dir):
        m = pattern.match(name)
        if m and os.path.isdir(os.path.join(base_dir, name)):
            ids.append(int(m.group(1)))
    ids.sort()
    if not ids:
        print(f"[ERROR] 在 {base_dir} 中未找到任何 {args.project_prefix}* 子目录", file=sys.stderr)
        sys.exit(1)

    # 聚合字段
    total_lines = covered_lines = 0
    total_branches = covered_branches = 0
    count = 0

    for i in ids:
        inst_dir = os.path.join(base_dir, f"{args.project_prefix}{i}")
        report_path = os.path.join(inst_dir, args.report_name)
        if not os.path.isfile(report_path):
            print(f"[WARN] 缺少报告，跳过: {report_path}", file=sys.stderr)
            #report_path = os.path.join("coverage_result/ourgen/noiter/Math%s"%(i), args.report_name)
            continue
            if not os.path.isfile(report_path):
                print(f"[WARN] 缺少报告，跳过: {report_path}", file=sys.stderr)
                #report_path = os.path.join("coverage_result/nomut/Math%s"%(i), args.report_name)
                continue
        with open(report_path, 'r', encoding='utf-8') as f:
            try:
                data = json.load(f)
            except json.JSONDecodeError:
                print(f"[WARN] 无法解析 JSON, 跳过: {report_path}", file=sys.stderr)
                continue
        # 累加
        total_lines += data.get('total_lines', 0)
        covered_lines += data.get('covered_lines', 0)
        total_branches += data.get('total_branches', 0)
        covered_branches += data.get('covered_branches', 0)
        count += 1

    if count == 0:
        print(f"[ERROR] 未找到任何有效的覆盖报告", file=sys.stderr)
        sys.exit(1)

    # 计算汇总比例
    line_rate = covered_lines / total_lines if total_lines else 0.0
    branch_rate = covered_branches / total_branches if total_branches else 0.0

    summary = {
        'instances_count': count,
        'total_lines': total_lines,
        'covered_lines': covered_lines,
        'total_branches': total_branches,
        'covered_branches': covered_branches,
        'line_rate': line_rate,
        'branch_rate': branch_rate
    }

    out_name = f"{args.project_prefix}_aggregate_report.json"
    out_path = os.path.join(base_dir, out_name)
    with open(out_path, 'w', encoding='utf-8') as f:
        json.dump(summary, f, ensure_ascii=False, indent=2)

    print(f"[OK] 聚合报告已写入: {out_path}")
    print(json.dumps(summary, indent=2, ensure_ascii=False))

if __name__ == '__main__':
    main()

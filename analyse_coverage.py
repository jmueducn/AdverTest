#!/usr/bin/env python3
# -*- coding: utf-8 -*-

import os
import sys
import json
import argparse
import xml.etree.ElementTree as ET
import re


def load_method_names(meta_path):
    """从 meta.json 中取出所有的方法名称"""
    with open(meta_path, 'r', encoding='utf-8') as f:
        data = json.load(f)
    methods = list(data.get('idx_to_method_name', {}).values())
    if not methods:
        print(f"[ERROR] 在 {meta_path} 中找不到任何方法", file=sys.stderr)
        sys.exit(1)
    return set(methods)


def extract_coverage_entries(methods, xml_path):
    """
    解析 coverage.xml，返回四个集合：
      valid_lines, covered_lines,
      valid_branches, covered_branches
    """
    tree = ET.parse(xml_path)
    root = tree.getroot()

    # namespace 处理
    ns = {}
    m = re.match(r'\{(.+)\}', root.tag)
    if m:
        ns['c'] = m.group(1)

    valid_lines = set()
    covered_lines = set()
    valid_branches = set()
    covered_branches = set()

    # 遍历所有类和方法
    for cls in root.findall('.//class', ns):
        for method in cls.findall('.//method', ns):
            name = method.get('name')
            if name not in methods:
                continue
            # 行与分支
            for line in method.findall('.//line', ns):
                ln = int(line.get('number', '0'))
                key_line = (name, ln)
                valid_lines.add(key_line)
                hits = int(line.get('hits', '0'))
                if hits > 0:
                    covered_lines.add(key_line)
                if line.get('branch') == 'true':
                    cc = line.get('condition-coverage', '')
                    m2 = re.search(r"\((\d+)/(\d+)\)", cc)
                    if m2:
                        cov, tot = map(int, m2.groups())
                        for idx in range(tot):
                            valid_branches.add((name, ln, idx))
                        for idx in range(cov):
                            covered_branches.add((name, ln, idx))
    return valid_lines, covered_lines, valid_branches, covered_branches


def main():
    parser = argparse.ArgumentParser(
        description="生成所有方法的总覆盖率报告（支持多方法累加, 精确行/分支统计）")
    parser.add_argument('gen_method')
    parser.add_argument('project_name')
    parser.add_argument('project_id')
    parser.add_argument('--meta-dir', default='metainfo')
    parser.add_argument('--cov-dir', default='coverage_result')
    parser.add_argument('--out-name', default='coverage_report.json')
    args = parser.parse_args()

    meta_file = os.path.join(args.meta_dir, f"{args.project_name}{args.project_id}fmeta.json")
    cov_xml = os.path.join(
        args.cov_dir, args.gen_method,
        f"{args.project_name}{args.project_id}", 'coverage_coverage.xml'
    )

    if not os.path.isfile(meta_file) or not os.path.isfile(cov_xml):
        print(f"[ERROR] 找不到文件: {meta_file} 或 {cov_xml}", file=sys.stderr)
        sys.exit(1)

    methods = load_method_names(meta_file)
    vl, cl, vb, cb = extract_coverage_entries(methods, cov_xml)

    # 统计覆盖数据，即使无条目也输出零值报告
    total_lines = len(vl)
    covered_lines = len(cl)
    total_branches = len(vb)
    covered_branches = len(cb)
    line_rate = covered_lines / total_lines if total_lines else 0.0
    branch_rate = covered_branches / total_branches if total_branches else 0.0

    report = {
        'total_lines': total_lines,
        'covered_lines': covered_lines,
        'total_branches': total_branches,
        'covered_branches': covered_branches,
        'line_rate': line_rate,
        'branch_rate': branch_rate
    }

    out_dir = os.path.dirname(cov_xml)
    out_path = os.path.join(out_dir, args.out_name)
    with open(out_path, 'w', encoding='utf-8') as f:
        json.dump(report, f, ensure_ascii=False, indent=2)

    print(f"[OK] 汇总覆盖率报告已写入: {out_path}")

if __name__ == '__main__':
    main()
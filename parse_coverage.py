#!/usr/bin/env python3
import xml.etree.ElementTree as ET
import re

def parse_coverage(xml_path, target_methods):
    tree = ET.parse(xml_path)
    root = tree.getroot()

    total_lines = covered_lines = 0
    total_branches = covered_branches = 0

    
    for m in root.findall(".//method"):
        name = m.get("name")
        if name not in target_methods:
            continue

        
        lines = m.findall(".//line")
        for ln in lines:
            total_lines += 1
            
            if int(ln.get("hits", "0")) > 0:
                covered_lines += 1

            
            if ln.get("branch") == "true":
                cond = ln.get("condition-coverage", "")
                m2 = re.search(r"\((\d+)/(\d+)\)", cond)
                if m2:
                    a, b = map(int, m2.groups())
                    covered_branches += a
                    total_branches   += b

    return {
        "line_rate":    covered_lines / total_lines * 100 if total_lines else 0,
        "branch_rate":  covered_branches / total_branches * 100 if total_branches else 0,
        "lines":        (covered_lines, total_lines),
        "branches":     (covered_branches, total_branches)
    }

if __name__ == "__main__":
    import sys
    if len(sys.argv) < 3:
        print("Usage: python calc_subset_cov.py coverage.xml method1 [method2 ...]")
        sys.exit(1)

    xml_file = sys.argv[1]
    method_list = sys.argv[2:]
    res = parse_coverage(xml_file, method_list)

    print(f"Methods: {', '.join(method_list)}")
    print(f"Line Coverage:    {res['lines'][0]}/{res['lines'][1]} = {res['line_rate']:.2f}%")
    print(f"Branch Coverage:  {res['branches'][0]}/{res['branches'][1]} = {res['branch_rate']:.2f}%")

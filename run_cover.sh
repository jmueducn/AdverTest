#!/usr/bin/env bash
set -euo pipefail

# 三种测试用例生成方法
methods=(ChatUniTest Evosuite ourmethod)

# 各项目名称与范围
declare -A projects=(
  [Chart]=27
  [Cli]=40
  [Compress]=47
  [Math]=106
  [JxPath]=22
  [Gson]=18
  [Csv]=16
)

for method in "${methods[@]}"; do
  echo "===== Running coverage for method: $method ====="
  for base in "${!projects[@]}"; do
    max=${projects[$base]}
    echo "-- Project group: $base (1..$max) --"
    for i in $(seq 1 $max); do
      proj="${base}${i}f"
      echo "  [$method] $proj"
      # 捕获 python 返回码，失败时打印 WARN 并继续下一次
      if ! python analyse_coverage.py \
          "$method" \
          "$base" \
          "$i"; then
        echo "  [WARN] $method $proj 处理失败，跳过" >&2
      fi
    done
  done
done

echo "All coverage runs completed."

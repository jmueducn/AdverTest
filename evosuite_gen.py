import subprocess

def run_evosuite(project, bug_id, output_dir, budget):
    """
    调用 Defects4J 的 gen_tests.pl 脚本，利用 EvoSuite 为指定项目生成测试用例。
    
    参数:
      project (str): 项目标识符，例如 "Chart"
      bug_id (str): Bug ID 或版本号，例如 "3f"
      output_dir (str): 测试用例输出目录
      budget (int): 时间预算（秒）
    """
    bug_id = str(bug_id)
     # 如果 bug_id 最后不是 'f'，则添加 'f'
    if not bug_id.endswith("f"):
        bug_id += "f"

    num_tests = 50  
    # 构造命令
    cmd = [
        "gen_tests.pl",
        "-g", "evosuite",
        "-p", project,
        "-v", bug_id,
        "-n", str(num_tests),
        "-o", output_dir,
        "-b", str(budget)
    ]
    try:
        print("执行命令:", " ".join(cmd))
        result = subprocess.run(cmd, check=True,
                                stdout=subprocess.PIPE,
                                stderr=subprocess.PIPE,
                                text=True)
        print("EvoSuite 测试生成成功。")
        print("标准输出:\n", result.stdout)
    except subprocess.CalledProcessError as e:
        print("调用 gen_tests.pl 出现错误:")
        print(e.stderr)


# 如果希望直接通过命令行调用此脚本，可以添加以下代码：
if __name__ == "__main__":
    # import argparse
    # parser = argparse.ArgumentParser(description="使用 EvoSuite 生成测试用例")
    # parser.add_argument("-p", "--project", required=True, help="项目标识符，例如 Chart")
    # parser.add_argument("-i", "--id", required=True, help="Bug ID 或版本号，例如 3f")
    # parser.add_argument("-o", "--output_dir", required=True, help="输出目录")
    # parser.add_argument("-b", "--budget", type=int, required=True, help="时间预算（秒）")
    # args = parser.parse_args()
    
    #run_evosuite(args.project, args.id, args.output_dir, args.budget)
    for i in range(39,44):
        run_evosuite('Cli', i+1, './output', 100)

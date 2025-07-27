import subprocess
from concurrent.futures import ThreadPoolExecutor, as_completed
import os


def run_evosuite(project: str, bug_id: int, output_dir: str, budget: int) -> None:
    """
    using Defects4J gen_tests.pl，exploit EvoSuite to generate tests for a specific bug in a project.

    param:
      project (str): "Chart"
      bug_id (int): Bug ID, 3
      output_dir (str): output directory
      budget (int): time  budget
    """
    
    os.makedirs(output_dir, exist_ok=True)

    bug_str = str(bug_id)
    if not bug_str.endswith("f"):
        bug_str += "f"

    num_tests = 50  
    cmd = [
        "gen_tests.pl",
        "-g", "randoop",
        "-p", project,
        "-v", bug_str,
        "-n", str(num_tests),
        "-o", output_dir,
        "-b", str(budget)
    ]
    try:
        print(f"[START] {project} {bug_str}")
        result = subprocess.run(
            cmd,
            check=True,
            stdout=subprocess.PIPE,
            stderr=subprocess.PIPE,
            text=True,
            timeout=500
        )
        print(f"[DONE]  {project} {bug_str}: Successfully generated tests.")
    except subprocess.TimeoutExpired:
        print(f"[TIMEOUT] {project} {bug_str}: Over budget, tests generation took too long.")
    except subprocess.CalledProcessError as e:
        print(f"[ERROR] {project} {bug_str}: Error during test generation.")
        print(e.stderr)


def main():
    #  (project, bug_id, output_dir, budget)
    tasks = []
    # Math: IDs 3f ... 106f (i from 2 to 105)
    # for i in range(80, 106):
    #     tasks.append(("Math", i+1, "./output", 100))
    # # Compress: 1f ... 47f
    # for i in range(0, 47):
    #     tasks.append(("Compress", i+1, "./output", 100))
    # # Gson: 1f ... 18f
    # for i in range(0, 18):
    #     tasks.append(("Gson", i+1, "./output", 100))
    # # Csv: 1f ... 16f
    # for i in range(0, 16):
    #     tasks.append(("Csv", i+1, "./output", 100))
    # # JxPath: 1f ... 22f
    # for i in range(0, 22):
    #     tasks.append(("JxPath", i+1, "./output", 100))
    for i in range(0, 30):
        tasks.append(("Beanutils", i+1, "./output", 100))
    for i in range(0, 20):
        tasks.append(("Dbcp", i+1, "./output", 100))    
    for i in range(0, 2):
        tasks.append(("Fileupload", i+1, "./output", 100)) 
    max_workers = min(1, len(tasks))  
    print(f"Start Using {max_workers} Threads generating testcases，total {len(tasks)} tasks.")

    with ThreadPoolExecutor(max_workers=max_workers) as executor:
        futures = [executor.submit(run_evosuite, proj, bug, out, bud) for proj, bug, out, bud in tasks]
        for future in as_completed(futures):
            
            try:
                future.result()
            except Exception as exc:
                print(f"** Exception: {exc}")

    print("All tasks completed in generating tests.")


if __name__ == "__main__":
    main()
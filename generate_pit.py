from langchain import ConversationChain
from langchain.memory import ConversationBufferMemory
from model import *
from function import *
from coverage import *
from enhance_testcase import TC_ENHANCE, enhance_from_details
from enhance_mutants import MT_ENHANCE
from test import bug_detection_ourgen
from concurrent.futures import ThreadPoolExecutor, as_completed
from pathlib import Path
import time
import shutil
import re
from config import our_method_logger



llm = Deepseek(api_key="your api-key",model="deepseek-chat")
ms_standard = 70
coverage_threshold = 100
projname = 'Math'
def run_one_case(proj,projid,mutants_raw,mutants_tested,llm,ms_standard=70,coverage_threshold=95):
    # method_names = set(affected_method_finding(proj, projid))
    # root = Path(f"/home/changpy/data3/MutAGENTest/mutantsgen/Wukong/pitest/Pitest_mutant/{proj}/{proj}{projid+1}")
    # print(root)
    # #result,test_file_path = initial_generate_testcase_def4j(proj,projid,llm)
    # method_re = re.compile(r"method=([^\s,\]]+)")
    # kept_files = []          # 收集符合条件的 details.txt
    # for details_file in root.rglob("*/details.txt"):
    #     txt = details_file.read_text()
        
      
    #     method_m = method_re.search(txt)
    #     #print(method_m)
    #     if not (method_m):
    #         continue

    #     fq_name = f"{method_m.group(1)}"   # 完全限定方法名
    #     if fq_name in method_names:
    #         kept_files.append(details_file)

    # print(f"共找到 {len(kept_files)} 个匹配的 mutants：")
    # for f in kept_files:
    #     print("  ", f)
    # out_base = "./fitered_mutants"
    # out_root = Path(out_base) / f"{proj}{projid+1}"
    
    # for details_file in kept_files:
    #     src_dir  = details_file.parent            # mutant 的目录
    #     rel_path = src_dir.relative_to(root)      # 保持原有层级
    #     dst_dir  = out_root / rel_path
    #     if dst_dir.exists():                      # 若已复制过，跳过
    #         continue
    #     dst_dir.mkdir(parents=True, exist_ok=True)
    #     shutil.copytree(src_dir, dst_dir, dirs_exist_ok=True)
    result,test_file_path = initial_generate_testcase_def4j(proj,projid,llm)
    print("ww")
    cnt = 0
    flag = 0
    while result == False:
        result = initial_generate_testcase_def4j(proj,projid,llm)
        cnt+=1
        if cnt == 3:
           flag = 1
           break
        if result == True:
           break
    if flag == 1:
        return -1
    out_base   = Path("./fitered_mutants")
    out_root   = out_base / f"{proj}{projid+1}"
    proj_root  = Path(f"/home/changpy/data3/MutAGENTest/src/defects4j_fixed/"
                      f"{proj}/{proj}_{projid+1}_fixed")
    src_root   = proj_root / "src/main/java"   # 如有差异请调整
    test_file  = test_file_path                # initial_generate_testcase_def4j 返回的路径

    for dt in out_root.rglob("details.txt"):
        print(f"[增强] {dt.relative_to(out_root)}")
        try:
            resp, errors = enhance_from_details(
                test_file        = str(test_file),
                details_txt      = str(dt),
                project_src_root = str(src_root),
                llm              = llm,
                base_dir         = str(proj_root)
            )
            print(f"    编译状态: {resp}, 错误数: {len(errors)}")
            resp, errors = enhance_from_details(
                test_file        = str(test_file),
                details_txt      = str(dt),
                project_src_root = str(src_root),
                llm              = llm,
                base_dir         = str(proj_root)
            )
            print(f"    编译状态: {resp}, 错误数: {len(errors)}")
        except Exception as e:
            print(f"    失败: {e}")
    result = bug_detection_ourgen(proj,projid)
    print("bug detection result",result)
    return result
#defects4j
# llm = deepseek(api_key="your api-key")
# generate_mutant('Chart',0,'./',llm)

# llm = StarChat(model_path='path')
# generate_mutant('Chart',0,'./',llm)

# llm = CodeLlama13B(model_path='path')
# generate_mutant('Chart',0,'./',llm)


def process_project(projid):
    try:
        res = run_one_case(projname, projid, './Mutants/GPT3.5/raw', './Mutants/GPT3.5/tested', llm, ms_standard, coverage_threshold)
        if res == -1:
            our_method_logger.info(f'Project name: {projname}, Project ID: {projid+1}, Result: Not successful generated')
        elif res == 0:
            our_method_logger.info(f'Project name: {projname}, Project ID: {projid+1}, Result: Generated, failed')
        else:
            our_method_logger.info(f'Project name: {projname}, Project ID: {projid+1}, Result: Success')
    except Exception as e:
        our_method_logger.info(f'Project name: {projname}, Project ID: {projid+1}, Result: error {e}')
        print(f'Project name: {projname}, Project ID: {projid+1}, Result: error {e}')

with ThreadPoolExecutor(max_workers=32) as executor:
    futures = [executor.submit(process_project, i) for i in range(0,106)]
    for future in as_completed(futures):
        future.result()  
    

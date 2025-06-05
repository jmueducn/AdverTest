from langchain import ConversationChain
from langchain.memory import ConversationBufferMemory
from model import *
from function import *
from coverage import *
from enhance_testcase import TC_ENHANCE
from enhance_mutants import MT_ENHANCE
from test import bug_detection_ourgen
from concurrent.futures import ThreadPoolExecutor, as_completed
import time
from config import our_method_logger



llm = Deepseek(api_key="your api-key",model="deepseek-chat")
ms_standard = 70
coverage_threshold = 95
projname = 'Math'
def run_one_case(proj,projid,mutants_raw,mutants_tested,llm,ms_standard=70,coverage_threshold=95):
    mutated_lines = initial_generate_mutant_def4j(proj,projid,mutants_raw,llm)
    print(mutated_lines)

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
    if mutated_lines == -1 :
        result = bug_detection_ourgen(proj,projid)
        print("unable to generate mutants,just test the original test",result)
        our_method_logger.info(f'Project name: {projname}, Project ID: {projid+1}, unable to generate mutants,just test the original test')
        return result
    mutation_score = running_mutants(proj,projid,mutants_raw,mutants_tested)
    if mutation_score == -1:
        result = bug_detection_ourgen(proj,projid)
        print("unable to generate mutants,just test the original test",result)
        our_method_logger.info(f'Project name: {projname}, Project ID: {projid+1}, unable to generate mutants,just test the original test')
        return result
    
    uncovered,coverage = coverage_process(proj,projid,mutated_lines)
    print("MS:",mutation_score)
    print("未被覆盖的行号列表:", uncovered)
    print("cvg rate:",coverage,"%")
    cnt_T = 0
    cnt_M = 0
    our_method_logger.info(f'Project name: {projname}, Project ID: {projid+1}, MS:{mutation_score} CV:{coverage},Round:{cnt_T+cnt_M+1},TC: {cnt_T},MT: {cnt_M}')
    result = bug_detection_ourgen(proj,projid)
    our_method_logger.info(f'Project name: {projname}, Project ID: {projid+1}, Round:{cnt_T+cnt_M}, result: {result}')
    last_M = 0
    while 1:
        
        if mutation_score>= ms_standard:
            MT_ENHANCE(proj,projid,mutants_raw,mutants_tested,uncovered,llm)
            cnt_M += 1
            last_M = 1
        else:
            TC_ENHANCE(proj,projid,'./defects4j_fixed',test_file_path,mutants_tested,llm)
            cnt_T += 1
            last_M = 0
        mutation_score = running_mutants(proj,projid,mutants_raw,mutants_tested)

        uncovered,coverage = coverage_process(proj,projid,mutated_lines)
        print("未被覆盖的行号列表:", uncovered)
        print("cvg rate:",coverage,"%")
        our_method_logger.info(f'Project name: {projname}, Project ID: {projid+1}, MS:{mutation_score} CV:{coverage},Round:{cnt_T+cnt_M+1},TC: {cnt_T},MT: {cnt_M}')
        result = bug_detection_ourgen(proj,projid)
        our_method_logger.info(f'Project name: {projname}, Project ID: {projid+1}, Round:{cnt_T+cnt_M}, result: {result}')
        if cnt_M+cnt_T >= 4:
            # if cnt_T < cnt_M  or last_M == 1:
            #     TC_ENHANCE(proj,projid,'./defects4j_fixed',test_file_path,mutants_tested,llm)
            #     cnt_T+=1
            #     mutation_score = running_mutants(proj,projid,mutants_raw,mutants_tested)
            #     uncovered,coverage = coverage_process(proj,projid,mutated_lines)
            #     our_method_logger.info(f'Project name: {projname}, Project ID: {projid+1}, MS:{mutation_score} CV:{coverage},Round:{cnt_T+cnt_M+1},TC: {cnt_T},MT: {cnt_M}')
            #     result = bug_detection_ourgen(proj,projid)
            #     our_method_logger.info(f'Project name: {projname}, Project ID: {projid+1}, Round:{cnt_T+cnt_M}, result: {result}')
            break
        # if coverage>coverage_threshold and mutation_score>= ms_standard:
        #     if cnt_T == 0:
        #         TC_ENHANCE(proj,projid,'./defects4j_fixed',test_file_path,mutants_tested,llm)
        #         mutation_score = running_mutants(proj,projid,mutants_raw,mutants_tested)
        #         uncovered,coverage = coverage_process(proj,projid,mutated_lines)
        #         cnt_T+=1
        #         our_method_logger.info(f'Project name: {projname}, Project ID: {projid+1}, MS:{mutation_score} CV:{coverage},Round:{cnt_T+cnt_M+1},TC: {cnt_T},MT: {cnt_M}')
        #         result = bug_detection_ourgen(proj,projid)
        #         our_method_logger.info(f'Project name: {projname}, Project ID: {projid+1}, Round:{cnt_T+cnt_M}, result: {result}')
        #         break
        #     break
        
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

with ThreadPoolExecutor(max_workers=8) as executor:
    futures = [executor.submit(process_project, i) for i in range(0, 106)]
    for future in as_completed(futures):
        future.result()  
    
# llm = GPT(api_key="your api-key",model="GPT-4o")
# generate_mutant('Chart',0,'./',llm)

# llm = GPT(api_key="your api-key",model="GPT-4o-mini")
# generate_mutant('Chart',0,'./',llm)

#condefect
# llm = deepseek(api_key="your api-key")
# con_generate_mutant(llm)

# llm = StarChat(model_path='path')
# con_generate_mutant(llm)

# llm = CodeLlama13B(model_path='path')
# con_generate_mutant(llm)

# llm = GPT(api_key="your api-key",model="GPT-3.5-turbo")
# con_generate_mutant(llm)

# llm = GPT(api_key="your api-key",model="GPT-4o")
# con_generate_mutant(llm)

# llm = GPT(api_key="your api-key",model="GPT-4o-mini")
# con_generate_mutant(llm)
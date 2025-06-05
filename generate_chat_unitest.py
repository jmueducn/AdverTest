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
import logging


def run_one_case(proj,projid,mutants_raw,mutants_tested,llm,ms_standard=70,coverage_threshold=95):
    

    result,test_file_path = initial_generate_testcase_def4j(proj,projid,llm)
    cnt = 0
    flag = 0
    while result == False:
        result = initial_generate_testcase_def4j(proj,projid,llm)
        cnt+=1
        if cnt == 6:
           flag = 1
           break
        if result == True:
           break
    
    result = bug_detection_ourgen(proj,projid)
    print("test",result)
    return result
#defects4j
# llm = deepseek(api_key="your api-key")
# generate_mutant('Chart',0,'./',llm)

# llm = StarChat(model_path='path')
# generate_mutant('Chart',0,'./',llm)

# llm = CodeLlama13B(model_path='path')
# generate_mutant('Chart',0,'./',llm)

llm = Deepseek(api_key="your api-key",model="deepseek-chat")
our_method_logger = logging.getLogger("our_method_logger")
our_method_logger.setLevel(logging.INFO)
our_method_handler = logging.FileHandler("ChatUniTest_Jxpath.log")
our_method_formatter = logging.Formatter('%(asctime)s - %(message)s')
our_method_handler.setFormatter(our_method_formatter)
our_method_logger.addHandler(our_method_handler)
ms_standard = 70
coverage_threshold = 95
projname = 'JxPath'

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
    futures = [executor.submit(process_project, i) for i in range(0,22)]
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
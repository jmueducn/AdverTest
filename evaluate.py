import subprocess
from tqdm import tqdm
import os
import math
import json
sum_mutant=0
use_mutant=0
def coupling(project,num,mutant_filter_path,d4j_path = '../mutantsgen/Wukong/defects4j'):
    """
    pronject: Project name
    num: Number
    d4j_path : The installation path for Defects4J
    mutant_filter_path: Filtered mutant storage path
    """
    print("--------------------",project,"----------------------")
    trigger_tests=[]
    for i in range(num):
        folder_path = d4j_path + '/framework/projects/%s/trigger_tests/%s' % (project,i+1)
        with open(folder_path, "r", encoding="utf-8") as f:
            data = f.readlines()
        for j in data:
            if "---" in j:
                trigger_tests.append(j.replace('---','').strip())
    global sum_mutant
    global use_mutant
    for i in tqdm(range(num)):
        mutant_path = mutant_filter_path + '/%s/%s_%s_test.json' % (project,project,i+1)
        with open(mutant_path, "r", encoding="utf-8") as f:
            mutant = json.load(f)
        for m in mutant:
            try:
                sum_mutant+=1
                for tri_test in trigger_tests:
                    if tri_test in m["fail_test:"]:
                        use_mutant+=1
                        break
            except Exception as e:
                continue
    
    print('sum mutant:',sum_mutant,'useful mutant',use_mutant,'ratio',use_mutant/sum_mutant)

def find_fault(project,num,mutant_filter_path,d4j_path = '../mutantsgen/Wukong/defects4j'):
    """
    pronject: Project name
    num: Number
    d4j_path : The installation path for Defects4J
    mutant_filter_path: Filtered mutant storage path
    """
    print("--------------------",project,"----------------------")
    kill_tests=[]
    for i in range(num):
        mutant_path = mutant_filter_path + '/%s/%s_%s_test.json' % (project,project,i+1)
        with open(mutant_path, "r", encoding="utf-8") as f:
            mutant = json.load(f)
        for m in mutant:
            try:
                if m['fail_test_number:']>0:
                    test=m['fail_test:'].split('\n')[1:]
                    for t in test:
                        if len(t)>0:
                            if t.split(' ')[-1] not in kill_tests:
                                kill_tests.append(t.split(' ')[-1])
            except Exception as e:
                continue
    find=0
    print('test number:',len(kill_tests))
    for i in tqdm(range(num)):
        folder_path = d4j_path + '/framework/projects/%s/trigger_tests/%s' % (project,i+1)
        with open(folder_path, "r", encoding="utf-8") as f:
            data = f.readlines()
        trigger_tests=[]
        for j in data:
            if "---" in j:
                trigger_tests.append(j.replace('---','').strip())
        for te in trigger_tests:
            if te in kill_tests:
                find+=1
                print("number ",i,"successfully  found")
                break
    print('find fault:',find,'   all:',num)


def ochiai(project,num,mutant_filter_path,d4j_path = '../mutantsgen/Wukong/defects4j'):
    """
    pronject: Project name
    num: Number
    d4j_path : The installation path for Defects4J
    mutant_filter_path: Filtered mutant storage path
    """
    print("--------------------",project,"----------------------")
    fault_similarity_num=0
    mutant_number=0
    mutant_ochiai=0
    mutant_all=[]
    for j in range(num):
        mutant_path = mutant_filter_path + '/%s/%s_%s_test.json' % (project,project,j+1)
        with open(mutant_path, "r", encoding="utf-8") as f:
            mutant = json.load(f)

        for m in mutant:
            mutant_number+=1
            mutant_all.append(m)
    for i in tqdm(range(num)):
        if_fault_similarity=False
        folder_path = d4j_path + '/framework/projects/%s/trigger_tests/%s' % (project,i+1)
        with open(folder_path, "r", encoding="utf-8") as f:
            data = f.readlines()
        trigger_tests=[]
        for j in data:
            if "---" in j:
                trigger_tests.append(j.replace('---','').strip())

        for m in mutant_all:
            try:
                mutant_number+=1
                fenzi=0
                for tri_test in trigger_tests:
                    if tri_test in m['fail_test:']:
                        fenzi+=1 #funny name...
                mutant_fail_number=int(m['fail_test_number:'])
                if mutant_fail_number>0:
                    Ochiai=fenzi/math.sqrt(len(trigger_tests)*mutant_fail_number)
                else:
                    Ochiai=0
                if Ochiai>0.8:
                    if_fault_similarity=True
                    mutant_ochiai+=1
            except Exception as e:
                continue
        if if_fault_similarity:
            fault_similarity_num+=1
    
    print('fault_similarity_num:',fault_similarity_num,'/',num,'mutant_ochiai>0.8',mutant_ochiai,'/',mutant_number)

mutant_filter_path = './Mutants/GPT3.5/filtered'

coupling('Cli',40,mutant_filter_path)
ochiai('Cli',40,mutant_filter_path)
find_fault('Cli',40,mutant_filter_path)
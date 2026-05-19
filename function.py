from openai import OpenAI
import httpx
import os
import time
import re
import glob
import subprocess
import json
import javalang
import shutil
from testIniGenPrompt import *
from extract import *
def initial_generate_mutant_def4j(project,project_id,savepath,llm,
d4j_path = './defects4j',
                                d4jbug_path = './defects4j_fixed'):
    """
    d4j_path : The installation path for Defects4J
    d4jbug_path: The download path for the fixed version projects in Defects4J
    """
    id=0
    print("..............................................................",project,project_id+1,"......................................................................")
    folder_path = d4j_path + '/framework/projects/%s/patches/' % (project)
    all_files = os.listdir(folder_path)
    patch_files = [file for file in all_files if file.endswith('src.patch')]
    mu_list=[]
    mutated_lines = []
    dd=0
    fail=0
    only_once = 0
    for patch_file in patch_files:
        if int(patch_file.split('.')[0])!=project_id+1:
            continue
        dd+=1
        patch_path=os.path.join(folder_path, patch_file)
        print(dd,"****************************",patch_path)
        try:
            with open(patch_path, 'r',errors='ignore') as patch:
                patch_content = patch.readlines()  
        except Exception as e:
            print("patch parsing error")
            continue
        fixed_bug = d4jbug_path + "/%s/%s_" % (project,project) +str(project_id+1)+"_fixed/"+patch_content[2][6:]
        fixed_bug=fixed_bug.strip()
        print(fixed_bug)
        try:
            liness = open(fixed_bug, "r").read().strip().splitlines()
        except Exception as e:
            print(f"patch parsing error: {e}")
            continue

        patch_lines=[]
        # java_classes=[]
        for i in patch_content:
            if "@@" in i:
                patch_lines.append(i)
        try:
            for patch_line in patch_lines:
                split_result = patch_line.split(" ")[1] 
                start_line, mutant_lines = map(int, split_result.split(','))
                start_line=-start_line
                end_line=start_line+mutant_lines-1
                mutant_code=""
                for i in liness[start_line-1:end_line]:
                    mutant_code=mutant_code+i+'\n'
                if(start_line>=len(liness)):
                    continue
                end_line=start_line
                while (start_line>=1) and ('*/' not in liness[start_line-1]):
                    start_line=start_line-1
                while (end_line<len(liness)) and ('/**' not in liness[end_line-1]):
                    end_line=end_line+1
                ori_code=""
                num_lines=(end_line-start_line)-1
                for i in range(start_line,end_line):
                    mutated_lines.append(i)
                for i in liness[start_line:end_line-1]:
                    ori_code=ori_code+i+'\n'
                print(start_line)
                prompt="""
            
            Below is the original code from a Java project. your task is to generate %s mutants in original code(notice:mutant refers to mutant in software engineering, i.e. making subtle alterations to the original code):
            code:java```
            %s\n
            ```

            as follows are some examples of mutants which you can refer to:
                {
                "precode": "n = (n & (n - 1));",
                "aftercode": " n = (n ^ (n - 1));"                                        
                },
                {
                "precode": "  while (!queue.isEmpty()) {",
                "aftercode": " while (true) { "             
                },                                        
                {
                "precode": "return depth==0;",
                "aftercode": "return true;"
                },                                        
                {
                "precode": "ArrayList r = new 
                ArrayList();r.add(first).addll(subset);to_add(r)",
                "aftercode": "to_add.addAll(subset);"
                },                               
                {
                "precode": "c = bin_op.apply(b,a);",
                "aftercode": "c = bin_op.apply(a,b);",    
                },                              
                {
                "precode":"while (Math.abs(x-approx*approx) > epsilon) { "     
                "aftercode": " while (Math.abs(x-approx) > epsilon) {"
                },                          
            #Requirement:
            1.Provide generated mutants directly
            2.A mutation can only occur on one line
            3.Your output must be like:
            "
            [
                {
                    "id":,
                    "line":,
                    "precode":"",
                    "filepath":"kk",
                    "aftercode":""
                }
            ]
            "(both brackets are required)
            Where "id" stand for mutant serlal number,"Line" represent the line number of the mutated(please refer to the original code line number),"precode" represent the line of code before mutation and it can't be empty,"aftercode" represent the line of code after mutation
            4.Prohibit generating the exact same mutants
            5.Do not generate equivalent mutants: for example generating on comments are completely useless.
            6.all write in a json file
            7.Please ensure that your mutant contains ONLY ONE LINE and pay attention to line breaks. Some statements may be split into two lines. 
            """%(min(num_lines,55),ori_code)
                num=0
                for tem in range(3): 
                    try:
                        generated_text = llm._call(prompt)
                        pattern = r'\[.*\]'
                        print("Generated text real",generated_text)
                        generated_text = re.findall(pattern,generated_text,re.DOTALL)
                        print("Generated text",generated_text)
                        try:
                            mu=json.loads(generated_text[0])
                            added = []
                            for i in range(len(mu)):
                                id+=1
                                mu[i]['id']=id
                                mu[i]['filepath']=fixed_bug
                                flag_for_add = 0

                                for k in range(num_lines):
                                    if mu[i]['precode'] in liness[start_line+k]:#It is a bug ...... OK......
                                        mu[i]['line']=start_line+k+1
                                        flag_for_add = 1
                                        continue
                                if flag_for_add == 1:
                                    added.append(mu[i])
                                else:
                                    id-=1
                            mu_list.extend(added)
                            break
                        except Exception as e:
                            num+=1
                            print
                            print("JSON Content Error",e) #It marked as JSON content Error Here. So it must be an error in upper
                            continue
                    except Exception as e:
                        print("LLMGEN Error",e)
                        num+=1
                        continue
                if num>=5:
                    fail+=1
        except Exception as e:
            print("the unshixianed generate mutation from whole class.")
            return -1
            
    save_path = savepath + '/%s/%s-' % (project,project) +str(project_id+1)+'.json' 
    print(save_path)
    with open(save_path, 'w', encoding='utf-8') as file:
        json.dump(mu_list, file, ensure_ascii=False,indent=4)
    print("fail times:",fail)
    return mutated_lines


def extract_methods_and_lines(java_file_path):
  
    if not os.path.isfile(java_file_path):
        print(f"File not found: {java_file_path}")
        return []

    with open(java_file_path, 'r', encoding='utf-8') as file:
        content = file.read()

    try:
        tree = javalang.parse.parse(content)
    except javalang.parser.JavaSyntaxError as e:
        print(f"Extraction error: {e}")
        return []

    methods = []
    for path, node in tree.filter(javalang.tree.MethodDeclaration):
        start_line = node.position.line

        if node.body:
            end_line = get_max_line(node)
            methods.append({
                'name': node.name,
                'start_line': start_line,
                'end_line': end_line
            })
    return methods


def find_affected_methods(patch_file_path, methods):
  
    if not os.path.isfile(patch_file_path):
        print(f"File not found: {patch_file_path}")
        return []

    with open(patch_file_path, 'r', encoding='utf-8') as patch_file:
        patch_content = patch_file.readlines()

    affected_methods = []
    for patch_line in patch_content:
        
        if "@@" in patch_line:  
           
            split_result = patch_line.split(" ")[1]
            newstart_line, mutant_lines = map(int, split_result.split(','))
            newstart_line = -newstart_line  
            newend_line = newstart_line + mutant_lines  
           
            for method in methods:
                for line in range(newstart_line,newend_line):
                    
                    if (line <= method['end_line'] and line >= method['start_line']):
                        affected_methods.append(method['name'])
                        break
    if affected_methods == []:
        return None
    return affected_methods
def find_affected_methods_by_linenumber(line_number, methods):
  
   

    affected_methods = []

    

    for method in methods:
        if (line_number <= method['end_line'] and line_number >= method['start_line']):
            affected_methods.append(method['name'])
            break
                

    return affected_methods
def extract_constructors_and_lines(java_file_path):
    
    if not os.path.isfile(java_file_path):
        print(f"File not found: {java_file_path}")
        return []
    with open(java_file_path, 'r', encoding='utf-8') as file:
        content = file.read()
    try:
        tree = javalang.parse.parse(content)
    except javalang.parser.JavaSyntaxError as e:
        print(f"Extraction Error: {e}")
        return []
    constructors = []
    for path, node in tree.filter(javalang.tree.ConstructorDeclaration):
        start_line = node.position.line
        if node.body:
            max_line = get_max_line(node)
            constructors.append({
                'name': node.name,  
                'start_line': start_line,
                'end_line': max_line
            })
    return constructors
def initial_generate_testcase_def4j(project,project_id,llm,
                                    d4j_path = './defects4j',
                                                                    d4jbug_path = './defects4j_fixed'):
    id=0
    print("..............................................................",project,project_id+1,"......................................................................")
    folder_path = d4j_path + '/framework/projects/%s/patches/' % (project)
    all_files = os.listdir(folder_path)
    patch_files = [file for file in all_files if file.endswith('src.patch')]
    dd=0
    for patch_file in patch_files:
        if int(patch_file.split('.')[0])!=project_id+1:
            continue
        dd+=1
        patch_path=os.path.join(folder_path, patch_file)
        print(dd,"****************************",patch_path)
        try:
            with open(patch_path, 'r',errors='ignore') as patch:
                patch_content = patch.readlines()  
        except Exception as e:
            print("patch parsing error")
            continue
        fixed_bug = d4jbug_path + "/%s/%s_" % (project,project) +str(project_id+1)+"_fixed/"+patch_content[2][6:]
        fixed_bug = fixed_bug.strip()
        base_dir = d4jbug_path + "/%s/%s_" % (project,project) +str(project_id+1)+"_fixed"
        base_dir = base_dir.strip()
        
        methods  = extract_methods_and_lines(fixed_bug)
        affected_methods = find_affected_methods(patch_path, methods)
       
        print(affected_methods)
        if affected_methods is None:
            constructors = extract_constructors_and_lines(fixed_bug)
            affected_methods = find_affected_methods(patch_path, constructors)
           
            print("successfully found affected constructors!!!!!!",affected_methods)
        if affected_methods is not None:
            return whole_process_TCIGen(fixed_bug, base_dir, llm, 0, affected_methods)
        else:
            return whole_process_TCIGen(fixed_bug, base_dir, llm, 1)
def affected_method_finding(project,project_id,
                                    d4j_path = './defects4j',
                                                                    d4jbug_path = './defects4j_fixed'):
    id=0
    print("..............................................................",project,project_id+1,"......................................................................")
    folder_path = d4j_path + '/framework/projects/%s/patches/' % (project)
    all_files = os.listdir(folder_path)
    patch_files = [file for file in all_files if file.endswith('src.patch')]
    dd=0
    for patch_file in patch_files:
        if int(patch_file.split('.')[0])!=project_id+1:
            continue
        dd+=1
        patch_path=os.path.join(folder_path, patch_file)
        print(dd,"****************************",patch_path)
        try:
            with open(patch_path, 'r',errors='ignore') as patch:
                patch_content = patch.readlines()  
        except Exception as e:
            print("patch parsing error")
            continue
        fixed_bug = d4jbug_path + "/%s/%s_" % (project,project) +str(project_id+1)+"_fixed/"+patch_content[2][6:]
        fixed_bug = fixed_bug.strip()
        base_dir = d4jbug_path + "/%s/%s_" % (project,project) +str(project_id+1)+"_fixed"
        base_dir = base_dir.strip()
        
        methods  = extract_methods_and_lines(fixed_bug)
        affected_methods = find_affected_methods(patch_path, methods)
        print(affected_methods)
        if affected_methods is None:
            constructors = extract_constructors_and_lines(fixed_bug)
            affected_methods = find_affected_methods(patch_path, constructors)
           
            print("successfully found affected constructors!!!!!!",affected_methods)
            affected_methods = ["<init>"]
        if affected_methods is not None:
            return affected_methods
        else:
            return None

def running_mutants(project,project_id,mutant_path,mutant_tested_path,d4jbug_path = './defects4j_fixed'):
    print(project," start....................................................................")
    sum=0
    compile_num=0
    test_pass=0
    dead=0
    i = project_id
    folder_path = mutant_path + '/%s/%s-%s.json' % (project,project,i+1)
    base_dir = d4jbug_path + "/%s/%s_" % (project,project) +str(project_id+1)+"_fixed"
    base_dir = base_dir.strip()
    archive_name = generate_tar_bz2(os.path.join(base_dir, "gentest"), base_dir)
    fail_test_min = -1
    print("****************************",folder_path)
    with open(folder_path, 'r') as f:
        print("loading....")
        data = json.load(f)
    test=[]
    proj_path = d4jbug_path + '/%s/%s_%s_fixed' % (project,project,i+1)
    build_dirs = ['/.classes_testgen','/.test_suite','/build','/build-tests','/.classes_instrumented']
    for mutant in data:
        time.sleep(1)
        sum+=1
        #if sum%200==0:
        print("mutant",sum,"testing......")
        dict={}
        filepath=mutant['filepath']
        try:
            oricode=open(filepath,"r",errors='ignore').read()
            lines1 = open(filepath, "r",errors='ignore').read().strip()
            liness = lines1.splitlines()
            liness[mutant['line']-1]=mutant['aftercode']
            
            with open(filepath, "w") as file:
                file.write('\n'.join(liness))
        except Exception as e:
            print(filepath,"error")
            open(filepath, "w").write(oricode)
            continue

        cmd = 'defects4j compile -w %s' % (proj_path)
        for build_dir in build_dirs:
            tmpbuild_path = proj_path + build_dir
            if os.path.exists(tmpbuild_path):
                shutil.rmtree(tmpbuild_path)
            #else:
                #print(build_dir,"does not exists, something wrong")
        # here deleting the compiled files like build or .testcase is needed.
        try:
            log=subprocess.Popen(cmd, shell=True, stdout=subprocess.PIPE, stderr=subprocess.PIPE, bufsize=-1, start_new_session=True).communicate()
            if len(log) > 1 and log[1].decode('utf-8') == 'Running ant (compile)...................................................... OK\nRunning ant (compile.tests)................................................ OK\n':
                compile_num+=1
                try:
                    test_cmd='timeout 500 defects4j test -w %s -s %s' % (proj_path,archive_name) 
                    log_test=subprocess.Popen(test_cmd, shell=True, stdout=subprocess.PIPE, stderr=subprocess.PIPE, bufsize=-1, start_new_session=True).communicate()
 

                    index = log_test[0].decode('utf-8').index("Failing tests:")

                    number = int(log_test[0].decode('utf-8')[index + len("Failing tests:"):].split()[0])
                    if number==0:
                        test_pass+=1
                    dict["mutant_id:"]=mutant['id']
                    dict["line"]=mutant["line"]
                    dict["filepath"]=mutant["filepath"]
                    dict["precode"]=mutant["precode"]
                    dict["aftercode"]=mutant["aftercode"]
                    dict["fail_test_number"]=number
                    dict["fail_test"]=log_test[0].decode('utf-8')
                    if fail_test_min == -1 or number < fail_test_min:
                        fail_test_min = number
                    test.append(dict)
                except Exception as e:
                    dead+=1
                    dict["mutant_id:"]=mutant['id']
                    dict["line"]=mutant["line"]
                    dict["filepath"]=mutant["filepath"]
                    dict["precode"]=mutant["precode"]
                    dict["aftercode"]=mutant["aftercode"]
                    dict["time out"]=True
                    test.append(dict)
                    open(filepath, "w").write(oricode)
                    continue
            open(filepath, "w").write(oricode)
        except Exception as e:
            open(filepath, "w").write(oricode)
            continue
    cmd = 'defects4j compile -w %s' % (proj_path)
    for build_dir in build_dirs:
        folder_path = proj_path + build_dir
        if os.path.exists(folder_path):
            shutil.rmtree(folder_path)
        else:
            print(build_dir,"does not exists, something wrong")
        # here deleting the compiled files like build or .testcase is needed.
    try:
        log=subprocess.Popen(cmd, shell=True, stdout=subprocess.PIPE, stderr=subprocess.PIPE, bufsize=-1, start_new_session=True).communicate()
        if len(log) > 1 and log[1].decode('utf-8') == 'Running ant (compile)...................................................... OK\nRunning ant (compile.tests)................................................ OK\n':
            compile_num+=1
            try:
                test_cmd='timeout 500 defects4j test -w %s -s %s' % (proj_path,archive_name) 
                log_test=subprocess.Popen(test_cmd, shell=True, stdout=subprocess.PIPE, stderr=subprocess.PIPE, bufsize=-1, start_new_session=True).communicate()
 

                index = log_test[0].decode('utf-8').index("Failing tests:")
                
                number = int(log_test[0].decode('utf-8')[index + len("Failing tests:"):].split()[0])
                if number==0:
                    print("kl")
                target_fail_num = number
                target_fail_tests=log_test[0].decode('utf-8')
            except Exception as e:
                print("sthr")
    except Exception as e:
            print("sthr")
            
    survival_nums = 0
    killed_nums = 0
    for mutant in test:
        try:
            if mutant["fail_test_number"] == target_fail_num and mutant["fail_test"] == target_fail_tests:
                mutant["survived"] = True
                survival_nums+=1
            else:
                mutant["survived"] = False
                killed_nums+=1
        except:
            mutant["survived"] = False
    mutation_score = 0
    if survival_nums+killed_nums !=0:
        mutation_score = killed_nums/(survival_nums+killed_nums) *100
    else:
        mutation_score = -1
    savepath=mutant_tested_path + '/%s/%s_%s_test.json' % (project,project,i+1) 
    test_cmd='defects4j coverage -w ./defects4j_fixed/%s/%s_%s_fixed -s %s' % (project,project,i+1,archive_name) 
    
    log_test = subprocess.Popen(test_cmd, shell=True, stdout=subprocess.PIPE, stderr=subprocess.PIPE, bufsize=-1, start_new_session=True)

    
    stdout_data, stderr_data = log_test.communicate()  

    
    print(stdout_data.decode(), end='')

    
    print(stderr_data.decode(), end='')
    with open(savepath, 'w', encoding='utf-8') as file1:
        json.dump(test, file1, ensure_ascii=False,indent=4)
    print("sum mutant:",sum)
    print("compile_num:",compile_num)
    print("test_pass:",test_pass)
    print("dead:",dead,"mutant id:",list)
    return mutation_score
import subprocess
import json
import os
import shutil
from compress import generate_tar_bz2
import logging


all = 0
def del_coverage_result(path):
    for file in os.listdir(path):
        if file.endswith('coverage.xml'):
            os.remove(os.path.join(path, file))
            print(f"已删除文件 {os.path.join(path, file)}")
def copy_coverage_result(src,dst):
    """
    将 src 目录下的 coverage.xml 文件复制到 dst 目录，并重命名为 coverage_%s.xml
    """
    if not os.path.exists(src):
        print(f"源目录 {src} 不存在")
        return
    if not os.path.exists(dst):
        os.makedirs(dst)
    for file in os.listdir(src):
        if file.endswith('coverage.xml'):
            shutil.copy(os.path.join(src, file), os.path.join(dst, 'coverage_%s.xml' % (file.split('.')[0])))
            print(f"已将文件从 {os.path.join(src, file)} 复制到 {os.path.join(dst, 'coverage_%s.xml' % (file.split('.')[0]))}")
def bug_detection_ourgen(project,projid,fixed_dir='./defects4j_fixed',bug_dir='./defects4j_bug'):
    fixed_dir = fixed_dir +'/%s/%s_%s_fixed' % (project,project,projid+1)
    bug_dir = bug_dir + '/%s/%s_%s_bug' % (project,project,projid+1)
    cmd_compile_fixed = 'defects4j compile -w %s' % (fixed_dir)
    cmd_compile_bug = 'defects4j compile -w %s' % (bug_dir)
    archive_name = generate_tar_bz2(os.path.join(fixed_dir, "gentest"), fixed_dir)
    number_fixed = -1
    number_bug = -1
    compilable_fix=0
    compilable_bug=0
    fail_test_fixed = ""
    fail_test_bug = ""
    build_dirs = ['/.classes_testgen','/.test_suite','/build','/build-tests','/.classes_instrumented']
    for build_dir in build_dirs:
            fixed_path = fixed_dir + build_dir
            bug_path = bug_dir + build_dir
            if os.path.exists(fixed_path):
                shutil.rmtree(fixed_path)
            if os.path.exists(bug_path):
                shutil.rmtree(bug_path)
            #else:
                #print(build_dir,"does not exists, something wrong")
        # here deleting the compiled files like build or .testcase is needed.
    try:
        log=subprocess.Popen(cmd_compile_fixed, shell=True, stdout=subprocess.PIPE, stderr=subprocess.PIPE, bufsize=-1, start_new_session=True).communicate()
        if len(log) > 1 and log[1].decode('utf-8') == 'Running ant (compile)...................................................... OK\nRunning ant (compile.tests)................................................ OK\n':
            test_cmd='timeout 500 defects4j test -w %s -s %s' % (fixed_dir,archive_name)
            try :
                compilable_fix = 1
                log_test=subprocess.Popen(test_cmd, shell=True, stdout=subprocess.PIPE, stderr=subprocess.PIPE, bufsize=-1, start_new_session=True).communicate()
 

                index = log_test[0].decode('utf-8').index("Failing tests:")

                number_fixed = int(log_test[0].decode('utf-8')[index + len("Failing tests:"):].split()[0])

                fail_test_fixed = log_test[0].decode('utf-8')
                fixed_run_ok =  1 
            except Exception as e:
                print("error in fixed run",e)
                fixed_run_ok = 0 
                number_fixed = -1
                fail_test_fixed = ""
    
        else:
            print("sth wrong1")
    except Exception as e:
        compilable_fix = 0
        print("error in compile fixed",e)
    try:    
        log=subprocess.Popen(cmd_compile_bug, shell=True, stdout=subprocess.PIPE, stderr=subprocess.PIPE, bufsize=-1, start_new_session=True).communicate()
        if len(log) > 1 and log[1].decode('utf-8') == 'Running ant (compile)...................................................... OK\nRunning ant (compile.tests)................................................ OK\n':
            test_cmd='timeout 500 defects4j test -w %s -s %s' % (bug_dir,archive_name) 
            try:
                compilable_bug = 1
                log_test=subprocess.Popen(test_cmd, shell=True, stdout=subprocess.PIPE, stderr=subprocess.PIPE, bufsize=-1, start_new_session=True).communicate()
 

                index = log_test[0].decode('utf-8').index("Failing tests:")

                number_bug= int(log_test[0].decode('utf-8')[index + len("Failing tests:"):].split()[0])

                fail_test_bug = log_test[0].decode('utf-8')
        
                bug_run_ok = 1 
            except Exception as e:
                print("error in bug run",e)
                bug_run_ok = 0 
                number_bug = -1
                fail_test_bug = ""   
        else:
        
            print("sth wrong2")
    except Exception as e:
        compilable_bug = 0
    if(compilable_fix == 0):
        return 0
    elif(compilable_bug == 0):
        return 1
    if(fixed_run_ok == 0):
        print("fixed run failed")
        if(bug_run_ok == 0):
            print("bug run failed")
            return 0
        else:
            print("bug run ok")
            return 0
    if(fixed_run_ok == 1 and bug_run_ok == 0):
        return 1
    if(number_bug==number_fixed):
        print("fail number is the same",number_fixed,number_bug)
        if(fail_test_fixed==fail_test_bug):
            print("fail test is the same",fail_test_bug)
            return 0
    print("testcase successfully identify the result!")
    return 1
def bug_detection_evosuite(project,projid,fixed_dir='./defects4j_fixed',bug_dir='./defects4j_bug'):
    fixed_dir = fixed_dir +'/%s/%s_%s_fixed' % (project,project,projid+1)
    bug_dir = bug_dir + '/%s/%s_%s_bug' % (project,project,projid+1)
    cmd_compile_fixed = 'defects4j compile -w %s' % (fixed_dir)
    cmd_compile_bug = 'defects4j compile -w %s' % (bug_dir)
    archive_name = './output/%s/evosuite/50/%s-%sf-evosuite.50.tar.bz2' %(project,project,projid+1)
    number_fixed = -1
    number_bug = -1
    compilable_fix=0
    compilable_bug=0
    fail_test_fixed = ""
    fail_test_bug = ""
    build_dirs = ['/.classes_testgen','/.test_suite','/build','/build-tests','/.classes_instrumented']
    for build_dir in build_dirs:
            fixed_path = fixed_dir + build_dir
            bug_path = bug_dir + build_dir
            if os.path.exists(fixed_path):
                shutil.rmtree(fixed_path)
            if os.path.exists(bug_path):
                shutil.rmtree(bug_path)
            #else:
                #print(build_dir,"does not exists, something wrong")
        # here deleting the compiled files like build or .testcase is needed.
    try:
        log=subprocess.Popen(cmd_compile_fixed, shell=True, stdout=subprocess.PIPE, stderr=subprocess.PIPE, bufsize=-1, start_new_session=True).communicate()
        if len(log) > 1 and log[1].decode('utf-8') == 'Running ant (compile)...................................................... OK\nRunning ant (compile.tests)................................................ OK\n':
            test_cmd='timeout 500 defects4j test -w %s -s %s' % (fixed_dir,archive_name)
            try :
                compilable_fix = 1
                log_test=subprocess.Popen(test_cmd, shell=True, stdout=subprocess.PIPE, stderr=subprocess.PIPE, bufsize=-1, start_new_session=True).communicate()
 

                index = log_test[0].decode('utf-8').index("Failing tests:")

                number_fixed = int(log_test[0].decode('utf-8')[index + len("Failing tests:"):].split()[0])

                fail_test_fixed = log_test[0].decode('utf-8')
                fixed_run_ok =  1 
                
            except Exception as e:
                print("error in fixed run",e)
                fixed_run_ok = 0 
                number_fixed = -1
                fail_test_fixed = ""
    
        else:
            print("sth wrong1")
    except Exception as e:
        compilable_fix = 0
        print("error in compile fixed",e)
    try:    
        log=subprocess.Popen(cmd_compile_bug, shell=True, stdout=subprocess.PIPE, stderr=subprocess.PIPE, bufsize=-1, start_new_session=True).communicate()
        if len(log) > 1 and log[1].decode('utf-8') == 'Running ant (compile)...................................................... OK\nRunning ant (compile.tests)................................................ OK\n':
            test_cmd='timeout 500 defects4j test -w %s -s %s' % (bug_dir,archive_name) 
            try:
                compilable_bug = 1
                log_test=subprocess.Popen(test_cmd, shell=True, stdout=subprocess.PIPE, stderr=subprocess.PIPE, bufsize=-1, start_new_session=True).communicate()
 

                index = log_test[0].decode('utf-8').index("Failing tests:")

                number_bug= int(log_test[0].decode('utf-8')[index + len("Failing tests:"):].split()[0])

                fail_test_bug = log_test[0].decode('utf-8')
        
                bug_run_ok = 1 
            except Exception as e:
                print("error in bug run",e)
                bug_run_ok = 0 
                number_bug = -1
                fail_test_bug = ""   
        else:
        
            print("sth wrong2")
    except Exception as e:
        compilable_bug = 0
    if(compilable_fix == 0):
        return 0,number_fixed,number_bug,fail_test_bug,fail_test_fixed
    elif(compilable_bug == 0):
        return 1,number_fixed,number_bug,fail_test_bug,fail_test_fixed
    if(fixed_run_ok == 0):
        print("fixed run failed")
        if(bug_run_ok == 0):
            print("bug run failed")
            return 0,number_fixed,number_bug,fail_test_bug,fail_test_fixed
        else:
            print("bug run ok")
            return 0,number_fixed,number_bug,fail_test_bug,fail_test_fixed
    if(fixed_run_ok == 1 and bug_run_ok == 0):
        return 1,number_fixed,number_bug,fail_test_bug,fail_test_fixed
    if(number_bug==number_fixed):
        print("fail number is the same",number_fixed,number_bug)
        if(fail_test_fixed==fail_test_bug):
            print("fail test is the same",fail_test_bug)
            return 0,number_fixed,number_bug,fail_test_bug,fail_test_fixed
    print("testcase successfully identify the result!")
    return 1,number_fixed,number_bug,fail_test_bug,fail_test_fixed
def bug_detection_outside(project,projid,fixed_dir='./defects4j_fixed',bug_dir='./defects4j_bug'):
    fixed_dir = fixed_dir +'/%s/%s_%s_fixed' % (project,project,projid+1)
    bug_dir = bug_dir + '/%s/%s_%s_bug' % (project,project,projid+1)
    cmd_compile_fixed = 'defects4j compile -w %s' % (fixed_dir)
    cmd_compile_bug = 'defects4j compile -w %s' % (bug_dir)
    archive_name = './output/ChatUniTest/%s/%s%s.tar.bz2' %(project,project,projid+1)
    number_fixed = -1
    number_bug = -1
    compilable_fix=0
    compilable_bug=0
    fail_test_fixed = ""
    fail_test_bug = ""
    build_dirs = ['/.classes_testgen','/.test_suite','/build','/build-tests','/.classes_instrumented']
    for build_dir in build_dirs:
            fixed_path = fixed_dir + build_dir
            bug_path = bug_dir + build_dir
            if os.path.exists(fixed_path):
                shutil.rmtree(fixed_path)
            if os.path.exists(bug_path):
                shutil.rmtree(bug_path)
            #else:
                #print(build_dir,"does not exists, something wrong")
        # here deleting the compiled files like build or .testcase is needed.
    try:
        log=subprocess.Popen(cmd_compile_fixed, shell=True, stdout=subprocess.PIPE, stderr=subprocess.PIPE, bufsize=-1, start_new_session=True).communicate()
        if len(log) > 1 and log[1].decode('utf-8') == 'Running ant (compile)...................................................... OK\nRunning ant (compile.tests)................................................ OK\n':
            test_cmd='timeout 500 defects4j test -w %s -s %s' % (fixed_dir,archive_name)
            try :
                compilable_fix = 1
                log_test=subprocess.Popen(test_cmd, shell=True, stdout=subprocess.PIPE, stderr=subprocess.PIPE, bufsize=-1, start_new_session=True).communicate()
 

                index = log_test[0].decode('utf-8').index("Failing tests:")

                number_fixed = int(log_test[0].decode('utf-8')[index + len("Failing tests:"):].split()[0])

                fail_test_fixed = log_test[0].decode('utf-8')
                fixed_run_ok =  1 
                
            except Exception as e:
                print("error in fixed run",e)
                fixed_run_ok = 0 
                number_fixed = -1
                fail_test_fixed = ""
    
        else:
            print("sth wrong1")
    except Exception as e:
        compilable_fix = 0
        print("error in compile fixed",e)
    try:    
        log=subprocess.Popen(cmd_compile_bug, shell=True, stdout=subprocess.PIPE, stderr=subprocess.PIPE, bufsize=-1, start_new_session=True).communicate()
        if len(log) > 1 and log[1].decode('utf-8') == 'Running ant (compile)...................................................... OK\nRunning ant (compile.tests)................................................ OK\n':
            test_cmd='timeout 500 defects4j test -w %s -s %s' % (bug_dir,archive_name) 
            try:
                compilable_bug = 1
                log_test=subprocess.Popen(test_cmd, shell=True, stdout=subprocess.PIPE, stderr=subprocess.PIPE, bufsize=-1, start_new_session=True).communicate()
 

                index = log_test[0].decode('utf-8').index("Failing tests:")

                number_bug= int(log_test[0].decode('utf-8')[index + len("Failing tests:"):].split()[0])

                fail_test_bug = log_test[0].decode('utf-8')
        
                bug_run_ok = 1 
            except Exception as e:
                print("error in bug run",e)
                bug_run_ok = 0 
                number_bug = -1
                fail_test_bug = ""   
        else:
        
            print("sth wrong2")
    except Exception as e:
        compilable_bug = 0
    if(compilable_fix == 0):
        return 0,number_fixed,number_bug,fail_test_bug,fail_test_fixed
    elif(compilable_bug == 0):
        return 1,number_fixed,number_bug,fail_test_bug,fail_test_fixed
    if(fixed_run_ok == 0):
        print("fixed run failed")
        if(bug_run_ok == 0):
            print("bug run failed")
            return 0,number_fixed,number_bug,fail_test_bug,fail_test_fixed
        else:
            print("bug run ok")
            return 0,number_fixed,number_bug,fail_test_bug,fail_test_fixed
    if(fixed_run_ok == 1 and bug_run_ok == 0):
        return 1,number_fixed,number_bug,fail_test_bug,fail_test_fixed
    if(number_bug==number_fixed):
        print("fail number is the same",number_fixed,number_bug)
        if(fail_test_fixed==fail_test_bug):
            print("fail test is the same",fail_test_bug)
            return 0,number_fixed,number_bug,fail_test_bug,fail_test_fixed
    print("testcase successfully identify the result!",fail_test_bug,fail_test_fixed)
    return 1,number_fixed,number_bug,fail_test_bug,fail_test_fixed
def mutant_test(method,project,bot,top,mutant_path,mutant_tested_path):
    global all
    print(project," start....................................................................")
    sum=0
    compile_num=0
    test_pass=0
    dead=0
    for i in range(bot,top):
        folder_path = mutant_path + '/%s/%s-%s.json' % (project,project,i+1)

        print("****************************",folder_path)
        with open(folder_path, 'r') as f:
            print("loading....")
            data = json.load(f)
        test=[]
        for mutant in data:
            all += 1
            sum+=1
            #if sum%200==0:
            print("mutant",sum,"testing......")
            dict={}
            filepath=mutant['filepath']
            try:
                if method == 'Major':
                    oricode=open(filepath,"r",errors='ignore').read()
                    lines1 = open(filepath, "r",errors='ignore').read().strip()
                    liness = lines1.splitlines()
                    liness[mutant['line']-1]=liness[mutant['line']-1].replace(mutant['precode'],mutant['aftercode'])
                else:
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
            cmd = 'defects4j compile -w ./defects4j_fixed/%s/%s_%s_fixed' % (project,project,i+1)
            try:
                log=subprocess.Popen(cmd, shell=True, stdout=subprocess.PIPE, stderr=subprocess.PIPE, bufsize=-1, start_new_session=True).communicate()
                if len(log) > 1 and log[1].decode('utf-8') == 'Running ant (compile)...................................................... OK\nRunning ant (compile.tests)................................................ OK\n':
                    compile_num+=1
                    try:
                        test_cmd='timeout 500 defects4j test -w ./defects4j_fixed/%s/%s_%s_fixed' % (project,project,i+1)
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
                        dict["fail_test_number:"]=number
                        dict["fail_test:"]=log_test[0].decode('utf-8')
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
        savepath=mutant_tested_path + '/%s/%s_%s_test.json' % (project,project,i+1) 
        with open(savepath, 'w', encoding='utf-8') as file1:
            json.dump(test, file1, ensure_ascii=False,indent=4)
    print("sum mutant:",sum)
    print("compile_num:",compile_num)
    print("test_pass:",test_pass)
    print("dead:",dead,"mutant id:",list)
def test_coverage(project,projid,fixed_dir='./defects4j_fixed',bug_dir='./defects4j_bug'):
    archive_name = './output/%s/evosuite/50/%s-%sf-evosuite.50.tar.bz2' %(project,project,projid+1)
    test_cmd='timeout 500  defects4j coverage -w ./defects4j_fixed/%s/%s_%s_fixed -s %s' % (project,project,projid+1,archive_name) 
    # 运行命令并获取输出
    log_test = subprocess.Popen(test_cmd, shell=True, stdout=subprocess.PIPE, stderr=subprocess.PIPE, bufsize=-1, start_new_session=True)
    log_test = log_test.communicate()
    copy_coverage_result('./defects4j_fixed/%s/%s_%s_fixed' % (project,project,projid+1),'./coverage_result/Evosuite/%s%s'%(project,projid+1))
def rotation_test(max,name):
    for i in range(0,max):
        try:
            del_coverage_result('./defects4j_fixed/%s/%s_%s_fixed' % (name,name,i+1))
            #result,nf,nb,_,_ = bug_detection_evosuite(name,i,fixed_dir='./defects4j_fixed',bug_dir='./defects4j_bug')
            #copy_coverage_result('./defects4j_fixed/%s/%s_%s_fixed' % (name,name,i+1),'./coverage_result/Evosuite/%s%s'%(name,i+1))
        # Log the result (expected to be either 0 or 1)
            test_coverage(name,i,fixed_dir='./defects4j_fixed',bug_dir='./defects4j_bug')
            evosuite_logger.info(f"Project {name} test {i+1}: coverage result saved")
            #evosuite_logger.info(f"Project {name} test {i+1}: result = {result},number_fixed =  {nf},number_bug = {nb}")
        except Exception as e:
            # Log any errors that occur during the function call
            evosuite_logger.error(f"Project {name} test {i+1}: encountered an error: {e}")
if __name__ == "__main__":
    # Configure the logging system
    # 配置第一个 logger，用于记录 evosuite 相关的日志
    evosuite_logger = logging.getLogger("evosuite_logger")
    evosuite_logger.setLevel(logging.INFO)
    evosuite_handler = logging.FileHandler("evo_unified_515.log")
    evosuite_formatter = logging.Formatter('%(asctime)s - %(levelname)s - %(message)s')
    evosuite_handler.setFormatter(evosuite_formatter)
    evosuite_logger.addHandler(evosuite_handler)
    #rotation_test(4,'Gson')
    rotation_test(26,'Chart')
    rotation_test(40,'Cli')
    rotation_test(47,'Compress')

    rotation_test(16,'Csv')

    rotation_test(18,'Gson')

    rotation_test(22,'JxPath')

    rotation_test(106,'Math')


    
# mutant_path = "./Mutants/GPT3.5/raw"
# mutant_tested_path = './Mutants/GPT3.5/tested'

# mutant_test("GPT3.5","Cli",0,40,mutant_path,mutant_tested_path)
# print(all)

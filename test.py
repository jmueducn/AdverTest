import subprocess
import json
import os
import shutil
from compress import generate_tar_bz2


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

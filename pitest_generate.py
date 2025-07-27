import subprocess
import os
import json


def generate_mutant_all(project,project_id):
    id=str(project_id+1)
    print("..............................................................",project,id,"......................................................................")
    folder_path = '../mutantsgen/Wukong/defects4j/framework/projects/%s/patches/' % (project)#遍历patches
    all_files = os.listdir(folder_path)
    patch_files = [file for file in all_files if file==(f'{id}.src.patch')]
    dd=0
    for patch_file in patch_files:

        dd+=1
        patch_path=os.path.join(folder_path, patch_file)
        print(dd,"****************************",patch_path)
        try:
            with open(patch_path, 'r',errors='ignore') as patch:
                patch_content = patch.readlines()  
        except Exception as e:
            print("wowow")
            continue
        if project=="Math":
            mutant_class=patch_content[0].split(' ')[-1][16:].replace('/','.')[:-6]
            test_class=mutant_class+"Test"
            savepath="pitest/Pitest_mutant/"+project+"/"+project+id+"/"+patch_path.split('/')[-1]
            cmd="java -cp pitest-1.14.4/pitest/target/pitest-dev-SNAPSHOT.jar:pitest-1.14.4/pitest-command-line/target/pitest-command-line-dev-SNAPSHOT.jar:pitest-1.14.4/pitest-entry/target/pitest-entry-dev-SNAPSHOT.jar:lib/junit-4.13.2.jar:lib/hamcrest-all-1.3.jar:defects4j_fixed/%s/%s_%s_fixed/target/classes/:defects4j_fixed/%s/%s_%s_fixed/target/test-classes/ org.pitest.mutationtest.commandline.MutationCoverageReport --reportDir %s --targetClasses %s --targetTests %s --sourceDirs defects4j_fixed/%s/%s_%s_fixed/ --mutators ALL --verbose true"%(project,project,id,project,project,id,savepath,mutant_class,test_class,project,project,id)
            #print(cmd)
            try:
                out, err = subprocess.Popen(cmd, shell=True, stdout=subprocess.PIPE, stderr=subprocess.PIPE, bufsize=-1, start_new_session=True).communicate()
                print("=== STDOUT ===")
                print(out)          # what the process wrote to stdout
                print("=== STDERR ===")
                print(err)          
            except Exception as e:
                if os.path.exists(savepath): 
                    os.rmdir(savepath)
                continue






for i in range(0,1):
    generate_mutant_all("Math",i)

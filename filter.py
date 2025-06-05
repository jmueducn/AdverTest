import subprocess
import os
from tqdm import tqdm
import json
def filter(project,num,folderpath,save_path):
    print("--------------------",project,"----------------------")
    global good
    global notk
    global com
    good=0
    notk=0
    com=0
    for i in range(num):
        filter_test=[]
        folder_path = folderpath + '/%s/%s_%s_test.json' % (project,project,i%2+1) # here modified should be i+1
        with open(folder_path, "r", encoding="utf-8") as f:
            data = json.load(f)
        for mutant in data:
            com+=1
            try:
                file_path=mutant['filepath']
                with open(file_path, "r", encoding="utf-8") as f:
                    mutantfile = f.readlines()
                if mutant['precode'] in mutantfile[mutant['line']-1] and mutant['precode']!=mutant['aftercode']and mutant['precode']!="\n":
                    if len(mutant['precode'].strip()) > 0:
                        if mutant['precode'].strip()[0].isalpha():
                            filter_test.append(mutant)
                            good+=1
                            if mutant['fail_test_number:']==0:
                                notk+=1
                    else:
                        if len(mutant['aftercode'].strip()) > 0:
                            if mutant['aftercode'].strip()[0]!='/':
                                filter_test.append(mutant)
                                good+=1
                                if mutant['fail_test_number:']==0:
                                    notk+=1
                        else:
                            filter_test.append(mutant)
                            good+=1
                            if mutant['fail_test_number:']==0:
                                notk+=1
            except Exception as e:
                continue
        savepath= save_path + '/%s/%s_%s_test.json' % (project,project,i+1) 
        with open(savepath, 'w', encoding='utf-8') as file1:
            json.dump(filter_test, file1, ensure_ascii=False,indent=4)
    print("com:",com)
    print("all:",good,"    killed",good-notk,"      score:",(good-notk)/good)

folderpath = './Mutants/GPT3.5/tested'
save_path = './Mutants/GPT3.5/filtered'
filter('Cli',40,folderpath,save_path)
#WTF is folderpath and WTF is save_path, man!
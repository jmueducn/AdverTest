import subprocess
import os
from tqdm import tqdm
import json
def download(project,n):
    for i in range(0,n):
        cmd='defects4j checkout -p %s -v %sf -w ./defects4j_fixed/%s/%s_%s_fixed' % (project,i+1,project,project,i+1)
        print(cmd)
        subprocess.Popen(cmd, shell=True, stdout=subprocess.PIPE, stderr=subprocess.PIPE, bufsize=-1, start_new_session=True).communicate()


download("Cli",40)


def bugdownload(project,n):
    for i in range(0,n):
        cmd='defects4j checkout -p %s -v %sb -w ./defects4j_bug/%s/%s_%s_bug' % (project,i+1,project,project,i+1)
        print(cmd)
        subprocess.Popen(cmd, shell=True, stdout=subprocess.PIPE, stderr=subprocess.PIPE, bufsize=-1, start_new_session=True).communicate()


bugdownload("Cli",40)

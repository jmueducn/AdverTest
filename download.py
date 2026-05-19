import subprocess
import os

def download(project, n):
    """Download fixed versions of a Defects4J project."""
    for i in range(0, n):
        cmd = 'defects4j checkout -p %s -v %sf -w ./defects4j_fixed/%s/%s_%s_fixed' % (project, i+1, project, project, i+1)
        print(cmd)
        subprocess.Popen(cmd, shell=True, stdout=subprocess.PIPE, stderr=subprocess.PIPE, bufsize=-1, start_new_session=True).communicate()


def bugdownload(project, n):
    """Download buggy versions of a Defects4J project."""
    for i in range(0, n):
        cmd = 'defects4j checkout -p %s -v %sb -w ./defects4j_bug/%s/%s_%s_bug' % (project, i+1, project, project, i+1)
        print(cmd)
        subprocess.Popen(cmd, shell=True, stdout=subprocess.PIPE, stderr=subprocess.PIPE, bufsize=-1, start_new_session=True).communicate()


# Defects4J v2.0.1 projects and their bug counts
PROJECTS = {
    "Chart": 26,
    "Cli": 40,
    "Closure": 174,
    "Codec": 18,
    "Collections": 28,
    "Compress": 47,
    "Csv": 16,
    "Gson": 18,
    "JacksonCore": 26,
    "JacksonDatabind": 112,
    "JacksonXml": 6,
    "Jsoup": 93,
    "JxPath": 22,
    "Lang": 65,
    "Math": 106,
    "Mockito": 38,
    "Time": 27,
}

if __name__ == "__main__":
    for project, count in PROJECTS.items():
        download(project, count)
        bugdownload(project, count)

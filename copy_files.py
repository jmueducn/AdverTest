import os
import shutil

def copy_and_rename(project, index, new_filename):

    src = f"./defects4j_fixed/{project}/{project}_{index}_fixed/gentest.tar.bz2"
    # Target directory where the file will be copied
    dst_dir = f"./output/pit/{project}"

    os.makedirs(dst_dir, exist_ok=True)
  
    dst = os.path.join(dst_dir, new_filename)
    
    # Check if the source file exists
    if not os.path.isfile(src):
        print(f"Source file {src} does not exist, please check the path or the value of chart_index.")
        return

    # Copy the file to the target directory and rename it
    shutil.copy(src, dst)
    print(f"Copied file from {src} to {dst}")

if __name__ == '__main__':
    project = 'Math'
    for i in range(0, 106):
        new_name = '%s%d.tar.bz2' % (project, i+1)
        copy_and_rename(project, i+1, new_name)

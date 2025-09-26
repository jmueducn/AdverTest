import os
import shutil

def copy_and_rename_chart(chart_index, new_filename):

    src = f"/data3/abc/data3/def/MutAGENTest/src/defects4j_fixed/Math/Math_{chart_index}_fixed/gentest.tar.bz2"
    # Target directory where the file will be copied
    dst_dir = "./output/pit/Math"

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
    # Example: Copy the file with chart index 1 and rename it to "chart1.tar.bz2"
    for i in range(0,106):
        new_name = 'Math%d.tar.bz2'%(i+1)
        copy_and_rename_chart(i+1, new_name)

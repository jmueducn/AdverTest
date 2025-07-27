import os
import shutil

def copy_and_rename_chart(chart_index, new_filename):
    """
    将指定 chart_index 的 gentest.tar.bz2 文件复制到目标目录，并重命名为 new_filename

    参数:
    - chart_index: int，源目录中 Chart 的编号（对应 Chart_%d_fixed 中的 %d）
    - new_filename: str，新文件名（例如："chart1.tar.bz2"）
    """
    # 构造源文件路径
    src = f"/data3/fyx/data3/cpy/MutAGENTest/src/defects4j_fixed/Math/Math_{chart_index}_fixed/gentest.tar.bz2"
    # 目标目录
    dst_dir = "./output/pit/Math"
    # 如果目标目录不存在，则创建它
    os.makedirs(dst_dir, exist_ok=True)
    # 构造目标文件路径
    dst = os.path.join(dst_dir, new_filename)
    
    # 检查源文件是否存在
    if not os.path.isfile(src):
        print(f"源文件 {src} 不存在，请检查路径或 chart_index 的值。")
        return

    # 复制文件到目标目录并重命名
    shutil.copy(src, dst)
    print(f"已将文件从 {src} 复制到 {dst}")

if __name__ == '__main__':
    # 示例：复制 chart 序号为 1 的文件，并将其重命名为 "chart1.tar.bz2"
    for i in range(0,106):
        new_name = 'Math%d.tar.bz2'%(i+1)
        copy_and_rename_chart(i+1, new_name)

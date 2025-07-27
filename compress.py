import os
import tarfile

def generate_tar_bz2(base_dir, save_dir):
    """
    将 base_dir 压缩为 .tar.bz2 文件，并保存到 save_dir。

    :param base_dir: 要压缩的目录。
    :param save_dir: 压缩文件的保存路径。可以是一个目录，也可以是一个具体的文件路径。
    """
    try:
        # 检查 base_dir 是否存在且为目录
        if not os.path.isdir(base_dir):
            raise FileNotFoundError(f"The base directory '{base_dir}' does not exist.")

        # 确定压缩文件的完整路径
        if os.path.isdir(save_dir):
            # 如果 save_dir 是一个目录，则在其中创建一个压缩文件，文件名为 base_dir 的目录名加上 .tar.bz2
            archive_name = os.path.join(save_dir, os.path.basename(base_dir) + '.tar.bz2')
        else:
            # 如果 save_dir 不是目录，则假设它是一个文件路径
            archive_name = save_dir

        # 确保保存路径的父目录存在
        archive_parent = os.path.dirname(archive_name)
        if archive_parent and not os.path.exists(archive_parent):
            os.makedirs(archive_parent)
            print(f"创建目录: {archive_parent}")  # "Created directory: {archive_parent}"

        # 打开 tar.bz2 文件进行写入
        with tarfile.open(archive_name, "w:bz2") as tar:
            # 将 base_dir 添加到归档中，保持其目录结构
            tar.add(base_dir, arcname=os.path.basename(base_dir))
            print(f"成功压缩 '{base_dir}' 到 '{archive_name}'")  # "Successfully compressed '{base_dir}' to '{archive_name}'"

        # 通知用户压缩成功
        print(f"压缩文件已保存到: {archive_name}")  # "Compressed file has been saved to: {archive_name}"

        return archive_name

    except Exception as e:
        print(f"压缩过程中出现错误: {e}")  # "An error occurred during compression: {e}"

if __name__ == "__main__":
    base_dir = './defects4j_fixed/Chart/Chart_3_fixed'
    generate_tar_bz2(base_dir+"/gentest",base_dir)
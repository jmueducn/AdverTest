import os
import tarfile

def generate_tar_bz2(base_dir, save_dir):
   
    try:
        if not os.path.isdir(base_dir):
            raise FileNotFoundError(f"The base directory '{base_dir}' does not exist.")

        if os.path.isdir(save_dir):
            archive_name = os.path.join(save_dir, os.path.basename(base_dir) + '.tar.bz2')
        else:
            archive_name = save_dir

        archive_parent = os.path.dirname(archive_name)
        if archive_parent and not os.path.exists(archive_parent):
            os.makedirs(archive_parent)
            print(f"Created directory: {archive_parent}")  # "Created directory: {archive_parent}"

        with tarfile.open(archive_name, "w:bz2") as tar:
            tar.add(base_dir, arcname=os.path.basename(base_dir))
            print(f"Successfully compressed '{base_dir}' to '{archive_name}'")

        print(f"Compressed file has been saved to: {archive_name}")

        return archive_name

    except Exception as e:
        print(f"An error occurred during compression: {e}")

if __name__ == "__main__":
    base_dir = './defects4j_fixed/Chart/Chart_3_fixed'
    generate_tar_bz2(base_dir+"/gentest",base_dir)
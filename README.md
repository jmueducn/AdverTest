# MutAGENTest
## 上面的名字其实没起，瞎编的

dependency: defects4j v2.0.1 我修改过一点（当时改的但是不知道要不要用），放在另一个repo里了
然后安装可以参考原版的defects4j
但是还是有可能出问题 到时候修改  defects4j.build.xml文件(defects4j/framework/projects/defects4j.build.xml)，把dependency的位置改对
```
    <property name="junit5-api.jar" value="xxxx"/>
    <property name="junit5-engine.jar" value="xxxx"/>
    <property name="junit5-platform.jar" value="xxxx"/>
    <property name="mock-junit.jar" value="xxxx"/>
    <property name="objnesis.jar" value="xxxx"/>
    <property name="mockito.jar" value="xxxx"/>
    <property name="byte-buddy.jar" value="xxxx"/>
```
最后也没用junit5,所以可以把junit5相关的删了。


usage：generate.py  跑实验的，目前在跑消融实验所以有更改 修改 18行的 projname = 'Math' 可以更改跑的项目

```
with ThreadPoolExecutor(max_workers=8) as executor:
    futures = [executor.submit(process_project, i) for i in range(0, 106)]
    for future in as_completed(futures):
        future.result() 
``` 
range 更改 你要在哪些bug上实验。（其实理论上应该搞个参数或者config文件的，但是没搞）
config.py目前只用来改log的名字。

如果要正常跑就把下面的注释去掉：
```
    if cnt_M+cnt_T >= 4:
            # if cnt_T < cnt_M  or last_M == 1:
            #     TC_ENHANCE(proj,projid,'./defects4j_fixed',test_file_path,mutants_tested,llm)
            #     cnt_T+=1
            #     mutation_score = running_mutants(proj,projid,mutants_raw,mutants_tested)
            #     uncovered,coverage = coverage_process(proj,projid,mutated_lines)
            #     our_method_logger.info(f'Project name: {projname}, Project ID: {projid+1}, MS:{mutation_score} CV:{coverage},Round:{cnt_T+cnt_M+1},TC: {cnt_T},MT: {cnt_M}')
            #     result = bug_detection_ourgen(proj,projid)
            #     our_method_logger.info(f'Project name: {projname}, Project ID: {projid+1}, Round:{cnt_T+cnt_M}, result: {result}')
            break
        # if coverage>coverage_threshold and mutation_score>= ms_standard:
        #     if cnt_T == 0:
        #         TC_ENHANCE(proj,projid,'./defects4j_fixed',test_file_path,mutants_tested,llm)
        #         mutation_score = running_mutants(proj,projid,mutants_raw,mutants_tested)
        #         uncovered,coverage = coverage_process(proj,projid,mutated_lines)
        #         cnt_T+=1
        #         our_method_logger.info(f'Project name: {projname}, Project ID: {projid+1}, MS:{mutation_score} CV:{coverage},Round:{cnt_T+cnt_M+1},TC: {cnt_T},MT: {cnt_M}')
        #         result = bug_detection_ourgen(proj,projid)
        #         our_method_logger.info(f'Project name: {projname}, Project ID: {projid+1}, Round:{cnt_T+cnt_M}, result: {result}')
        #         break
        #     break
```
因为写死了一些命名格式所以  
把bug版本的项目放在./defects4j_bug/{projname}/{projname}_{id}_bug里  
把修复版本的项目放在./defects4j_fixed/{projname}/{projname}_{id}_fixed里（不用手动操作，可能需要新建文件夹）  
使用download.py下载项目（在defects4j安装完后）   

使用copy_files.py把生成后的测试另存   

其他有用的文件：  
enhance_mutants.py  
enhance_testcase.py  
function.py  
test.py: 可以跑保存下来的测试用例+把覆盖率报告存到指定的位置。    
aggregate_coverage.py和analyse_coverage.py用来分析覆盖率的
model.py 是设置模型给Langchain的。目前只有gpt和 deepseek-v3（可以增加一些）

=========================
我把还要做的事情写在issue里

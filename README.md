# AdverTest

dependency: defects4j v2.0.1 

defects4j.build.xml (defects4j/framework/projects/defects4j.build.xml)，add dependency
```
    <property name="mock-junit.jar" value="xxxx"/>
    <property name="objnesis.jar" value="xxxx"/>
    <property name="mockito.jar" value="xxxx"/>
    <property name="byte-buddy.jar" value="xxxx"/>
```


usage：generate.py  

```
with ThreadPoolExecutor(max_workers=8) as executor:
    futures = [executor.submit(process_project, i) for i in range(0, 106)]
    for future in as_completed(futures):
        future.result() 
``` 
range for bugs




put bug version project at./defects4j_bug/{projname}/{projname}_{id}_bug 
put fixed version ./defects4j_fixed/{projname}/{projname}_{id}_fixed  
use download.py to download projects after finishing install defects4j  

use copy_files.py to copy generated tests for further usage.


enhance_mutants.py  
enhance_testcase.py  
function.py  
test.py:  
aggregate_coverage.py analyse_coverage.py
model.py 
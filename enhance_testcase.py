# enhance_testcase.py
# enhance_from_details.py
import  tempfile
from pathlib import Path


import os
import re
import json
import subprocess
import traceback
from langchain.prompts import ChatPromptTemplate
from model import GPT,Deepseek
from extract import extract_method  
from compress import generate_tar_bz2
from testIniGenPrompt import extract_pure_test_method,ensure_unique_test_method_names,assemble_test_class,compile_and_run,update_test_class_info,llm_based_fix
from function import extract_methods_and_lines,find_affected_methods_by_linenumber,extract_constructors_and_lines
DETAILS_RE = re.compile(
    r"clazz=([A-Za-z0-9_.$]+).*?"
    r"method=([A-Za-z0-9_<$]+).*?"
    r"lineNumber=(\d+).*?"
    r"description=(.*?)\]"
)
def tc_enhance(test_file,mutants_file,llm,base_dir):
    
    test_class_code = ""
    with open(test_file, 'r') as file:
        test_class_code = file.read()    
    with open(mutants_file, 'r') as file:
        mutants_data = json.load(file)
    survived_mutants = []
    for mutant in mutants_data:
        if mutant['survived'] == True:
            survived_mutants.append(mutant)
    if survived_mutants == []:
        return 1,[]
    index = test_class_code.find("@Test")
    simple_mode =  0
    import_str = ""
    if index != -1:
        import_str = test_class_code[:index]
        simple_mode = 1
    cached = False
    last_tested_file = "what"
    program_under_test = ""
    methods = ""
    extracted = ""
    methods = ""
    class_name = ""
    imports = ""
    pkg = ""
    backup_tcc = test_class_code
    backup_tci = ""
    Rfirst = True
    for survived_mutant in survived_mutants:
        print("------generating from survived mutants------")
        pure_test_methods = extract_pure_test_method(test_class_code)
        tested_file = survived_mutant['filepath']
        print(survived_mutant)
        if(tested_file == last_tested_file):
            cached = True
        else:
            cached = False
        last_tested_file = tested_file
        line = survived_mutant['line']
        
        if not cached:
            class_name = os.path.splitext(os.path.basename(tested_file))[0]
            
            with open(tested_file, 'r') as file:
                program_under_test = file.read()
            
            extracted = extract_method(tested_file)
            imports = extracted[0].get('imports', [])
            pkg = extracted[0].get('package', [])
            methods = extract_methods_and_lines(tested_file)
            
            methods = find_affected_methods_by_linenumber(line,methods)
            print(methods)
            if methods == []:
                methods = extract_constructors_and_lines(tested_file)
                methods = find_affected_methods_by_linenumber(line,methods)
            if methods == []:
                print("still no methdosdsdsdsds")
        #this prompt is 0-shot, maybe we can try one-shot later.
        prompt_template = '''
Instruction:

You are an expert Java developer and software tester. Your task is to generate a JUnit test method to detect and capture survived mutants in a given Java method within a Java class. The mutant has already been identified, and your job is to create test cases to ensure that it is caught during testing.(notice:mutant refers to mutant in software engineering, i.e. making subtle alterations to the original code)
Follow these steps to ensure comprehensive and detection for mutants:

1. **Analyze the Java Method and Its Mutation**:
   - Clearly identify the method's parameters, return type, and intended functionality.
   - Examine the mutated code carefully to determine how the functionality has changed. For example, if a loop condition changes from <= to <, thoroughly consider how the loop's boundary behavior is impacted.
   - Deeply consider any called methods whose implementations are not shown. These methods may alter object states or variables more significantly than initially expected.

2. **Design Test Cases to Capture the Mutant**:
   - Develop test cases targeted specifically at capturing the behavior altered by the mutation. Clearly define the intended behavior of the original method, then contrast this with how the mutation affects outcomes.
   - Include scenarios covering typical usage, edge cases, and boundary conditions relevant to the mutation.
   - Ensure your test cases explicitly address logical differences introduced by the mutation, such as altered loop boundaries, modified exception handling, or changes in data processing logic.

3. **Implement the Test Method**:
   - Make Sure your variables are declared inside your test Method.
   - Write a test method annotated with @Test for each test case.
   - Use assertions (e.g., assertEquals, assertTrue) to verify the expected outcomes, ensuring that the test cases catch the mutated behavior.
   - Make sure the test is clear, logical, and thorough.



Your Task:

Given the following Java method and survived mutant, generate a JUnit test method that thoroughly tests the method and detect the mutant. Utilize your reasoning ability to ensure that all possible scenarios and edge cases are considered.

_Input Java Method ({method_name})_:
```
{method_code}
```
_Mutant Info_:
{mutant_info}

_Other Class Variables_:

{class_variables}

_Other Methods declared in the Class_(No method body showed):

{method_info}

_Constructors of the class object:

{Constructors}
_Guidelines_:

- **Annotations**: Use @Test to annotate each test method.
- **Assertions**: Use appropriate assertions to validate expected outcomes and catch the mutant.
- **Completeness**: You should complete every assertions on your own, I would not add anything to your code. Make sure your test is as complete as you can. 
- **Exception Handling**: Ensure that methods throwing exceptions are properly tested.
- **Thinking Step by Step**:
   - Understand the effect of the mutant on the code's logic and structure.
   - Organize the test cases logically within the test method, ensuring readability and clarity.
   - Keep the test cases small, focused, and meaningful.

- **Version Of Java and Junit**:
   - Use Java 1.8 and JUnit 4. Make sure not to use features from JUnit 5 (e.g., assertThrows), and instead, use ExpectedException if needed.
   - Use @Test to mark your test methods and ensure that the test cases are correctly structured and implemented.
   - Mockito is statically imported , so use "mock" instead of "Mockito.mock", If you doesn't know the behavior of other class(not the tested class), you can mock it instead.
   - Do not abuse using "mockito", make sure you interact with the mocked object.

_Output Format_:
1.Tested method analysis:
The tested method is intended to ......
2.Mutation analysis:
The mutation mutated the code...... to ......, makes ...... different from the original behavior.
3.Prepare for the test:
The different behavior can be tested by monitoring ......., so ......
4.Generate the test case:
```
<test method>
```
'''
        prompt = ChatPromptTemplate.from_template(prompt_template)

        test_methods = []
        method_infos = []
        methodstr = ""
        constructors = extracted[0].get('constructors', [])
        constrstr = []
        
        for method in extracted:
            method_info = method['method']
            method_info_without_body = {key: value for key, value in method_info.items() if key != 'body'}
            method_infos.append(method_info_without_body)
            parameterstr = ""
            first = 0
            for param in method_info['parameters']:
                if first != 0:
                    parameterstr+= " "
                first = 1
                parameterstr += f"{param['type']} {param['name']}"
            methodstr += f"{method_info['modifiers']} {method_info['return_type']} {method_info['name']} ({parameterstr});\n"
        first_method = 1
        if constructors:
            for constructor in constructors:
                cons_modifiers = constructor['modifiers']
                cons_name = constructor['name']
                cons_parameters = constructor['parameters']
                cons_body = constructor['body']
                
                params_str = ', '.join([f"{param['type']} {param['name']}" for param in cons_parameters])
                
                constrstr.append(f"  - {cons_modifiers} {cons_name}({params_str}) {{")

                for line in cons_body.split('\n'):
                    constrstr.append(f"      {line.strip()}")
                constrstr.append("  }")
                if cons_name in methods:
                    try:
                        class_variables = method['class_variables']
                        class_json = json.dumps(class_variables, indent=2)           
                        chain = prompt | llm
                        test_method = chain.invoke({"method_name":{cons_name},
                    "method_code":{cons_body},
                    "class_variables":f"class_name:{class_name}\nclass variables:{class_json}\n",
                    "method_info":f"all methods: {methodstr}",
                    "Constructors":f"Constructors: {constrstr}",
                    #"mutant_info":f" "
                    #"mutant_info":f"{survived_mutant['description']}",
                    "mutant_info":f"in the upper method, code{survived_mutant['precode']} was mutated to {survived_mutant['aftercode']}, however, it was not detected by previous test cases, plase add a new test case to detect it!"
                    })
                        print("hahaha")
                        new_pure_methods = extract_pure_test_method(test_method)
                        print(f"Generated Test Method for {cons_name} .")
                        if new_pure_methods:
                            if first_method:
                                for test_method_name, pure_method in pure_test_methods:
                                    test_methods.append({
                            "method_name": cons_name,
                            "method_code": cons_body,
                            "test_method_name": test_method_name,
                            "pure_test_method": pure_method.strip()
                            })
                            backup_tci ={
                        "class_name": class_name,
                        "test_class_name": f"{class_name}Test",
                        "test_methods": test_methods
                    }
                            first_method = 0
                        for test_method_name, pure_method in new_pure_methods:
                            test_methods.append({
                        "method_name": cons_name,
                        "method_code": cons_body,
                        "test_method_name": test_method_name,
                        "pure_test_method": pure_method.strip()
                        })
                        else:
                            print(f"no pure method errors extracted for {cons_name}.")
            

                    except ValueError as ve:
                        print(f"prompt template error for {cons_name}: {ve}")
                        continue
                    except Exception as e:
                        print(f" LLM failed for {cons_name}: {e}")
                        traceback.print_exc()  
                        continue
        else:
            constrstr.append("  - No constructors found.")
        constructors_str = "\n".join(constrstr)

        for method in extracted:
            method_info = method['method']
            method_name = method_info['name']
            method_code = method_info['body']
            if method_name not in methods:
            #if method_name in 
                continue
        
            try:
                class_variables = method['class_variables']
                class_json = json.dumps(class_variables, indent=2)           
                chain = prompt | llm
                test_method = chain.invoke({"method_name":{method_name},
                "method_code":{method_code},
                "class_variables":f"class_name:{class_name}\nclass variables:{class_json}\n",
                "method_info":f"all methods: {methodstr}",
                "Constructors":f"Constructors: {constructors_str}",
                #"mutant_info":f"{survived_mutant['description']}"
                #"mutant_info":f" "
                "mutant_info":f"in the upper method, code{survived_mutant['precode']} was mutated to {survived_mutant['aftercode']},however, it was not detected by previous test cases, plase add a new test case to detect it!"
                })
            
                new_pure_methods = extract_pure_test_method(test_method)
                print(f"Generated Test Method for {method_name} .")
                if new_pure_methods:
                    if first_method:
                        for test_method_name, pure_method in pure_test_methods:
                            test_methods.append({
                            "method_name": method_name,
                            "method_code": method_code,
                            "test_method_name": test_method_name,
                            "pure_test_method": pure_method.strip()
                            })
                        backup_tci ={
                        "class_name": class_name,
                        "test_class_name": f"{class_name}Test",
                        "test_methods": test_methods
                    }
                        first_method = 0
                    for test_method_name, pure_method in new_pure_methods:
                        test_methods.append({
                        "method_name": method_name,
                        "method_code": method_code,
                        "test_method_name": test_method_name,
                        "pure_test_method": pure_method.strip()
                        })
                else:
                    print(f"Unable to extract pure test method for {method_name}.")
            

            except ValueError as ve:
                print(f"Prompt template format error for {method_name}: {ve}")
                continue
            except Exception as e:
                print(f"LLM call failed for {method_name}: {e}")
                traceback.print_exc()
                continue
        if test_methods == []:
            for test_method_name, pure_method in pure_test_methods:
                            test_methods.append({
                            "method_name": method_name,
                            "method_code": method_code,
                            "test_method_name": test_method_name,
                            "pure_test_method": pure_method.strip()
                            })

        
        test_class_info = {
    "class_name": class_name,
    "test_class_name": f"{class_name}Test",
    "test_methods": test_methods
}
        test_class_info = ensure_unique_test_method_names(test_class_info) # theoretically, no need for the =
        test_class_code = assemble_test_class(test_class_info,imports,pkg,simple_mode,import_str)
        update_test_class_info(test_class_code,test_class_info)
        test_dir=base_dir+"/gentest"
        back_up_dir = base_dir+"/backup"
        pos = pkg + ".tests"
        test_positions=pos.split(".")
        for layer in test_positions:
            test_dir+=("/"+layer)
            back_up_dir+=("/"+layer)
        
        test_class_code,resp,errors= compile_and_run(test_class_code,test_class_info,test_dir,base_dir)
        if Rfirst:
            os.makedirs(back_up_dir, exist_ok=True)
            backup_path = os.path.join(back_up_dir, f"{test_class_info['test_class_name']}.java")
            try:
                with open(backup_path, 'w', encoding='utf-8') as test_file:
                    test_file.write(backup_tcc)
            except Exception as e:
                print(f"Error writing file: {e}")
            Rfirst = False
            print(f"\nbackup: {backup_path}")
     
        if resp == 0:
            is_llm_success, test_class_code, errors, test_class_info = llm_based_fix(
                 test_class_code, errors, test_class_info, base_dir, test_dir, 0,tested_file,llm,pkg
                )
            if is_llm_success:
                backup_tci = test_class_info
                backup_tcc = test_class_code
                backup_tcc,resp,errors= compile_and_run(backup_tcc,backup_tci,test_dir,base_dir)
                continue
            else:
                test_class_info = backup_tci
                test_class_code = backup_tcc
                backup_tcc,resp,errors= compile_and_run(backup_tcc,backup_tci,test_dir,base_dir)
                print("not able to fix, skip this mutant")
        else:
            backup_tci = test_class_info
            backup_tcc = test_class_code
            backup_tcc,resp,errors= compile_and_run(backup_tcc,backup_tci,test_dir,base_dir)
            continue
    test_class_code,resp,errors= compile_and_run(test_class_code,test_class_info,test_dir,base_dir)
    print("enhance ended")
    return resp,errors


def enhance_from_details(test_file: str,
                         details_txt: str,
                         project_src_root: str,
                         llm,
                         base_dir: str):

    mutants = []
    with open(details_txt, encoding="utf-8") as f:
        for line in f:
            if not line.startswith("MutationDetails"):
                continue
            m = DETAILS_RE.search(line)
            if not m:
                continue
            clazz, method, line_no, desc = m.groups()
            java_path = Path(project_src_root) / Path(clazz.replace(".", "/") + ".java")
            mutants.append({
                "survived": True,
                "filepath": str(java_path),
                "line": int(line_no),
                "description": desc          
            })

    if not mutants:
        print("details.txt has no MutationDetails")
        return -1, []

    with tempfile.NamedTemporaryFile(mode="w", suffix=".json", delete=False) as tmp:
        json.dump(mutants, tmp, indent=2, ensure_ascii=False)
        mutants_json_path = tmp.name

    resp, errors = tc_enhance(
        test_file=test_file,
        mutants_file=mutants_json_path,
        llm=llm,
        base_dir=base_dir
    )
    os.unlink(mutants_json_path)
    return resp, errors

def TC_ENHANCE(proj,id,base_dir,test_file,mutants_dir,LLM):
    rbase_dir = base_dir + "/%s/%s_" % (proj,proj) +str(id+1)+"_fixed"
    rbase_dir = rbase_dir.strip()
    mutants_file = mutants_dir + "/%s/%s_" % (proj,proj) +str(id+1)+"_test.json"
    
    return tc_enhance(test_file,mutants_file,LLM,rbase_dir)

if __name__ == "__main__":
    test_file = "./defects4j_fixed/Compress/Compress_31_fixed/gentest/org/apache/commons/compress/archivers/tar/tests/TarUtilsTest.java"
    mutants_file = "./Mutants/GPT3.5/tested/Compress/Compress_31_test.json"
    base_dir = "./defects4j_fixed/Compress/Compress_31_fixed"
    llm =   Deepseek(api_key=os.getenv("DEEPSEEK_API_KEY"), model="deepseek-chat")

    tc_enhance(test_file,mutants_file,llm,base_dir)
    # should be changed to (PROJ,ID,BASE_DIR,Mutants_dir,LLM)
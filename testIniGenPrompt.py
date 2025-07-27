# testIniGenPrompt.py

import os
import re
import json
import subprocess
import traceback
import signal
import config
from langchain.prompts import ChatPromptTemplate
from model import GPT  
from extract import extract_method  
from compress import generate_tar_bz2
from config import error_logger


def extract_pure_test_method(test_method_str):
    
    pure_test_methods = []
    length = len(test_method_str)
    index = 0
    while index < length:
        
        test_index = test_method_str.find('@Test', index)
        if test_index == -1:
            break  
        
        
        method_start = test_method_str.find('public void', test_index)
        if method_start == -1:
            index = test_index + 6  
            continue  
       
        brace_start = test_method_str.find('{', method_start)
        if brace_start == -1:
            index = method_start + 11  
            continue  

        
        stack = []
        current_index = brace_start
        stack.append('{')
        current_index += 1  

        while current_index < length and stack:
            char = test_method_str[current_index]
            if char == '{':
                stack.append('{')
            elif char == '}':
                stack.pop()
            current_index += 1

        if not stack:
            
            pure_method = test_method_str[test_index:current_index]
            pure_method = pure_method.replace("Mockito.", "")  

            
            method_name_match = re.search(r'public\s+void\s+(\w+)\s*\(', pure_method)
            if method_name_match:
                test_method_name = method_name_match.group(1)
                pure_test_methods.append((test_method_name, pure_method))
            else:
                print("Warning: Could not extract method name from a pure_test_method.")
            
            index = current_index  
        else:
            
            index = brace_start + 1

    
    # print("Annotations in @Test",annotations)
    return pure_test_methods
def ensure_unique_test_method_names(test_class_info):
    
    method_name_counts = {}
    updated_test_methods = []
    flag = 1
    for test_method in test_class_info.get('test_methods', []):
        original_name = test_method['test_method_name']
        original_name_removing_numbers = re.sub(r'\d+$', '', original_name)
       
        if original_name_removing_numbers not in method_name_counts:
            method_name_counts[original_name_removing_numbers] = 1
            new_name = original_name_removing_numbers  
        else:
            method_name_counts[original_name_removing_numbers] += 1
            suffix = method_name_counts[original_name_removing_numbers] - 1
            new_name = f"{original_name_removing_numbers}{suffix}"  
            

     
        if new_name != original_name:
            pure_code = test_method['pure_test_method']
            
            
            pattern = re.compile(r'(public\s+void\s+)' + re.escape(original_name) + r'(\s*\()')
            pure_code_new = pattern.sub(r'\1' + new_name + r'\2', pure_code, count=1)
            
            
            if pure_code_new == pure_code:
                
                pattern_underscore = re.compile(r'(public\s+void\s+)' + re.escape(original_name) + r'(\s*\()')
                pure_code_new = pattern_underscore.sub(r'\1' + new_name + r'\2', pure_code, count=1)
            
           
            test_method['test_method_name'] = new_name
            test_method['pure_test_method'] = pure_code_new
            print(f"Method Name Changed: {original_name} -> {new_name}")
            flag = 0
        else:
            print(f"No repetition in Method names: {original_name}")

        updated_test_methods.append(test_method)

    test_class_info['test_methods'] = updated_test_methods
    if flag == 0:
        return ensure_unique_test_method_names(test_class_info)
    return test_class_info
def initial_gen_testcase_by_methods(class_path,llm,methods):
    error_logger.info(f"alive {class_path}")
    try:
        if methods[0] == "No":
            return initial_gen_testcase_per_class(class_path, llm)
        else :
            
            extracted = extract_method(class_path)
            
        if not extracted:
            return {"error": "Unable to Extract Any methods or Class Variables"}
    except IndexError as e:
        print("sth wrong gen whole class")
        return initial_gen_testcase_per_class(class_path, llm)
    except Exception as e:
        traceback.print_exc()

    
    class_name = os.path.splitext(os.path.basename(class_path))[0]
    with open(class_path, 'r', encoding='utf-8') as file:
        class_code = file.read()


    prompt_template = '''
Instruction:

You are an expert Java developer and software tester. Your task is to generate full JUnit test methods for a given Java method inside a Java class. Follow these steps to ensure comprehensive and effective test coverage:

1. **Analyze the Java Method**:
   - Identify the method's parameters and return type.
   - Understand the functionality and purpose of the method.

2. **Design Test Cases**:
   - For each method, design test cases that cover typical usage, edge cases, and potential error conditions.
   - Ensure that all possible execution paths are tested.

3. **Implement the Test Method**:
   - Make Sure your variables are declared inside your test Method.
   - Write a test method annotated with @Test for each test case.
   - Use assertions to verify the expected outcomes.
   - Finish the code on your own including the assertions, you are the project owner instead of me.

**Example**:

_Input Java Method (add)_:

```
public class Calculator{{
...
public int add(int a, int b) {{ return a + b; }}
...
}}

```


_Generated JUnit Test Method (testAdd_TypicalValues)_:
```
@Test 
public void testAdd_TypicalValues() 
{{ 
    Calculator calculator = new Calculator();
    assertEquals(5, calculator.add(2, 3));
    assertEquals(-1, calculator.add(-2, 1));
    assertEquals(0, calculator.add(0, 0)); }}
```  
Your Task:

Given the following Java method, generate a complete JUnit test method that thoroughly tests the method. Utilize your reasoning ability to ensure that all possible scenarios and edge cases are considered.

_Input Java Method ({method_name})_:

method_body:
```
{method_code}
```
_Other Class Variables_:

{class_variables}

_Other Methods in the Class_(no method body showed):

{method_info}

_Constructors of the class object:
{Constructors}
_Guidelines_:

- **Annotations**: Use @Test to annotate each test method.
- **Assertions**: Use appropriate assertions to validate expected outcomes.
- **Completeness**: You should complete every assertions on your own, I would not add anything to your code. Make sure your test is as complete as you can. 
- **Exception Handling**: Ensure that methods throwing exceptions are properly tested.
- **Thinking Step by Step**:
   - Begin by thoroughly understanding the structure and functionality of the provided Java method. Think critically about different scenarios and edge cases that the method should handle. Organize the test cases logically within the test method to maintain clarity and readability.

- **Version Of Java and Junit**:
   - Use Version 1.8 of Java and Junit 4, you need to be aware of this.(Do not use Junit 5's feature like assertThrows,use ExpectedException instead)
   - Still need to use @Test to mark your respond.
- **Mockito**:
   - Mockito is statically imported , so use "mock" instead of "Mockito.mock", If you really doesn't know the behavior of other class(not the tested class), you can mock it instead.
   - Do not abuse using Mockito, because wrongly using Mockito might lead to the test method hanging or timeout.
_Note_:
- I use triple backticks to mark the code sessions.
- Do not give me the skeleton, give me the full code and everything should be implemented inside the code.
'''
    prompt_unitest = """
The focal method is `{focal_method}` in the focal class `{class_name}`, and their information is
```{full_fm}```.

- **Annotations**: Use @Test to annotate each test method.
- **Assertions**: Use appropriate assertions to validate expected outcomes.
- **Completeness**: You should complete every assertions on your own, I would not add anything to your code. Make sure your test is as complete as you can. 
- **Exception Handling**: Ensure that methods throwing exceptions are properly tested.
"""
    
    prompt = ChatPromptTemplate.from_template(prompt_template)
    #prompt = ChatPromptTemplate.from_template(prompt_unitest)
   
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
        #print(methodstr)
    
    if constructors:
            
            for constructor in constructors:
                cons_modifiers = constructor['modifiers']
                cons_name = constructor['name']
                cons_parameters = constructor['parameters']
                cons_body = constructor['body']
                
                
                params_str = ', '.join([f"{param['type']} {param['name']}" for param in cons_parameters])
                james = ""
                james = f"  - {cons_modifiers} {cons_name}({params_str}) {{"
                #constrstr.append(f"  - {cons_modifiers} {cons_name}({params_str}) {{")
                
                for line in cons_body.split('\n'):
                    james+=f"{line.strip()}\n"
                james+="  }"
                constrstr.append(james)
                if cons_name in methods:
                    cons_name = constructors[0]['name']
                    try:
                        class_variables = method['class_variables']
                        class_json = json.dumps(class_variables, indent=2)           
                        chain = prompt | llm
                        test_method = chain.invoke({"method_name":{cons_name},
                    "method_code":{james},
                    "class_variables":f"class_name:{class_name}\nclass variables:{class_json}\n",
                    "method_info":f"all methods: {methodstr}",
                    "Constructors":f"There is no need for other constructors."
                    })
                    #------upper for our method , lower for chatunitest
            #             test_method = chain.invoke({"focal_method":{james},
            # "full_fm":{class_code},
            # "class_name":f"{class_name}",})
                        
                        #print(test_method)
                        pure_methods = extract_pure_test_method(test_method)
                        print(f"Generated Test Method for {cons_name} .")
                        if pure_methods:
                            for test_method_name, pure_method in pure_methods:
                                test_methods.append({
                                "method_name": cons_name,
                                "method_code": cons_body,
                                "test_method_name": test_method_name,
                                "pure_test_method": pure_method.strip()
                            })
                        else:
                            print(f"Unable to Extract Pure Test Method for {cons_name}.")
            

                    except ValueError as ve:
                        print(f"Prompt ERROR for {cons_name}: {ve}")
                        continue
                    except Exception as e:
                        print(f"LLM Error for {cons_name}: {e}")
                        traceback.print_exc()  
                        continue
            
    else:
        constrstr.append("  - No constructors found.")
    constructors_str = "\n".join(constrstr)
    for method in extracted:
        
                
        method_info = method['method']
        method_modifiers = method_info['modifiers']
        method_parameters = method_info['parameters']
        method_name = method_info['name']
        method_code = method_info['body']
        
        if method_name not in methods:
            #if method_name in 
            continue

      
        try:

            class_variables = method['class_variables']
            class_json = json.dumps(class_variables, indent=2)
            methods_json = json.dumps(method_infos,indent = 2)
            
            params_str = ', '.join([f"{param['type']} {param['name']}" for param in method_parameters])
            mames = ""
            mames = f"  - {method_modifiers} {method_name}({params_str}) {{"
                #constrstr.append(f"  - {cons_modifiers} {cons_name}({params_str}) {{")
                
            for line in method_code.split('\n'):
                mames+=f"{line.strip()}\n"
            mames+="  }"
            chain = prompt | llm
            test_method = chain.invoke({"method_name":{method_name},
            "method_code":{method_code},
            "class_variables":f"class_name:{class_name}\nclass variables:{class_json}\n",
            "method_info":f"all methods: {methodstr}",
            "Constructors":f"Constructors: {constructors_str}"
            })
            # -------------uppper our meth, lower chatunitesst
            # test_method = chain.invoke({"focal_method":{mames},
            # "full_fm":{class_code},
            # "class_name":f"{class_name}",})
            pure_methods = extract_pure_test_method(test_method)
            print(f"Generated Test Method for {method_name} .")
            if pure_methods:
                for test_method_name, pure_method in pure_methods:
                    test_methods.append({
                        "method_name": method_name,
                        "method_code": method_code,
                        "test_method_name": test_method_name,
                        "pure_test_method": pure_method.strip()
                    })
            else:
                print(f"Unable to Extract Pure Test Method for {method_name}.")
            

        except ValueError as ve:
            print(f"Prompt ERROR for {method_name}: {ve}")
            continue
        except Exception as e:
            print(f"LLM Error for {method_name}: {e}")
            traceback.print_exc()  
            continue

  
    test_class_info = {
    "class_name": class_name,
    "test_class_name": f"{class_name}Test",
    "test_methods": test_methods
}


    test_class_json = json.dumps(test_class_info, indent=4, ensure_ascii=False)

    return test_class_json

def initial_gen_testcase_per_class(class_path, llm):
   
    extracted = extract_method(class_path)
    if not extracted:
        return {"error": "Unable to Extract Any methods or Class Variables"}

   
    class_name = os.path.splitext(os.path.basename(class_path))[0]

    with open(class_path, 'r', encoding='utf-8') as file:
        class_code = file.read()

    prompt_template = '''
Instruction:

You are an expert Java developer and software tester. Your task is to generate full JUnit test methods for a given Java method inside a Java class. Follow these steps to ensure comprehensive and effective test coverage:

1. **Analyze the Java Method**:
   - Identify the method's parameters and return type.
   - Understand the functionality and purpose of the method.

2. **Design Test Cases**:
   - For each method, design test cases that cover typical usage, edge cases, and potential error conditions.
   - Ensure that all possible execution paths are tested.

3. **Implement the Test Method**:
   - Make Sure your variables are declared inside your test Method.
   - Write a test method annotated with @Test for each test case.
   - Use assertions to verify the expected outcomes.
   - Finish the code on your own including the assertions, you are the project owner instead of me.

**Example**:

_Input Java Method (add)_:

```
public class Calculator{{
...
public int add(int a, int b) {{ return a + b; }}
...
}}

```


_Generated JUnit Test Method (testAdd_TypicalValues)_:
```
@Test 
public void testAdd_TypicalValues() 
{{ 
    Calculator calculator = new Calculator();
    assertEquals(5, calculator.add(2, 3));
    assertEquals(-1, calculator.add(-2, 1));
    assertEquals(0, calculator.add(0, 0)); }}
```  
Your Task:

Given the following Java method, generate a complete JUnit test method that thoroughly tests the method. Utilize your reasoning ability to ensure that all possible scenarios and edge cases are considered.

_Input Java Method ({method_name})_:

method_body:
```
{method_code}
```
_Other Class Variables_:

{class_variables}

_Other Methods in the Class_(no method body showed):

{method_info}

_Constructors of the class object:
{Constructors}
_Guidelines_:

- **Annotations**: Use @Test to annotate each test method.
- **Assertions**: Use appropriate assertions to validate expected outcomes.
- **Completeness**: You should complete every assertions on your own, I would not add anything to your code. Make sure your test is as complete as you can. 
- **Exception Handling**: Ensure that methods throwing exceptions are properly tested.
- **Thinking Step by Step**:
   - Begin by thoroughly understanding the structure and functionality of the provided Java method. Think critically about different scenarios and edge cases that the method should handle. Organize the test cases logically within the test method to maintain clarity and readability.

- **Version Of Java and Junit**:
   - Use Version 1.8 of Java and Junit 4, you need to be aware of this.(Do not use Junit 5's feature like assertThrows,use ExpectedException instead)
   - Still need to use @Test to mark your respond.
- **Mockito**:
   - Mockito is statically imported , so use "mock" instead of "Mockito.mock", If you really doesn't know the behavior of other class(not the tested class), you can mock it instead.
   - Do not abuse using Mockito, because wrongly using Mockito might lead to the test method hanging or timeout.
_Note_:
- I use triple backticks to mark the code sessions.
- Do not give me the skeleton, give me the full code and everything should be implemented inside the code.
'''
    prompt_unitest = '''
    
    Here are the information of the focal method and class: 
The focal method is {focal_method} in the focal class {class_name}, and their information is
```{full_fm}```.
'''
    prompt = ChatPromptTemplate.from_template(prompt_template)
    #prompt = ChatPromptTemplate.from_template(prompt_unitest)
    test_methods = []
    method_infos = []
    constructors = extracted[0].get('constructors', [])
    constrstr = []
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
    else:
        constrstr.append("  - No constructors found.")
    constructors_str = "\n".join(constrstr)
    methodstr = ""
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
        methodstr+=(f"{method_info['modifiers']} {method_info['return_type']} {method_info['name']} ({parameterstr});\n")
    for method in extracted:
        method_info = method['method']
        method_name = method_info['name']
        method_code = method_info['body']
       

        try:
            class_variables = method['class_variables']
            class_json = json.dumps(class_variables, indent=2)
            chain = prompt | llm
            test_method = chain.invoke({"method_name":{method_name},
            "method_code":{method_code},
            "class_variables":f"class_name:{class_name}\nclass variables:{class_json}\n",
            "method_info":f"all methods: {methodstr}",
            "Constructors":f"Constructors: {constructors_str}"
            })
            # --------------------
            # test_method = chain.invoke({"focal_method":{method_code},
            # "full_fm":{class_code},
            # "class_name":f"{class_name}",})
            pure_methods = extract_pure_test_method(test_method)
            print(f"Generated Test Method for {method_name} .")
            if pure_methods:
                for test_method_name, pure_method in pure_methods:
                    test_methods.append({
                        "method_name": method_name,
                        "method_code": method_code,
                        "test_method_name": test_method_name,
                        "pure_test_method": pure_method.strip()
                    })
            else:
                print(f"Unable to Extract Pure Test Method for {method_name}.")
            

        except ValueError as ve:
            print(f"Prompt ERROR for {method_name}: {ve}")
            continue
        except Exception as e:
            print(f"LLM Error for {method_name}: {e}")
            traceback.print_exc()  
            continue

    
    test_class_info = {
    "class_name": class_name,
    "test_class_name": f"{class_name}Test",
    "test_methods": test_methods
}


    test_class_json = json.dumps(test_class_info, indent=4, ensure_ascii=False)

    return test_class_json
def assemble_test_class(json_data,imports,package,simple_mode=0,real_str=""):
    """
    根据生成的 JSON 数据拼合成完整的 JUnit 测试类代码。
    """
    if "error" in json_data:
        print(f"错误: {json_data['error']}")
        return ""
    class_name = json_data.get("class_name", "UnknownClass")
    test_class_name = json_data.get("test_class_name", f"{class_name}Test")
    test_methods = json_data.get("test_methods", [])
    import_string = ""
    tested_class = package+"."+class_name
    pos = "gentest."+ package + ".tests"
    if simple_mode:
        test_class_code = real_str
        for test_method in test_methods:
            pure_test_method = test_method.get("pure_test_method", "")
            if pure_test_method:
            
                indented_test_method = '\n'.join([' ' + line for line in pure_test_method.split('\n')])
                test_class_code += f"{indented_test_method}\n\n"
        test_class_code += "}\n"
        return test_class_code

   
    for simgle_import in imports:
        import_string += "import "
        import_string += simgle_import
        import_string += ";\n"
    
    test_class_code = f"""package {pos};
import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import java.lang.reflect.Method;
import java.lang.reflect.Field;
import java.lang.reflect.Constructor;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import {package}.*;
{import_string} 
public class {test_class_name} {{
    @Rule
    public ExpectedException thrown = ExpectedException.none();

"""

    
    for test_method in test_methods:
        pure_test_method = test_method.get("pure_test_method", "")
        if pure_test_method:
            
            indented_test_method = '\n'.join([' ' + line for line in pure_test_method.split('\n')])
            test_class_code += f"{indented_test_method}\n\n"

    test_class_code += "}\n"

    return test_class_code
def extract_errors(javac_output):
    error_lines = []
    error_pattern = re.compile(
        r'\[javac\]\s+(.+?):(\d+):\s*(错误|error):\s*(.+)'
    )
    try:
        for line in javac_output.splitlines():
            match = error_pattern.search(line)
            if match:
                file_path = match.group(1).strip()
                line_num = int(match.group(2))
                error_type = match.group(3).strip()
                message = match.group(4).strip()
            
                error_info = {
                'file': file_path,
                'line': line_num,
                'type': error_type,
                'message': message
            }
                error_lines.append(error_info)
    except Exception as e:
        print(f"Error extracting errors: {e}")
        error_logger.info(f"Error in extracting error lines: {e}")
    return error_lines
def display_errors(errors):
    if not errors:
        print("No Compile Errors Found.")
        return
    
    print("Extracted Error Messages：\n")
    for error in errors:
        print(f"File: {error['file']}")
        print(f"Line: {error['line']}")
        print(f"Error Type: {error['type']}")
        print(f"Error Message: {error['message']}\n")
def update_test_class_info(test_class_code, test_class_info):
    '''
    This function updates the 'pure_test_method' in 'test_class_info' based on the new 'test_class_code'.
    It parses the 'test_class_code' to extract all pure test methods, matches them to their corresponding
    test methods using 'test_method_name', and updates the 'pure_test_method' for each entry in 'test_class_info'.

    Parameters:
        test_class_code (str): The complete Java test class code containing all test methods.
        test_class_info (dict): The existing test class information, containing 'test_methods'.

    Returns:
        dict: The updated test_class_info with updated 'pure_test_method's.
    '''

    pure_test_methods = extract_pure_test_method(test_class_code)
    if not pure_test_methods:
        print("Warning: No pure test methods were extracted from the provided test_class_code.")
        return 0,test_class_info

    
    test_method_name_to_pure_method = {}
    for test_method_name, pure_method_code in pure_test_methods:
        test_method_name_to_pure_method[test_method_name] = pure_method_code.strip()

    
    for test_method_entry in test_class_info.get('test_methods', []):
        test_method_name = test_method_entry.get('test_method_name')
        if not test_method_name:
            print("Warning: A test_method entry is missing the 'test_method_name' field.")
            continue
        pure_method_code = test_method_name_to_pure_method.get(test_method_name)
        if pure_method_code:
            test_method_entry['pure_test_method'] = pure_method_code
            print(f"Updated pure_test_method for test_method_name '{test_method_name}'.")
        else:
            print(f"Warning: No pure test method found for test_method_name '{test_method_name}'.")
    test_class_info = ensure_unique_test_method_names(test_class_info)
    return 1,test_class_info

def delete_last_test_method_and_regenerate(test_class_code, test_class_infos, imports, pkg):
  
    if not test_class_infos.get('test_methods'):
        error_logger.info(f"No more methods to delete and")
        return test_class_code,-1

    # 删除最后一个测试方法
    removed = test_class_infos['test_methods'].pop()
    print(f"Remove {removed['test_method_name']} Due to Timeout")
    error_logger.info(f"Remove {removed['test_method_name']} Due to Timeout")

    # 重新组装测试类
    new_code = assemble_test_class(
        test_class_infos,
        imports,
        pkg,
        simple_mode=0
    )
    return new_code,0

def compile_and_run(test_class_code, test_class_infos, test_dir, base_dir):
    archive_name = ""
    if "error" not in test_class_infos:
        os.makedirs(test_dir, exist_ok=True)
        test_class_path = os.path.join(test_dir, f"{test_class_infos['test_class_name']}.java")
        with open(test_class_path, 'w', encoding='utf-8') as test_file:
            test_file.write(test_class_code)
        archive_name = generate_tar_bz2(base_dir + "/gentest", base_dir)

    command = f'defects4j test -w {base_dir} -s {archive_name}'
    process = subprocess.Popen(
            command, shell=True, text=True, stdout=subprocess.PIPE, stderr=subprocess.PIPE
        )
    stdout =0 
    stderr =0
    try:
        stdout, stderr = process.communicate(timeout=500)  # 使用 communicate 方法等待进程完成
        process.wait()
    except subprocess.TimeoutExpired:
        print(f"Timeout, Try to Delete the last test method..., {base_dir}")
        error_logger.info(f"Timeout, Try to Delete the last test method..., {base_dir}")
           
        process.terminate() 
        error_logger.info(f"terminating..., {base_dir}")
        process.wait()  
        error_logger.info(f"terminating..., {base_dir}")
            
        i = base_dir.rfind('/')
        if i != -1:
            suffix = base_dir[i+1:]
        else:
            suffix = base_dir  
        error_logger.info(suffix)
        try:
            result = subprocess.run(["ps", "aux"], stdout=subprocess.PIPE, text=True)
            for line in result.stdout.splitlines():
                if suffix in line:
                    parts = line.split()
                    pid = int(parts[1])
                    error_logger.info(f"Killing PID={pid} → {line}")
            os.kill(pid, signal.SIGKILL)
        except Exception as e:
            error_logger.info(f"Error killing process: {e}")
        file = os.path.join(test_dir, f"{test_class_infos['test_class_name']}.java")
        imports = extract_method(file)[0].get('imports', [])
        pkg = extract_method(file)[0].get('package', [])
            # 重新生成代码
        new_code, flag = delete_last_test_method_and_regenerate(
                test_class_code, test_class_infos, imports, pkg
            )
        test_class_code = new_code
        if flag == 0:
            test_class_code = new_code
            return compile_and_run(new_code, test_class_infos, test_dir, base_dir)  # 继续重试
        else:
            return test_class_code,0, "Overtime, no more methods to delete."
    if not stdout:
        print("Compilation Failed, extract error messages.")
        errors = extract_errors(stderr)
        display_errors(errors)
        return test_class_code,0, errors
    else:
        print("Test Complete,:", stdout)
        return test_class_code,1, stdout
def rule_based_fix(test_class_code, error_messages, test_class_infos, base_dir, test_dir, depth):
    
    # 最大递归深度，防止无限循环
    MAX_DEPTH = 10
    if depth >= MAX_DEPTH:
        print(f"Reached maximum recursion depth ({MAX_DEPTH}). Cannot fix further.")
        print(f"Reached {MAX_DEPTH}. No further Fix")
        return 0, test_class_code, error_messages, test_class_infos

    updated_code_lines = test_class_code.split('\n')
    modifications_made = False  # 标记是否进行了修改 / Flag to track if any modifications were made

    for error in error_messages:
        error_msg = error['message']
        line_num = error['line']

        # Debug: 打印正在处理的错误 / Debug: Print the error being processed
        print(f"Processing error at line {line_num}: {error_msg}")
        print(f"正在处理第 {line_num} 行的错误: {error_msg}")

        # 1. 处理缺少分号的错误 / Handle missing semicolon errors
        if ("expected ';'" in error_msg or "missing ';'" in error_msg or
            "预期';'" in error_msg or "缺少';'" in error_msg or "需要';'" in error_msg) :
            if 0 < line_num <= len(updated_code_lines):
                line = updated_code_lines[line_num - 1].rstrip()
                # 确保该行不是以 '{' 或 '}' 结尾 / Ensure the line does not end with '{' or '}'
                if not line.endswith(';') and not line.endswith('{') and not line.endswith('}'):
                    updated_code_lines[line_num - 1] = line + ';'
                    print(f"Added missing semicolon at line {line_num}. 在第 {line_num} 行添加缺少的分号。")
                    modifications_made = True

        # 2. 处理缺少闭合大括号的错误 / Handle missing closing bracket errors
        elif ("reached end of file while parsing" in error_msg or
              "reached end of input while parsing" in error_msg or
              "解析时已到达文件结尾" in error_msg or
              "在解析时到达输入末尾" in error_msg):
            # 统计大括号的数量 / Count the number of opening and closing brackets
            open_brackets = test_class_code.count('{')
            close_brackets = test_class_code.count('}')
            missing_brackets = open_brackets - close_brackets

            if missing_brackets > 0:
                for _ in range(missing_brackets):
                    updated_code_lines.append('}')
                print(f"Added {missing_brackets} missing closing bracket(s) at the end of the file. 在文件末尾添加了 {missing_brackets} 个缺失的闭合大括号。")
                modifications_made = True

        # 3. 处理“不是语句”的错误，这可能表示缺少分号或不完整的语句 / Handle "not a statement" errors which might indicate missing semicolons or incomplete statements
        elif ("not a statement" in error_msg or "不是语句" in error_msg):
            if 0 < line_num <= len(updated_code_lines):
                line = updated_code_lines[line_num - 1].rstrip()
                # 确保该行不是以 '{' 或 '}' 结尾 / Ensure the line does not end with '{' or '}'
                if not line.endswith(';') and not line.endswith('{') and not line.endswith('}'):
                    updated_code_lines[line_num - 1] = line + ';'
                    print(f"Added missing semicolon at line {line_num} to fix 'not a statement' error. 在第 {line_num} 行添加缺少的分号以修复“不是语句”错误。")
                    modifications_made = True

        # 4. 处理方法声明无效或非法类型开始的错误 / Handle invalid method declarations or illegal start of type errors
        elif ("invalid method declaration" in error_msg or
              "illegal start of type" in error_msg or
              "非法的类型开始" in error_msg or
              "无效的方法声明" in error_msg):
            # 尝试通过平衡大括号来修复不匹配的问题 / Attempt to fix mismatched brackets by balancing them
            open_brackets = test_class_code.count('{')
            close_brackets = test_class_code.count('}')
            if open_brackets > close_brackets:
                updated_code_lines.append('}')
                print("Added a missing closing bracket at the end of the file to fix syntax error. 在文件末尾添加了缺失的闭合大括号以修复语法错误。")
                modifications_made = True
            if "..." in updated_code_lines[line_num-1]:
                print("removing meaningless ... inside the code")
                modifications_made = True
                updated_code_lines[line_num-1] = updated_code_lines[line_num - 1].replace("...", "")
        elif ("中定义了方法" in error_msg 
              ):
            if 0 < line_num <= len(updated_code_lines):
                line = updated_code_lines[line_num - 1].rstrip()
                if("public void" in line):
                    parts = line.split('(')
                    if len(parts) > 1:
                        before_parenthesis = parts[0]
                        before_parenthesis = before_parenthesis+"_"
                        line = parts[0] + parts[1] 
                        updated_code_lines[line_num-1] = line   
            
        # 添加更多错误处理情况，如需要 / Add more error handling cases as needed

    # 如果没有进行任何修改，则没有可应用的基于规则的修复 / If no modifications were made, there's nothing to fix
    if not modifications_made:
        print("No applicable rule-based fixes found for the given errors. 未找到适用的基于规则的修复。")
        return 0, test_class_code, error_messages, test_class_infos

    # 重新组装更新后的代码 / Reassemble the updated code
    updated_code = '\n'.join(updated_code_lines)
    result,_ = update_test_class_info(updated_code,test_class_infos)
    if result == 0:
        return 0, updated_code, error_messages, test_class_infos
    # 尝试编译更新后的代码 / Attempt to compile the updated code
    updated_code,flag, compile_output = compile_and_run(updated_code, test_class_infos, test_dir, base_dir)

    if flag == 1:
        print("Rule-based fixes succeeded. The test class now compiles successfully. 基于规则的修复成功。测试类现在成功编译。")
        return 1, updated_code, compile_output, test_class_infos
    else:
        print("Rule-based fixes did not resolve all compilation errors. Attempting further fixes... 基于规则的修复未能解决所有编译错误。尝试进一步修复...")
        # / Extract new errors from the compilation output
        #new_error_messages = extract_errors(compile_output)
        # / Recursively attempt to fix the new errors
        return rule_based_fix(updated_code, compile_output, test_class_infos, base_dir, test_dir, depth + 1)

    


def llm_based_fix(test_class_code, error_messages, test_class_infos, base_dir, test_dir, depth, file_path, llm, pkg):
    
    
    def format_code_with_line_numbers(code_lines, start_line, error_lines=set()):
        formatted_code = []
        for idx, line in enumerate(code_lines, start=start_line):
            line_number = f"{idx:4}: "
            if idx in error_lines:
                formatted_code.append(f"**{line_number}{line}**")
            else:
                formatted_code.append(f"{line_number}{line}")
        return '\n'.join(formatted_code)
    class_name = os.path.splitext(os.path.basename(file_path))[0]

    MAX_DEPTH = 10
    if depth >= MAX_DEPTH:
        print(f"Reached {MAX_DEPTH}. No further Fix")
        return 0, test_class_code, error_messages, test_class_infos

    code_lines = test_class_code.split('\n')

    test_method_boundaries = []

    for idx, line in enumerate(code_lines):
        if '@Test' in line:
            method_signature = None
            for j in range(idx + 1, len(code_lines)):
                signature_match = re.search(r'public\s+void\s+(\w+)\s*\(', code_lines[j])
                if signature_match:
                    method_signature = j
                    break
                elif re.search(r'@\w+', code_lines[j]):
                    break
            if method_signature is None:
                continue  

            method_body_start = None
            for j in range(method_signature, len(code_lines)):
                if '{' in code_lines[j]:
                    method_body_start = j
                    break
            if method_body_start is None:
                continue  

            
            brace_count = 0
            method_end = method_body_start
            for k in range(method_body_start, len(code_lines)):
                brace_count += code_lines[k].count('{')
                brace_count -= code_lines[k].count('}')
                method_end = k
                if brace_count == 0:
                    break
            else:
                method_end = len(code_lines) - 1  

            #print("appending", method_signature, method_end)
            test_method_boundaries.append((method_signature, method_end))

    
    error_line_map = {}
    for error in error_messages:
        line_num = error.get('line', None)
        if line_num:
            if line_num not in error_line_map:
                error_line_map[line_num] = []
            error_line_map[line_num].append(error)

   
    test_method_errors = {}
    class_level_errors = []

    for line_num, errors in error_line_map.items():
       
        mapped = False
        for method_start, method_end in test_method_boundaries:
           
            if (method_start + 1) <= line_num <= (method_end + 1):
                method_key = (method_start, method_end)
                if method_key not in test_method_errors:
                    test_method_errors[method_key] = []
                test_method_errors[method_key].extend(errors)
                mapped = True
                break
        if not mapped:
          
            class_level_errors.extend(errors)

   
    affected_test_methods = {}
    for key, errors in test_method_errors.items():
        method_start, method_end = key
        method_code = code_lines[method_start:method_end + 1]
        affected_test_methods[key] = {
            'code': method_code,
            'errors': errors,
            'start': method_start,
            'end': method_end
        }

    
    def extract_tested_methods(file_path):
        
        tested_methods = {}
        constructors_str = "no constructors"
        method_infos = {}
        try:
            methods = extract_method(file_path)      
            constructors = methods[0].get('constructors', [])
            constrstr = []
            methodstr = ""
            if constructors:
                for constructor in constructors:
                    cons_modifiers = constructor['modifiers']
                    cons_name = constructor['name']
                    cons_parameters = constructor['parameters']
                    cons_body = constructor['body']
                
                    
                    params_str = ', '.join([f"{param['type']} {param['name']}" for param in cons_parameters])
                
                    constrstr.append(f"  - {cons_modifiers} {cons_name}({params_str}) {{")
                    
                    if cons_body and cons_name:
                        tested_methods[cons_name] = cons_body
                for line in cons_body.split('\n'):
                    constrstr.append(f"      {line.strip()}")
                    constrstr.append("  }")
            else:
                constrstr.append("  - No constructors found.")
            constructors_str = "\n".join(constrstr)
            for method in methods:
                method_info = method.get('method', {})
                method_name = method_info.get('name')
                method_body = method_info.get('body')
                parameterstr = ""
                first = 0
                for param in method_info['parameters']:
                    if first != 0:
                        parameterstr+= " "
                    first = 1
                    parameterstr += f"{param['type']} {param['name']}"
                methodstr+=(f"{method_info['modifiers']} {method_info['return_type']} {method_info['name']} ({parameterstr});\n")
                if method_name and method_body:
                    tested_methods[method_name] = method_body
        except Exception as e:
            print(f"Error extracting methods from {file_path}: {e}")
        return tested_methods,constructors_str,methodstr

    tested_methods_mapping,constrmsg,methodstr = extract_tested_methods(file_path)
    #print("methods_code",tested_methods_mapping)
    
    
    # print("methods_code2",tested_method_code_map)
    
    detailed_affected_test_methods = []
    for key, info in affected_test_methods.items():
        method_code = info['code']
        method_start = info['start']
        method_end = info['end']
        method_name_match = re.search(r'public\s+void\s+(\w+)\s*\(', '\n'.join(method_code))
        if method_name_match:
            test_method_name = method_name_match.group(1)
            
            corresponding_test_method = next(
                (m for m in test_class_infos['test_methods'] if m['test_method_name'] == test_method_name),
                None
            )
            if corresponding_test_method:
                tested_method_name = corresponding_test_method.get('method_name')
                tested_method_code = corresponding_test_method.get('method_code')
            else:
                tested_method_name = "UnknownMethod"
                tested_method_code = ""
            detailed_affected_test_methods.append({
                'test_method_name': test_method_name,
                'test_method_code': '\n'.join(method_code),
                'tested_method_name': tested_method_name,
                'tested_method_code': tested_method_code,
                'errors': info['errors'],
                'start': method_start,
                'end': method_end
            })
        else:
            print(f"无法提取测试方法名，方法起始于第 {info['start'] + 1} 行。")

    
    for test_method_info in detailed_affected_test_methods:
        test_method_name = test_method_info['test_method_name']
        tested_method_name = test_method_info['tested_method_name']
        tested_method_code = test_method_info['tested_method_code']
        test_method_code = test_method_info['test_method_code']
        errors = test_method_info['errors']
        start_line = test_method_info['start'] + 1  
        error_line_numbers = {error['line'] for error in errors}

 
        formatted_test_method_code = format_code_with_line_numbers(
           test_method_code.split('\n'), start_line, error_line_numbers
        )
        # print(f"```java\n{formatted_test_method_code}\n```")
        # print("-" * 40)

    
    if not detailed_affected_test_methods and not class_level_errors:
        print("No error in class level")
        return 0, test_class_code, error_messages, test_class_infos
    

    for test_method in detailed_affected_test_methods:
        test_method_name = test_method['test_method_name']
        tested_method_name = test_method['tested_method_name']
        tested_method_code = test_method['tested_method_code']
        test_method_code = test_method['test_method_code']
        errors = test_method['errors']
        constructor = constrmsg

       
        repair_prompt = """
        You are an expert Java developer and software tester. You need to fix the following JUnit test method that has compilation errors.
        **Test Method Name**: {test_method_name}
        **Test Method Code**:
        ```java
        @Test
        {test_method_code}
        ```
        **Name of the method which were tested**: {tested_method_name}
        **Code of the method which were tested**:
        ```java
        {tested_method_code}
        ```
        **Compilation Errors**:
        {errors}
        _Constructors of the class object:
        {constructor}
        other methods of the class:
        {Other_methods}
        **Instructions**:
        - Analyze the errors and modify the test method code to fix the compilation issues.
        - Do not alter the tested method(method that are being tested).
        - You should define all variables inside your test method. Make sure you use your defined/declared variables inside the test method.
        - You need to return the fixed test method(we only extract the method, not the whole class).
        - Your test method should start with an @Test annotation
        - Use Version 1.8 of Java and Junit 4, you need to be aware of this.(Do not use Junit 5's feature like "assertThrows")
        - When using reflection because private/protected variable/methods was used, Please notice that do not use ReflectiontestUtils because it is from spring framework. Our Program is not necessarily run at Spring framework. Use java.lang.reflect package Instead.
        - If you need to use Mockito, please make sure it is mocked correctly so that it will not cause the test procedure to hang.
        
        Hint：If you see the "symbol not found error",
        basically it is caused by using undefined variables, we only extract the test method, so the definition in test class outside the method will be missing.
        Please make sure you use only the in-method defined variables.Or re-define the variable inside the method.
        You should follow the output format.
        _Output Format_:
        1.Tested method analysis:
        The method being tested by our method is intended to ......
        2.Error analysis:
        The compilation error is..., which could be caused by ......
        3.Test method analysis:
        The test method line ... is causing the compilation error , we can fix the error by  ......
        4.Generate the test case:
        ```
        <fixed test method>
        ```
        """
        repair_prompt_unitest = """
        I need you to fix an error in a unit test, an error occurred while {error_type}.

The unit test is:
```
{unit_test}
```

The error message is:
```
{error_message}
```
Hint：If you see the "symbol not found error",
basically it is caused by using undefined variables, we only extract the test METHOD, so the definition in test class outside the method will be missing.
Please make sure you use only the in-method defined variables.Or re-define the variable inside the method.
The unit test is testing the method {method_name} in the class {class_name},
the source code of the method under test and its class is:
```
{method_code}
```

Please fix the error and return the whole fixed unit test. You can use Junit 4, Mockito 3 and reflection. No explanation is needed.
        """
        try:
            #prompt = ChatPromptTemplate.from_template(repair_prompt_unitest)
            prompt = ChatPromptTemplate.from_template(repair_prompt)
            chain = prompt | llm
            
            formatted_test_method_code = format_code_with_line_numbers(
            test_method_code.split('\n'), start_line, error_line_numbers
        )
            tmc = f"{formatted_test_method_code}```"
    
            #print("tmc:",tmc,"tested:",tested_method_code)
            corrected_test_method = chain.invoke({"test_method_name":{test_method_name},
            "test_method_code":{tmc},
            "tested_method_name":{tested_method_name},
            "tested_method_code":{tested_method_code},
            "errors":json.dumps(errors, ensure_ascii=False, indent=2),
            "constructor":{constructor},
            "Other_methods":{methodstr}
            })
            # ----------------------
            # corrected_test_method_unitest = chain.invoke({
            #     "error_type": "compilation",
            #     "unit_test": tmc,
            #     "error_message": json.dumps(errors, ensure_ascii=False, indent=2),
            #     "method_name": tested_method_name,
            #     "class_name": class_name,
            #     "method_code": tested_method_code
            # })
            # 
            #print("wtf:",corrected_test_method)
            pure_methods = extract_pure_test_method(corrected_test_method)
            #pure_methods = extract_pure_test_method(corrected_test_method_unitest)
            #print(f"Generated Test Method for {tested_method_name} .")
            if pure_methods:
                for new_test_method_name, pure_method in pure_methods:
              
                    original_test_method = next(
                  (tm for tm in test_class_infos['test_methods'] if tm['test_method_name'] == test_method_name),
                  None
              )
                    if original_test_method:
                        test_class_infos['test_methods'].remove(original_test_method)
                        print(f"Delete the original test method '{test_method_name}'。")
              
              
                    test_class_infos['test_methods'].append({
                  "method_name": tested_method_name,
                  "test_method_name": new_test_method_name,
                  "pure_test_method": pure_method.strip()
              })
                    print(f"Fixed test method added: '{new_test_method_name}'。")
            else:
                print(f"Unable to Extract Pure Test Method for {tested_method_name}.")
        except Exception as e:
            print(f"LLM fix for '{test_method_name}' failed: {e}")
            continue

    # 重新组装更新后的测试类代码
    updated_test_methods_code = ""
    for test_method in test_class_infos['test_methods']:
        pure_test_method = test_method.get('pure_test_method', "")
        if pure_test_method:
            # 统一缩进
            indented_test_method = '\n'.join(['    ' + line for line in pure_test_method.split('\n')])
            updated_test_methods_code += f"{indented_test_method}\n\n"

    class_name = test_class_infos.get("class_name", "UnknownClass")
    test_class_name = test_class_infos.get("test_class_name", f"{class_name}Test")
    imports = extract_method(file_path)[0].get('imports', [])

    import_string = ""
    pos = "gentest."+ pkg + ".tests"
    for simgle_import in imports:
        import_string += "import "
        import_string += simgle_import
        import_string += ";\n"
    updated_code = f"""package {pos};
import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import java.lang.reflect.Method;
import java.lang.reflect.Field;
import java.lang.reflect.Constructor;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import {pkg}.*;
{import_string} 
public class {test_class_name} {{
    
    @Rule
    public ExpectedException thrown = ExpectedException.none();
{updated_test_methods_code}
}}
"""
    
    if class_level_errors:
        print("\nClass Level Errors Detected:")
        for error in class_level_errors:
            print(f"  Line {error['line']}: {error['message']}")
        print("Class Code (may include imports or field declarations):")
        
        lines = test_class_code.splitlines()

        
        for err in sorted(class_level_errors, key=lambda e: e['line'], reverse=True):
            idx = err['line'] - 1          
            if 0 <= idx < len(lines):
                del lines[idx]

        
        updated_code = '\n'.join(lines)
        
        # class_code_snippet = code_lines[:20]
        # class_error_lines = {error['line'] for error in class_level_errors if error['line'] <= 20}
        # formatted_class_code = format_code_with_line_numbers(class_code_snippet, 0, class_error_lines)
        #print(f"```java\n{formatted_class_code}\n```")
        #print("-" * 40)


    result,_ = update_test_class_info(updated_code,test_class_infos)
    if result == 0:
        return 0, updated_code, error_messages, test_class_infos
   
    updated_code,flag, compile_output = compile_and_run(updated_code, test_class_infos, test_dir, base_dir)

    if flag == 1:
        print("LLM Fix Succeeded. The test class now compiles successfully.")
        return 1, updated_code, compile_output, test_class_infos
    else:
        print("LLM Fix Failed to Resolve All Compilation Errors. Trying Other Fix Methods.")
        
        return llm_based_fix(
        updated_code,
        compile_output,
        test_class_infos,
        base_dir,
        test_dir,
        depth + 1,
        file_path,
        llm,
        pkg
    )
    

def delete_bad_code(test_class_code, error_messages, test_class_infos, base_dir, test_dir, depth=0):
    

    #  Maximum recursion depth to prevent infinite loops
    MAX_DEPTH = 10
    if depth >= MAX_DEPTH:
        print(f"Reached maximum recursion depth ({MAX_DEPTH}). Cannot fix further.")
        print(f"Reached {MAX_DEPTH}. No further Fix")
        return 0, test_class_code, error_messages, test_class_infos

    #  If error_messages is None, set it to an empty list
    if error_messages is None:
        print("No error messages provided. Nothing to fix.")
        print("未提供错误信息。无需修复。")
        return 0, test_class_code, error_messages, test_class_infos

    updated_code_lines = test_class_code.split('\n')
    modifications_made = False  # Flag to track if any modifications were made

    # Sort errors by line number in descending order to avoid shifting
    sorted_errors = sorted(error_messages, key=lambda x: x.get('line', 0), reverse=True)

    for error in sorted_errors:
        error_msg = error.get('message', '')
        line_num = error.get('line', 0)

        # Debug: Print the error being processed
        print(f"Processing error at line {line_num}: {error_msg}")
       

        #  If the line number is invalid, skip this error
        if line_num == 0:
            print(f"Invalid line number for error: {error_msg}")
            
            continue
            
        if 0 < line_num <= len(updated_code_lines):
            line_content = updated_code_lines[line_num - 1].strip()
            # Check if the line contains only an opening or closing brace
            if line_content in ('{', '}'):
                print(f"Skipping deletion of line {line_num} as it contains only a brace.")
                
                continue
            elif '{' in line_content or '}' in line_content:
                # If the line contains braces and other content, remove non-brace content
                print(f"Modifying line {line_num} to preserve braces while removing other content.")
                
               
                if("public void" not in line_content):
                    modified_line = re.sub(r'[^\{\}]+', '', updated_code_lines[line_num - 1])
                    updated_code_lines[line_num - 1] = modified_line
                    modifications_made = True
                else:
                    modifications_made = True
            else:
                #  If the line does not contain braces, delete the entire line
                print(f"Deleting line {line_num} due to error: {error_msg}")
                del updated_code_lines[line_num - 1]
                modifications_made = True
        else:
            print(f"Line number {line_num} out of range for error: {error_msg}")

    if not modifications_made:
        print("No modifications were made. Nothing to fix.")
        return 0, test_class_code, error_messages, test_class_infos

    #  Reassemble the updated code
    updated_code = '\n'.join(updated_code_lines)
    result,_ = update_test_class_info(updated_code,test_class_infos)
    if result == 0:
        return 0, updated_code, error_messages, test_class_infos

    #  Re-extract pure test methods from the updated code
    # updated_pure_methods = extract_pure_test_method(updated_code)

    # if not updated_pure_methods:
    #     print("No pure test methods could be extracted after deleting bad code.")
   
    #     return 0, updated_code, error_messages, test_class_infos

    # Update the 'pure_test_method' in 'test_class_infos'
    # if len(updated_pure_methods) != len(test_class_infos['test_methods']):
    #     print("Mismatch in the number of pure test methods after deleting bad code.")
   
    #     return 0, updated_code, error_messages, test_class_infos

    # for idx, test_method in enumerate(test_class_infos['test_methods']):
    #     test_method['pure_test_method'] = updated_pure_methods[idx].strip()
    #     print(f"Updated pure_test_method for method '{test_method['method_name']}'.")
    

    # Attempt to compile the updated code
    updated_code,flag, compile_output = compile_and_run(updated_code, test_class_infos, test_dir, base_dir)

    if flag == 1:
        print("Successfully fixed the test class by deleting bad code.")
        return 1, updated_code, compile_output, test_class_infos
    else:
        print("Failed to compile after deleting bad code. Attempting further fixes...")
        #  Extract new errors from the compilation output
        
        #  Recursively attempt to fix the new errors
        return delete_bad_code(updated_code, compile_output, test_class_infos, base_dir, test_dir, depth + 1)
def whole_process_TCIGen(file_path,base_dir,llm,Whole_class=1,Method_name_list = None):
    if Whole_class == 1:
        json_output = initial_gen_testcase_per_class(file_path, llm)
    else:
        json_output = initial_gen_testcase_by_methods(file_path, llm,Method_name_list)
    
    imports = extract_method(file_path)[0].get('imports', [])
    pkg = extract_method(file_path)[0].get('package', [])
    pos = pkg + ".tests"
  
    #print(json_output)
    test_positions=pos.split(".")
    test_dir=base_dir+"/gentest"
    for layer in test_positions:
        test_dir+=("/"+layer)
    test_class_info = json.loads(json_output)
    print("before",test_class_info,"before")
    test_class_info =ensure_unique_test_method_names(test_class_info)
    complete_test_class_code = assemble_test_class(test_class_info,imports,pkg)
    result,_ =update_test_class_info(complete_test_class_code,test_class_info)
    if result == 0:
        return 0, None


    #print(complete_test_class_code)
 
    complete_test_class_code,flag, errors = compile_and_run(complete_test_class_code, test_class_info, test_dir, base_dir)
    if flag == 1:
        print("Test class compiled successfully.")
        # Optional: Proceed with further actions like running tests
    else:
        print("Test class compilation failed. Attempting to fix errors...")
        # Attempt rule-based fixes
        total = 0
        while 1:
            total = total + 1
            if total == 3:
                break
            is_success, complete_test_class_code, errors, test_class_info = rule_based_fix(
               complete_test_class_code, errors, test_class_info, base_dir, test_dir, depth=0
         )
            if is_success == 1:
                print("Rule-based fixes succeeded. The test class now compiles successfully.")
                break
            else:
                print("Rule-based fixes did not resolve all compilation errors. Trying LLM-based fixes...")
            # Attempt LLM-based fixes
                is_llm_success, complete_test_class_code, errors, test_class_info = llm_based_fix(
                 complete_test_class_code, errors, test_class_info, base_dir, test_dir, 0,file_path,llm,pkg
                )

           
                if is_llm_success == 1:
                    print("LLM-based fixes succeeded. The test class now compiles successfully.")
                    break
                else:
                    print("LLM-based fixes did not resolve all compilation errors. Trying to delete bad code...")
                    is_success, complete_test_class_code, errors, test_class_info = rule_based_fix(
               complete_test_class_code, errors, test_class_info, base_dir, test_dir, depth=0
         )
                    if is_success == 1:
                        break
                # Attempt to delete bad code
                    is_success_final, complete_test_class_code, errors, test_class_info = delete_bad_code(
                    complete_test_class_code, errors, test_class_info, base_dir, test_dir, depth=9
                )
                    if is_success_final == 1:
                        print("By deleting bad code, the test class has been fixed and compiled successfully.")
                        break
                    else:
                        print("Failed to fix the test class after multiple attempts. Continuing to next iteration...")
                        is_success, complete_test_class_code, errors, test_class_info = rule_based_fix(
               complete_test_class_code, errors, test_class_info, base_dir, test_dir, depth=0
         )              
                        is_llm_success, complete_test_class_code, errors, test_class_info = llm_based_fix(
                 complete_test_class_code, errors, test_class_info, base_dir, test_dir, 0,file_path,llm,pkg
                ) 
                        
                        if is_success or is_llm_success:
                            print("By rule-based or LLM-based fixes, the test class has been fixed and compiled successfully.")
                            break
                        else:
                            is_success_final, complete_test_class_code, errors, test_class_info = delete_bad_code(
                    complete_test_class_code, errors, test_class_info, base_dir, test_dir, depth=0
                )           
                            if is_success_final == 1:
                                print("By deleting bad code, the test class has been fixed and compiled successfully.")
                                break
                            else:
                                continue
                            
    result,_ =update_test_class_info(complete_test_class_code,test_class_info)
    if result == 0:
        return 0, None
    # print("after",test_class_info,"after")
    # Optional: Further processing like archiving or running tests
    # Ensure that `complete_test_class_code` is successfully fixed before archiving
    test_class_path = os.path.join(test_dir, f"{test_class_info['test_class_name']}.java")
    if flag == 1 or is_success == 1 or is_llm_success == 1 or is_success_final == 1:
        archive_name = generate_tar_bz2(os.path.join(base_dir, "gentest"), base_dir)
        print(f"Test class archived: {archive_name}")
        return 1,test_class_path
    else:
        print("Test class compilation failed. No archive file generated.")
        return 0,test_class_path
if __name__ == "__main__":
    file_path = './defects4j_fixed/Compress/Chart_1_fixed/source/org/jfree/data/time/Day.java'
    base_dir = './defects4j_fixed/Compress/Chart_1_fixed'
    
    llm = GPT(api_key=os.getenv("OPENAI_API_KEY"), model="gpt-3.5-turbo")
    methods = ["previous"]
    whole_process_TCIGen(file_path,base_dir,llm,0,methods)
    
    





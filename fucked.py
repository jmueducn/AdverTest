import os
import re
import json
from langchain.prompts import ChatPromptTemplate
from model import GPT  # 确保你有一个 model.py 并定义了 GPT 类
from extract import extract_method  # 确保 extract.py 中有 extract_method 函数

def initial_gen_testcase_per_class(class_path, llm):
    """
    生成针对一个类的 JUnit 测试用例。提取类的所有方法、变量和构造函数信息，并生成测试提示。
    :param class_path: Java 类的文件路径。
    :param llm: 语言模型，通常是 GPT 实例。
    :return: 返回包含测试用例和相关信息的字典。
    """
    # 使用 extract_method 提取类的方法、构造函数和类变量
    extracted = extract_method(class_path)
    if not extracted:
        return {"error": "未能提取任何方法或类变量。"}

    # 提取类名
    class_name = "UnknownClass"
    with open(class_path, 'r', encoding='utf-8') as file:
        content = file.read()
        match = re.search(r'class\s+(\w+)', content)
        if match:
            class_name = match.group(1)

    # 提取类中的所有导入（imports）
    imports = []
    import_matches = re.findall(r'import\s+([a-zA-Z0-9._*]+);', content)
    if import_matches:
        imports = import_matches

    # 获取类的构造函数和类变量
    class_variables = []
    constructors = []
    for method in extracted:
        if method['method']['name'] == class_name:
            constructors.append(method)  # 构造函数
        else:
            method_info = method['method']
            class_variables.append({
                "modifiers": method_info.get('modifiers', 'private'),
                "type": method_info.get('return_type', 'UnknownType'),
                "name": method_info.get('name', 'unknown')
            })
    
    # 构建 JUnit 测试提示模板
    prompt_template = f'''
Instruction:

You are an expert Java developer and software tester. Your task is to generate JUnit test methods for each public method in the following Java class. Follow these steps to ensure comprehensive and effective test coverage:

1. **Analyze the Java Method**:
   - Identify the method's parameters and return type.
   - Understand the functionality and purpose of the method.

2. **Design Test Cases**:
   - For each method, design test cases that cover typical usage, edge cases, and potential error conditions.
   - Ensure that all possible execution paths are tested, including input validation and exception handling.

3. **Implement the Test Method**:
   - Write a test method annotated with @Test for each test case.
   - Use assertions to verify the expected outcomes.

4. **Analyze Class Information**:
   - The class is named **{class_name}**.
   - The following **imports** should be considered while writing test cases:
     {json.dumps(imports, indent=2)}

   5. **Class Variables**:
      The class contains the following variables:
      {json.dumps(class_variables, indent=2)}

   6. **Constructors**:
      The class contains the following constructor(s):
      {json.dumps(constructors, indent=2)}

Now, please proceed to generate the JUnit test methods for the class based on the information provided.'''

    return {"class_name": class_name, "prompt_template": prompt_template, "imports": imports, "class_variables": class_variables, "constructors": constructors}

# 使用示例
class_path = './defects4j_fixed/Chart/Chart_1_fixed/source/org/jfree/chart/renderer/category/AbstractCategoryItemRenderer.java'
llm = GPT(api_key=os.getenv("OPENAI_API_KEY"), model="gpt-3.5-turbo")
result = initial_gen_testcase_per_class(class_path, llm)

print(json.dumps(result, indent=2))

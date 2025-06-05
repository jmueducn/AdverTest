import os
import re
import json
import javalang

def get_type_name(type_node):
    """
    将 javalang 的类型节点转换为字符串表示。
    """
     # 如果 type_node 为 None，则返回一个默认类型字符串，防止后续报错
    if type_node is None:
        return "UnknownType"

    if isinstance(type_node, javalang.tree.BasicType):
        type_name = type_node.name
    elif isinstance(type_node, javalang.tree.ReferenceType):
        # 处理包名和类型名
        if isinstance(type_node.name, list):
            type_name = '.'.join(type_node.name)
        else:
            type_name = type_node.name

        # 处理泛型参数
        if type_node.arguments:
            args = ', '.join([get_type_name(arg.type) for arg in type_node.arguments if isinstance(arg, javalang.tree.TypeArgument)])
            type_name += f'<{args}>'
    else:
        type_name = 'UnknownType'

    # 处理数组维度，如果存在的话
    if hasattr(type_node, 'dimensions') and type_node.dimensions:
        type_name += '[]' * len(type_node.dimensions)
    return type_name
def get_max_line(node):
    """
    递归遍历 AST 节点及其所有子节点，返回节点中出现的最大行号。
    """
    # 如果当前节点有位置信息则使用，否则设为 0
    max_line = node.position.line if hasattr(node, 'position') and node.position else 0

    # 遍历当前节点的所有子节点
    for child in node.children:
        if isinstance(child, list):
            # 如果子节点为列表，则遍历其中每个元素
            for subchild in child:
                if isinstance(subchild, javalang.ast.Node):
                    max_line = max(max_line, get_max_line(subchild))
        elif isinstance(child, javalang.ast.Node):
            max_line = max(max_line, get_max_line(child))
    return max_line
def extract_method(java_file_path):
    # 检查文件是否存在
    if not os.path.isfile(java_file_path):
        print(f"文件不存在: {java_file_path}")
        return []

    with open(java_file_path, 'r', encoding='utf-8') as file:
        content = file.read()

    try:
        # 解析 Java 代码
        tree = javalang.parse.parse(content)
    except javalang.parser.JavaSyntaxError as e:
        print(f"解析错误: {e}")
        return []

    # 分割内容为行，便于根据行号提取方法体
    lines = content.split('\n')

    result = []

    # 提取所有import语句
    imports = [imp.path for imp in tree.imports]
    package = tree.package.name
    # 遍历所有类型声明（类、接口等）
    for path, node in tree.filter(javalang.tree.ClassDeclaration):
        class_node = node
        class_name = class_node.name

        # 提取类级变量（字段）
        class_variables = []
        for field in class_node.fields:
            # 每个字段可能有多个变量声明
            for declarator in field.declarators:
                var_name = declarator.name
                var_type = get_type_name(field.type)
                var_modifiers = ' '.join(field.modifiers) if field.modifiers else ''
                class_variables.append({
                    'name': var_name,
                    'type': var_type,
                    'modifiers': var_modifiers
                })

        # 提取所有构造函数
        constructors = []
        for constructor in class_node.constructors:
            constructor_info = {}
            constructor_info['name'] = constructor.name
            constructor_info['modifiers'] = ' '.join(constructor.modifiers) if constructor.modifiers else ''

            # 提取参数
            parameters = []
            for param in constructor.parameters:
                param_type = get_type_name(param.type)
                param_name = param.name
                parameters.append({
                    'type': param_type,
                    'name': param_name
                })
            constructor_info['parameters'] = parameters

            # 提取构造函数体
            if constructor.body:
                # 方法体的起始行（构造函数体的第一个语句）
                start_line = constructor.body[0].position.line - 1  # 行号从1开始，索引从0
                # 构造函数体的结束行，通过遍历构造函数体内的所有节点找到最大的行号
                max_line = get_max_line(constructor)
                constructor_body_lines = lines[start_line:max_line + 1]
                constructor_body = '\n'.join(constructor_body_lines).strip()
            else:
                constructor_body = ''

            constructor_info['body'] = constructor_body

            constructors.append(constructor_info)

        # 提取方法
        for method in class_node.methods:
            try:
                method_info = {}
                method_info['name'] = method.name
                try:
                    method_info['return_type'] = get_type_name(method.return_type) if method.return_type else 'void'
                    method_info['modifiers'] = ' '.join(method.modifiers) if method.modifiers else ''
                except Exception as e:
                    method_info['return_type'] = 'void'
                    method_info['modifiers'] = ''
            # 提取参数
                parameters = []
                for param in method.parameters:
                    param_type = get_type_name(param.type)
                    param_name = param.name
                    parameters.append({
                    'type': param_type,
                    'name': param_name
                })
                method_info['parameters'] = parameters
            # 提取方法体
                if method.body:
                # 方法体的起始行（方法体的第一个语句）
                    start_line = method.body[0].position.line - 1  # 行号从1开始，索引从0
                # 方法体的结束行，通过遍历方法体内的所有节点找到最大的行号
                    max_line =get_max_line(method)
                # 提取方法体的代码
                    method_body_lines = lines[start_line:max_line + 1]
                    method_body = '\n'.join(method_body_lines).strip()
                else:
                    method_body = ''

                method_info['body'] = method_body
           
            # 将方法信息、类变量、构造函数和import语句关联
                result.append({
                'method': method_info,
                'class_variables': class_variables,
                'constructors': constructors,
                'imports': imports,
                'package': package
            })
            except Exception as e:
                continue
    return result

if __name__ == "__main__":
    # 示例文件路径，请根据实际情况修改
    file_path = './defects4j_fixed/Chart/Chart_1_fixed/source/org/jfree/data/time/DateRange.java' 

    methods = extract_method(file_path)
    if not methods:
        print("未提取到任何方法。")
    else:
        # 假设所有方法的import语句和构造函数相同，从第一个方法提取
        first_method = methods[0]
        imports = first_method.get('imports', [])
        constructors = first_method.get('constructors', [])
        pkg = first_method.get('package',[])
        print("Package Name:",pkg)
        # 打印 import 语句
        print("Imports:")
        if imports:
            for imp in imports:
                print(f"  - import {imp};")
        else:
            print("  - No import statements found.")
        print("="*80)

        # 打印构造函数
        print("Constructors:")
        if constructors:
            for constructor in constructors:
                cons_modifiers = constructor['modifiers']
                cons_name = constructor['name']
                cons_parameters = constructor['parameters']
                cons_body = constructor['body']
                
                # 构建参数字符串
                params_str = ', '.join([f"{param['type']} {param['name']}" for param in cons_parameters])
                
                print(f"  - {cons_modifiers} {cons_name}({params_str}) {{")
                # 打印构造函数体，每行缩进四个空格
                for line in cons_body.split('\n'):
                    print(f"      {line.strip()}")
                print("  }")
        else:
            print("  - No constructors found.")
        print("="*80)

        # 遍历每个方法，打印方法信息和类变量
        for method in methods:
            method_info = method['method']
            print(f"Method Name: {method_info['name']}")
            print(f"Return Type: {method_info['return_type']}")
            print(f"Modifiers: {method_info['modifiers']}")
            print("Parameters:")
            for param in method_info['parameters']:
                print(f"  - {param['type']} {param['name']}")
            print("Method Body:\n", method_info['body'])
            print("Class-level variables:")
            for var in method['class_variables']:
                modifiers = var['modifiers']
                var_type = var['type']
                var_name = var['name']
                print(f"  - {modifiers} {var_type} {var_name}")
            print("="*80)
        #print("Package Name:",pkg)

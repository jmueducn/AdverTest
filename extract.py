import os
import re
import json
import javalang

def get_type_name(type_node):
    
    if type_node is None:
        return "UnknownType"

    if isinstance(type_node, javalang.tree.BasicType):
        type_name = type_node.name
    elif isinstance(type_node, javalang.tree.ReferenceType):
       
        if isinstance(type_node.name, list):
            type_name = '.'.join(type_node.name)
        else:
            type_name = type_node.name

     
        if type_node.arguments:
            args = ', '.join([get_type_name(arg.type) for arg in type_node.arguments if isinstance(arg, javalang.tree.TypeArgument)])
            type_name += f'<{args}>'
    else:
        type_name = 'UnknownType'

    
    if hasattr(type_node, 'dimensions') and type_node.dimensions:
        type_name += '[]' * len(type_node.dimensions)
    return type_name
def get_max_line(node):
    
    
    max_line = node.position.line if hasattr(node, 'position') and node.position else 0

    
    for child in node.children:
        if isinstance(child, list):
            
            for subchild in child:
                if isinstance(subchild, javalang.ast.Node):
                    max_line = max(max_line, get_max_line(subchild))
        elif isinstance(child, javalang.ast.Node):
            max_line = max(max_line, get_max_line(child))
    return max_line
def extract_method(java_file_path):
    
    if not os.path.isfile(java_file_path):
        print(f"File not found: {java_file_path}")
        return []

    with open(java_file_path, 'r', encoding='utf-8') as file:
        content = file.read()

    try:
        tree = javalang.parse.parse(content)
    except javalang.parser.JavaSyntaxError as e:
        print(f"Extraction error: {e}")
        return []

    lines = content.split('\n')

    result = []

    imports = [imp.path for imp in tree.imports]
    package = tree.package.name
    for path, node in tree.filter(javalang.tree.ClassDeclaration):
        class_node = node
        class_name = class_node.name

        class_variables = []
        for field in class_node.fields:
            for declarator in field.declarators:
                var_name = declarator.name
                var_type = get_type_name(field.type)
                var_modifiers = ' '.join(field.modifiers) if field.modifiers else ''
                class_variables.append({
                    'name': var_name,
                    'type': var_type,
                    'modifiers': var_modifiers
                })

        constructors = []
        for constructor in class_node.constructors:
            constructor_info = {}
            constructor_info['name'] = constructor.name
            constructor_info['modifiers'] = ' '.join(constructor.modifiers) if constructor.modifiers else ''

            parameters = []
            for param in constructor.parameters:
                param_type = get_type_name(param.type)
                param_name = param.name
                parameters.append({
                    'type': param_type,
                    'name': param_name
                })
            constructor_info['parameters'] = parameters

            if constructor.body:
                start_line = constructor.body[0].position.line - 1  
                max_line = get_max_line(constructor)
                constructor_body_lines = lines[start_line:max_line + 1]
                constructor_body = '\n'.join(constructor_body_lines).strip()
            else:
                constructor_body = ''

            constructor_info['body'] = constructor_body

            constructors.append(constructor_info)

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
                parameters = []
                for param in method.parameters:
                    param_type = get_type_name(param.type)
                    param_name = param.name
                    parameters.append({
                    'type': param_type,
                    'name': param_name
                })
                method_info['parameters'] = parameters
                if method.body:
                    start_line = method.body[0].position.line - 1  
                    max_line =get_max_line(method)
                    method_body_lines = lines[start_line:max_line + 1]
                    method_body = '\n'.join(method_body_lines).strip()
                else:
                    method_body = ''

                method_info['body'] = method_body
           
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
    file_path = './defects4j_fixed/Chart/Chart_1_fixed/source/org/jfree/data/time/DateRange.java' 

    methods = extract_method(file_path)
    if not methods:
        print("No methods found or extraction failed.")
    else:
        first_method = methods[0]
        imports = first_method.get('imports', [])
        constructors = first_method.get('constructors', [])
        pkg = first_method.get('package',[])
        print("Package Name:",pkg)
       
        print("Imports:")
        if imports:
            for imp in imports:
                print(f"  - import {imp};")
        else:
            print("  - No import statements found.")
        print("="*80)

        
        print("Constructors:")
        if constructors:
            for constructor in constructors:
                cons_modifiers = constructor['modifiers']
                cons_name = constructor['name']
                cons_parameters = constructor['parameters']
                cons_body = constructor['body']
                
                
                params_str = ', '.join([f"{param['type']} {param['name']}" for param in cons_parameters])
                
                print(f"  - {cons_modifiers} {cons_name}({params_str}) {{")
                
                for line in cons_body.split('\n'):
                    print(f"      {line.strip()}")
                print("  }")
        else:
            print("  - No constructors found.")
        print("="*80)

        print("Methods:")
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

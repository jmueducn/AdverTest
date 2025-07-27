import json
import re 
from langchain.prompts import ChatPromptTemplate

def group_mutants_by_line(mutants_data):
    """
    
    """
    groups = {}
    for mutant in mutants_data:
        line = mutant.get("line")
        if line is None:
            continue  # 
        groups.setdefault(line, []).append(mutant)
    return groups

def MT_ENHANCE(proj,projid,MUT_POS_RAW,MUT_POS_TESTED,Uncovered_Lines,llm,base_dir = "./defects4j_fixed"):
    mt_raw_file = MUT_POS_RAW + "/%s/%s-" % (proj,proj) +str(projid+1)+".json"
    id = 0
    mt_tested_file = MUT_POS_TESTED + "/%s/%s_" % (proj,proj) +str(projid+1)+"_test.json"
    with open(mt_tested_file, 'r') as file:
        mutants_data = json.load(file)
    tested_file = ""
    survived_mutants = []
    for mutant in mutants_data:
        id+=1
        mutant['id'] = id
        tested_file = mutant['filepath']
        if mutant['survived'] == True:
            survived_mutants.append(mutant)
    if mutants_data[0]['filepath']!= tested_file:
        print("not from same file")
    
    PUT = ""
    with open(tested_file, 'r') as file:
        PUT = file.read()
    survived_groups_by_line = group_mutants_by_line(survived_mutants)
    for group in survived_groups_by_line:
        program_lines = PUT.strip().splitlines()
        line = int(group)
        existing_mutants = survived_groups_by_line[group]
        existing_summary = ", ".join([mutant.get("aftercode", "") for mutant in existing_mutants])
        
        prompt = """
            %s
            Above is the original code line.We have Already generated mutants on this line: [%s]
            Your task is to generate %s more mutants in the original code that are different from the ones already generated.
            There are multiple ways you can modify the code, for example:
                {
                    "precode": "n = (n & (n - 1));",
                    "aftercode": "n = (n ^ (n - 1));"                                        
                },
                {
                    "precode": "  while (!queue.isEmpty()) {",
                    "aftercode": "while (true) {"
                },
                {
                    "precode": "return depth==0;",
                    "aftercode": "return true;"
                },
                {
                    "precode": "ArrayList r = new ArrayList(); r.add(first).addll(subset); to_add(r)",
                    "aftercode": "to_add.addAll(subset);"
                },
                {
                    "precode": "c = bin_op.apply(b,a);",
                    "aftercode": "c = bin_op.apply(a,b);"
                },
                {
                    "precode": "while (Math.abs(x-approx*approx) > epsilon) {",
                    "aftercode": "while (Math.abs(x-approx) > epsilon) {"
                }
            # Requirements:
            1. Provide generated mutants directly.
            2. A mutation can only occur on one line.
            3. Your output must be like:
               [
                   {
                       "id": <mutant_serial_number>,
                       "line": <line_number>,
                       "precode": "<original_code_line>",
                       "filepath": "<filepath>",
                       "aftercode": "<mutated_code_line>"
                   }
               ]
               where "id" is the mutant serial number, "line" is the mutated line number,
               "precode" is the original code (cannot be empty), and "aftercode" is the mutated code.
            4. Prohibit generating the exact same mutants.
            5. All output should be in a JSON file.
            """ % (program_lines[line-1], existing_summary, 2)
        
        num = 0
        for tem in range(3): 
            try:
                generated_text = llm._call(prompt)
                pattern = r'\[.*\]'
                generated_text = re.findall(pattern, generated_text, re.DOTALL)
                print("Generated text", generated_text)
                try:
                    mu = json.loads(generated_text[0])
                    for i in range(len(mu)):
                        id += 1
                        mu[i]['id'] = id
                        mu[i]['filepath'] = tested_file
                        if mu[i]['precode'] in program_lines[line-1]:
                            mu[i]['line'] = line
                        else:
                            print("something not correct, please check this later")
                            mu[i]['line'] = line
                    mutants_data.extend(mu)
                    break
                except Exception as e:
                    num += 1
                    print("JSON Content Error", e)
                    continue
            except Exception as e:
                print("LLMGEN Error", e)
                num += 1
                continue
    for Line in Uncovered_Lines:
        program_lines = PUT.strip().splitlines()
        # start_line = Line+1
        # end_line = Line-1
        # while('*/' not in program_lines[start_line-1]):
        #     start_line=start_line-1
        #     if start_line<1:
        #         break
        # while('/**' not in program_lines[end_line-1]):
        #     end_line=end_line+1
        #     if end_line>=len(program_lines):
        #         break
        # TRY AND SEE WHETHER FULL CODE IS NEEDED.
        prompt="""
            %s\n
            Above is the original code line. your task is to generate %s mutants in original code(notice:mutant refers to mutant in software engineering, i.e. making subtle alterations to the original code)
            There are multiple ways you can do to modify the code,
            as follows are some examples of mutants which you can refer to:
                {
                "precode": "n = (n & (n - 1));",
                "aftercode": " n = (n ^ (n - 1));"                                        
                },
                {
                "precode": "  while (!queue.isEmpty()) {",
                "aftercode": " while (true) { "             
                },                                        
                {
                "precode": "return depth==0;",
                "aftercode": "return true;"
                },                                        
                {
                "precode": "ArrayList r = new 
                ArrayList();r.add(first).addll(subset);to_add(r)",
                "aftercode": "to_add.addAll(subset);"
                },                               
                {
                "precode": "c = bin_op.apply(b,a);",
                "aftercode": "c = bin_op.apply(a,b);",    
                },                              
                {
                "precode":"while (Math.abs(x-approx*approx) > epsilon) { "     
                "aftercode": " while (Math.abs(x-approx) > epsilon) {"
                },                          
            #Requirement:
            1.Provide generated mutants directly
            2.A mutation can only occur on one line
            3.Your output must be like:
            [
                {
                    "id":,
                    "line":,
                    "precode":"",
                    "filepath":"kk",
                    "aftercode":""
                }
            ]
            Where "id" stand for mutant serlal number,"Line" represent the line number of the mutated,"precode" represent the line of code before mutation and it can't be empty,"aftercode" represent the line of code after mutation
            4.Prohibit generating the exact same mutants
            5.all write in a json file
    
            """%(program_lines[Line-1],2)
        num=0
        for tem in range(3): 
            try:
                generated_text = llm._call(prompt)
                pattern = r'\[.*\]'
                generated_text = re.findall(pattern,generated_text,re.DOTALL)
                print("Generated text",generated_text)
                try:
                    mu=json.loads(generated_text[0])
                    for i in range(len(mu)):
                        id+=1
                        mu[i]['id']=id
                        mu[i]['filepath']=tested_file
                        if mu[i]['precode'] in program_lines[Line-1]:
                            mu[i]['line']=Line
                            continue
                        else:
                            print("something not correct,please check this later")
                            mu[i]['line']=Line
                            continue
                    mutants_data.extend(mu)
                    break
                except Exception as e:
                    num+=1
                    print("JSON Content Error",e)
                    continue
            except Exception as e:
                print("LLMGEN Error",e)
                num+=1
                continue
    with open(mt_raw_file, 'w', encoding='utf-8') as file:
        json.dump(mutants_data, file, ensure_ascii=False,indent=4)            
    return 0
import xml.etree.ElementTree as ET

def get_uncovered_lines(xml_file):
    
    tree = ET.parse(xml_file)
    root = tree.getroot()
    
    uncovered_lines = []
    
    
    for cls in root.findall('.//class'):
        lines_section = cls.find('lines')
        if lines_section is not None:
            for line in lines_section.findall('line'):
                hits = line.get('hits')
                if hits == '0':
                    line_number = int(line.get('number'))
                    uncovered_lines.append(line_number)
    
   
    uncovered_lines = sorted(list(set(uncovered_lines)))
    return uncovered_lines
def get_real_uncovered_lines(uncovered_lines,mutated_lines):
    return list(set(uncovered_lines) & set(mutated_lines))
def get_real_coverage_rate(uncovered_lines,mutated_lines):
    total_lines = len(mutated_lines)  
    uncovered_count = len(uncovered_lines)  

   
    covered_count = total_lines - uncovered_count

  
    coverage_rate = (covered_count / total_lines) * 100

    return coverage_rate

def coverage_process(project,project_id,mutated_lines,d4jbug_path = './defects4j_fixed'):
    coverage_dir = d4jbug_path + "/%s/%s_" % (project,project) +str(project_id+1)+"_fixed/coverage.xml"
    uncovered = get_uncovered_lines(coverage_dir)
    uncovered = get_real_uncovered_lines(uncovered,mutated_lines)
    coverage = get_real_coverage_rate(uncovered,mutated_lines)
    return uncovered,coverage
if __name__ == "__main__":
    uncovered,coverage = coverage_process('Chart',2,[1214,1215,1216,1217,1218,1219])
    print("Not coverated List:", uncovered)
    print("cvg rate:",coverage,"%")
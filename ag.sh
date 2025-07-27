# python aggregate_coverage.py Evosuite Chart
# python aggregate_coverage.py Evosuite Cli
# python aggregate_coverage.py Evosuite Csv
# python aggregate_coverage.py Evosuite JxPath
# python aggregate_coverage.py Evosuite Gson
# python aggregate_coverage.py Evosuite Math
# python aggregate_coverage.py Evosuite Compress
# python aggregate_coverage.py ourmethod Chart
# python aggregate_coverage.py ourmethod Cli
# python aggregate_coverage.py ourmethod Csv
# python aggregate_coverage.py ourmethod JxPath
# python aggregate_coverage.py ourmethod Gson
# python aggregate_coverage.py ourmethod Math
# python aggregate_coverage.py ourmethod Compress
# python aggregate_coverage.py ChatUniTest Chart
# python aggregate_coverage.py ChatUniTest Cli
# python aggregate_coverage.py ChatUniTest Csv
# python aggregate_coverage.py ChatUniTest JxPath
# python aggregate_coverage.py ChatUniTest Gson
# # python aggregate_coverage.py ChatUniTest Math
# # python aggregate_coverage.py ChatUniTest Compress
# python aggregate_coverage.py randoop Chart
# python aggregate_coverage.py randoop Cli
# python aggregate_coverage.py randoop Csv
# python aggregate_coverage.py randoop JxPath
# python aggregate_coverage.py randoop Gson
for i in $(seq 1 40); do
    python analyse_coverage.py gemini Cli "$i"
    
    done
python aggregate_coverage.py gemini Cli
# python aggregate_coverage.py randoop Compress

# done
# for i in $(seq 1 26); do
#     python analyse_coverage.py randoop Chart "$i"
    
#     done

# for i in $(seq 1 40); do
#     python analyse_coverage.py randoop Cli "$i"
    
#     done

# for i in $(seq 1 47); do
#     python analyse_coverage.py randoop Compress "$i"
    
#     done

# for i in $(seq 1 16); do
#     python analyse_coverage.py randoop Csv "$i"
    
#     done

# for i in $(seq 1 18); do
#     python analyse_coverage.py randoop Gson "$i"
    
#     done

# for i in $(seq 1 22); do
#     python analyse_coverage.py randoop JxPath "$i"
    
#     done
# done
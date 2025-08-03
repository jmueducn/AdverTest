from langchain.llms.base import LLM
from typing import Any, List, Optional
from langchain.callbacks.manager import CallbackManagerForLLMRun
from transformers import AutoTokenizer, AutoModelForCausalLM, GenerationConfig
from transformers import pipeline
import torch
import os
from openai import OpenAI
import httpx

class GPT(LLM):
    
    api_key: str = ''
    mymodel: str = ''
    def __init__(self, api_key: str,model):
        super().__init__()
        self.api_key = api_key
        self.mymodel = model
    def _call(self, prompt: str,stop: Optional[List[str]] = None,
              run_manager: Optional[CallbackManagerForLLMRun] = True,
              **kwargs: Any) -> str:
        client = OpenAI(
            api_key="yourapikey",
            base_url="https://api.openai.com"
        )
        response = client.chat.completions.create(
            model = self.mymodel,  
            messages=[
                {"role": "system", "content": "You are a helpful assistant."},
                {"role": "user", "content": prompt}
            ],
            temperature=0.3
        )
        return response.choices[0].message.content
    @property
    def _llm_type(self) -> str:
        return "gpt"  

class Deepseek(LLM):
    
    api_key: str = ''
    mymodel: str = ''
    def __init__(self, api_key: str,model):
        super().__init__()
        self.api_key = api_key
        self.mymodel = model
    def _call(self, prompt: str,stop: Optional[List[str]] = None,
              run_manager: Optional[CallbackManagerForLLMRun] = True,
              **kwargs: Any) -> str:
        client = OpenAI(
        
            api_key="your api",
            base_url="https://api.deepseek.com"
        )
        response = client.chat.completions.create(
            model = self.mymodel,  
            messages=[
                {"role": "system", "content": "You are a talented Java programmer and experienced in software testing. Your ability of writing unit tests is excellent. And you have the knowledge on Everything about Java & JUnit. Also, you have the ability of critical thinking and logical reasoning. That will help you to write bugless code and help debugging."},
                {"role": "user", "content": prompt}
            ],
#             messages=[
#                 {"role": "system", "content": """
# Please help me generate a whole JUnit test for a focal method in a focal class.
# I will provide the following information of the focal method:
# 1. The focal class signature.
# 2. Source code of the focal method.
# 3. Signatures of other methods and fields in the class.
# I need you to create a whole unit test using JUnit 4 and Mockito 3, ensuring optimal branch and line coverage. The whole test should include necessary imports for JUnit 4 and Mockito 3, compile without errors, and use reflection to invoke private methods. No additional explanations required."""},
                 
#                 {"role": "user", "content": prompt}
#             ],
            temperature=0.0
        )
        return response.choices[0].message.content
    @property
    def _llm_type(self) -> str:
        return "deepseek"  

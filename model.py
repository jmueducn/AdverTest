from langchain.llms.base import LLM
from typing import Any, List, Optional
from langchain.callbacks.manager import CallbackManagerForLLMRun
import os
from openai import OpenAI


class LLMWrapper(LLM):
    """Generic wrapper for any OpenAI-compatible LLM API.

    Usage:
        llm = LLMWrapper(
            api_key=os.getenv("LLM_API_KEY"),
            model="deepseek-chat",
            base_url="https://api.deepseek.com",
        )
    """

    api_key: str = ''
    mymodel: str = ''
    base_url: str = 'https://api.openai.com/v1'
    system_prompt: str = (
        "You are a talented Java programmer and experienced in software testing. "
        "Your ability of writing unit tests is excellent. And you have the knowledge "
        "on Everything about Java & JUnit. Also, you have the ability of critical "
        "thinking and logical reasoning. That will help you to write bugless code "
        "and help debugging."
    )
    temperature: float = 0.0

    def __init__(self, api_key: str, model: str, base_url: str = 'https://api.openai.com/v1',
                 system_prompt: str = None, temperature: float = 0.0):
        super().__init__()
        self.api_key = api_key
        self.mymodel = model
        self.base_url = base_url
        if system_prompt is not None:
            self.system_prompt = system_prompt
        self.temperature = temperature

    def _call(self, prompt: str, stop: Optional[List[str]] = None,
              run_manager: Optional[CallbackManagerForLLMRun] = True,
              **kwargs: Any) -> str:
        client = OpenAI(
            api_key=self.api_key,
            base_url=self.base_url
        )
        response = client.chat.completions.create(
            model=self.mymodel,
            messages=[
                {"role": "system", "content": self.system_prompt},
                {"role": "user", "content": prompt}
            ],
            temperature=self.temperature
        )
        return response.choices[0].message.content

    @property
    def _llm_type(self) -> str:
        return "llm_wrapper"


# Convenience aliases for backward compatibility
def Deepseek(api_key: str, model: str = "deepseek-chat"):
    return LLMWrapper(api_key=api_key, model=model, base_url="https://api.deepseek.com")


def GPT(api_key: str, model: str = "gpt-4"):
    return LLMWrapper(api_key=api_key, model=model, base_url="https://api.openai.com/v1")

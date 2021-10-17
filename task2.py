#!/usr/bin/env python3
import re
from main import AppTest

class Test1(AppTest):
    def getTest():
        return """Петров П.П. P000
Анищенко А.А. P33113
Примеров Е.В. P3130
Иванов И.И. P3130"""
    def getAnswer():
        return """Петров П.П. P000
Анищенко А.А. P33113
Примеров Е.В. P3130"""

class Test2(AppTest):
    def getTest():
        return """Петров П.П. P000
Анищенко А.А. P33113
Примеров Е.В. P3132
Иванов И.И. P3132"""
    def getAnswer():
        return """Петров П.П. P000
Анищенко А.А. P33113
Примеров Е.В. P3132
Иванов И.И. P3132"""

class Test3(AppTest):
    def getTest():
        return """Петров П.П. P000
Анищенко А.А. P33113
Примеров Е.В. P3130
Иванов И.И. P3131"""
    def getAnswer():
        return """Петров П.П. P000
Анищенко А.А. P33113
Примеров Е.В. P3130
Иванов И.И. P3131"""

class Test4(AppTest):
    def getTest():
        return """Петров П.П. P3130
Анищенко А.А. P3130
Примеров Е.В. P3130
Иванов И.И. P3130"""
    def getAnswer():
        return """Примеров Е.В. P3130"""

class Test5(AppTest):
    def getTest():
        return """Анищенко А.А. P33113"""
    def getAnswer():
        return "Анищенко А.А. P33113"

class App(object):
    def __init__(self):
        pass
    def ans(self, inp):
        if isinstance(inp, AppTest):
            dr = inp.__class__
            nowt = str(dr.getTest())
        else:
            nowt = str(inp)
        arr = nowt.split('\n')
        ans = ""
        for i in range(len(arr)):
            ls = re.findall("[А-Я]", arr[i])
            if len(set(ls)) != 1 or re.search("P3130$", arr[i]) == None:
                if ans != "":
                    ans += '\n'
                ans += arr[i]
        if isinstance(inp, AppTest):
            print(inp.__class__.__name__, "-> ", end="")
            if ans == dr.getAnswer():
                print("succeeded")
            else:
                print("failed")
        return ans
if __name__ == "__main__":
    df = App()
    df.ans(Test1())
    df.ans(Test2())
    df.ans(Test3())
    df.ans(Test4())
    df.ans(Test5())
    
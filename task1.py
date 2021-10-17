#!/usr/bin/env python3
import re
from main import AppTest

class Test1(AppTest):
    def getTest():
        return "Кривошеее существо гуляет по парку"
    def getAnswer():
        return ["гуляет"]
class Test2(AppTest):
    def getTest():
        return "Кривошеее сщество гуляет по парку"
    def getAnswer():
        return ["Кривошеее", "гуляет"]
class Test3(AppTest):
    def getTest():
        return "Я сделал вид что я не вижу"
    def getAnswer():
        return []
class Test4(AppTest):
    def getTest():
        return "Тоо как ты рядом ходишь будто ходишь по Парижу,"
    def getAnswer():
        return ["Тоо"]
class Test5(AppTest):
    def getTest():
        return "Мне так впервые сносит крышу!"
    def getAnswer():
        return ["впервые"]

class App(object):
    def __init__(self):
        pass
    def ans(self, inp):
        if isinstance(inp, AppTest):
            dr = inp.__class__
            nowt = dr.getTest()
        else:
            nowt = inp
        arr = re.findall('\w+', nowt)
        ans = []
        for i in range(len(arr) - 1):
            if re.match(r'\w*[аеёиоуэюыя]{2}\w*', arr[i]) and len(re.findall(r'[аеёиоуэюыя]{1}', arr[i + 1])) < 3:
                ans.append(arr[i])
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
    
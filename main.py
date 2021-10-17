#!/usr/bin/env python3
import re
class AppTest(object):
    def __init__(self):
        pass
    def getTest():
        pass
    def getAnswer():
        pass

class Test1(AppTest):
    def getTest():
        return "LolKekCheburek"
    def getAnswer():
        return 0
class Test2(AppTest):
    def getTest():
        return "=<{|"
    def getAnswer():
        return 1
class Test3(AppTest):
    def getTest():
        return "=<{|=<{|=<{|;-'=<{|"
    def getAnswer():
        return 4
class Test4(AppTest):
    def getTest():
        return "=<{|}>=<{|"
    def getAnswer():
        return 2
class Test5(AppTest):
    def getTest():
        return "=<{|X<P=<{|8-(=<{|"
    def getAnswer():
        return 3


class App(object):
    eyes = [":", ";", "X", "8", "="]
    noses = ["-", "<", "-{", "<{"]
    mouths = ["\(", "\)", "O", "\|", "\\", "\/", "P"]
    smile = ""
    pattern = re.compile('')
    
    
    def __init__(self, isu):
        self.smile += self.eyes[isu % 5]
        self.smile += self.noses[isu % 4]
        self.smile += self.mouths[isu % 7]
        self.pattern = re.compile(self.smile)
    def ans(self, inp):
        if isinstance(inp, AppTest):
            dr = inp.__class__
            nowt = dr.getTest()
        else:
            nowt = inp
        ans = len(self.pattern.findall(nowt))
        if isinstance(inp, AppTest):
            nowa = dr.getAnswer()
            print(inp.__class__.__name__, "-> ", end="")
            if ans == nowa:
                print("succeeded")
            else:
                print("failed")
            
        return ans
if __name__ == "__main__":
    sd = App(338999)
    #print(sd.ans("=<{|=<{|>sd{=<{|}=<{|"))
    sd.ans(Test1())
    sd.ans(Test2())
    sd.ans(Test3())
    sd.ans(Test4())
    sd.ans(Test5())
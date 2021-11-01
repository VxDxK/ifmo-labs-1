#!/usr/bin/env python3
DEBUG_MODE = False

stack = []
filename = "extended"
filename = "schedule"
json_file = open(filename + ".json")
xml_file = open(filename + ".xml", "w")

def getSpaces():
    return ((len(stack) -1) * 4) * " "

def writeImpl(a):
    if DEBUG_MODE:
        print(a)
    else:
        xml_file.write(a + '\n')
#writeImpl("<?xml version="1.0"?>")
for line in json_file:
    now = line.strip()
    splited_now = now.split(":", 1)
    for i in range(len(splited_now)):
        splited_now[i] = splited_now[i].replace("\"", "")
    if(len(now)) <= 2:
        if(now[0] == "}" or now[0] == "]"):
            poped = stack.pop()
            if len(stack) != 0:
                writeImpl(getSpaces() + "</" + poped + ">")
        elif(now[0] == "{"):
            if len(stack) != 0:
                writeImpl(getSpaces() + "<element>")
            stack.append('element')
        continue
    if(splited_now[1] == "{" or splited_now[1] == "["):
        writeImpl(getSpaces() + "<" + splited_now[0] + ">")
        stack.append(splited_now[0])
    else:
        if splited_now[1][-1] == ",":
            to_print = splited_now[1][:-1]
        else :
            to_print = splited_now[1]
        writeImpl(getSpaces() + "<" + splited_now[0] + ">" + to_print + "</" + splited_now[0] + ">")

if DEBUG_MODE:
    print()
if len(stack) == 0:
    
    exit("Наверное нормально распарсило")
exit("Скорей всего где-то была ошибка")
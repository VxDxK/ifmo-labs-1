#!/usr/bin/env python3
from json2xml import json2xml
from json2xml.utils import readfromurl, readfromstring, readfromjson

DEBUG_MODE = False
filename = "extended"
filename = "schedule"
xml_file = open(filename + "_with_libs.xml", "w")

def writeImpl(a):
    if DEBUG_MODE:
        print(a)
    else:
        xml_file.write(a + '\n')
data =  readfromjson(filename + ".json")
writeImpl(json2xml.Json2xml(data).to_xml())
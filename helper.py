#!/usr/bin/env python3
def calc(s):
    i = int(s, 16)
    if i > (2**15)-1:
        i = -(2**16-i)
    return i
while True: print(calc(input("HEX: ")))

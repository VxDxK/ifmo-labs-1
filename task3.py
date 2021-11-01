#!/usr/bin/env python3
import time
import os
times = []
now_t = time.time()
for i in range(10):
    os.system("./main.py")
times.append(time.time() - now_t)

now_t = time.time()
for i in range(10):
    os.system("./task1.py")
times.append(time.time() - now_t)

now_t = time.time()
for i in range(10):
    os.system("./task2.py")
times.append(time.time() - now_t)
print("\nОсновная Библиотека Регулярки")
print(times)
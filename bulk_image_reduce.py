#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Wed Jul 17 23:23:41 2019

@author: meet18091998
"""
import reduce_image_size
import os

item = "bed"
basename = "images/"+item+"/"


files = os.listdir(basename)

def renameFiles():
    for i in range(len(files)):
        formatN = files[i].split(".").pop()
        if i+1 > 10:
            os.rename(basename + files[i], basename + item + "_00" + str(i+1)+"."+formatN)
        else:
            os.rename(basename + files[i], basename + item + "_000" + str(i+1)+"."+formatN)
            

            
renameFiles()    
files = os.listdir(basename)
for i in files:
    reduce_image_size.reduceSize(basename + i, 320, 240)
    
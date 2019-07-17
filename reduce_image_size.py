#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Wed Jul 17 22:48:42 2019

@author: meet18091998
"""

from PIL import Image

def reduceSize(pathToImage):
    originalImage = Image.open(pathToImage)
    w, h = originalImage.size
    print(w,h)
    if w > 500 or h > 500:
        if w > h :
            reducePerc = (500/w)
            newHeight = int(reducePerc*h)
            print(500, newHeight)
            newImage = originalImage.resize((500, newHeight), Image.ANTIALIAS)
            newImage.save("resizedImage.jpg")
        else:
            reducePerc = (500/h)
            newWidth = int(reducePerc*w)
            print(newWidth, 500)
            newImage = originalImage.resize((newWidth, 500), Image.ANTIALIAS)
            newImage.save("resizedImage.jpg")
    else:
        print("No need to reduce")
    
    
reduceSize("img.jpg")
    
    
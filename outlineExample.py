#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Thu Jul 18 23:39:11 2019

@author: meet18091998
"""

from PIL import Image, ImageFilter, ImageEnhance
import os

def outlineImage(path):
    source = Image.open(path)
    sharpener = ImageEnhance.Sharpness(source.convert('RGB')).image
    newImage = sharpener.filter(ImageFilter.FIND_EDGES)
    newImage.save(path)

item = 'door'
basepath = 'outline/'+item+'/'
files = os.listdir(basepath)

for i in files:
    outlineImage(basepath + i)
    
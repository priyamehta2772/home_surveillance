#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Wed Jul 17 22:48:42 2019

@author: meet18091998
"""

from PIL import Image

def reduceSize(pathToImage, w, h):
    originalImage = Image.open(pathToImage)
    newImage = originalImage.resize((w, h), Image.ANTIALIAS)
    newImage.save(pathToImage)    
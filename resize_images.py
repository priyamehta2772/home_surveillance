# -*- coding: utf-8 -*-
"""
Created on Wed Jul 17 23:16:11 2019

@author: priya
"""

import os
import glob
import cv2

def insert (source_str, insert_str, pos):
    return source_str[:pos]+insert_str+source_str[pos:]

def resize_images(source_path, height, width):
    dimension = (width, height)
    for f in glob.glob(os.path.join(source_path, "*.png")):
        print("Processing file: {}".format(f))
        img = cv2.imread(f,-1)
        resized_img = cv2.resize(img, dimension)
        cv2.imwrite(insert(f, "re", 21), resized_img)
        

room_folder_path = ".\\images\\train\\clean"
resize_images(room_folder_path, 150, 150)
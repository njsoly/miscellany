#!/bin/bash

for char in {a..z}; 
do [ -e /cygdrive/$char ] && [ ! -e /$char ] && ln -s /cygdrive/$char /$char && echo "made shortcut \"/$char\" to \"/cygdrive/$char\"."; 
done
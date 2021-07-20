#!/bin/bash
TWL=../twl06.txt
cat $TWL | grep -e "[OYARQ]\{0,3\}C[OYARQ]" -w

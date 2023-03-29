#!/usr/bin/env bash

#############################################################################
##  This is intended for use with Cygwin.
## 
##  - checks all the possible Windows drives (A: through Z:),
##  checks whether they exist (are mounted in /cygdrive/ ),
##  and makes a symbolic link to that same letter on root."
#############################################################################

for char in {a..z}; do 
	[ -e /cygdrive/$char ] && [ ! -e /$char ] && \
	ln -s /cygdrive/$char /$char && \
	echo "made shortcut \"/$char\" to \"/cygdrive/$char\"." || \
	echo "didn't make shortcut for /cygdrive/$char."
done

tsc --version > /dev/null 2>&1
[ "0" == "$?" ] && echo "typescript is installed" || echo "typescript is 
NOT installed."

tsc --version 2>&1 > /dev/null
[ -z "$?" ] && echo "typescript is installed" || echo "typescript is NOT installed."

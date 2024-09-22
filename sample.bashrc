#!/bin/bash

echo 'running C:\Users\njsoly\.bashrc'
pushd ~

. ~/miscellany/.bashrc

export NVM_DIR="$HOME/.nvm"
[ -s "$NVM_DIR/nvm.sh" ] && \. "$NVM_DIR/nvm.sh" # this loads nvm
[ -s "$NVM_DIR/bash_completion" ] && \. "$NVM_DIR/bash_completion" # this loads nvm bash_completion

if [[ "$OSTYPE" == "cygwin" ]]; then
	export dropbox=/cygdrive/c/Users/${USERNAME}/Dropbox
elif [[ "$OSTYPE" == "linux-gnu" ]] && [[ -d "/mnt/c/Users/${USER}" ]]; then
	export dropbox=/mnt/c/Users/${USERNAME}/Dropbox
fi

pushd > /dev/null

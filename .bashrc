#!/bin/bash

## set the shell ##
#export PS1="\e[32m\e[40m[\t]\e\] \e[0m\e[36m \w \e[35m\n$\[\] \e[0m"
#export PS1='\e[32;40m[\t]\e[m \e[0;36m\w\e[m \e[0;35m$\e[m '

##  exports  ##
export global_ignores="~/.gitignore_global"

[[ -e ./kotlin ]] && export kotlin=${PWD}/kotlin

## aliases to built-ins ##
alias lS='ls -s --block-size=KiB -A --color'
alias lskb='lS'
alias ls='ls -A -F --color'
alias dirs='dirs -v'
alias dirsn='dirs -v'

## git aliases ##
alias gs='git status'
alias gau='git add -u'
alias bra='git branch -a'
alias brch='git branch'
alias remtv='git remote -v'
alias gsuno='git status -uno'
alias gsnu='git status -uno'
alias gco='git checkout'
alias gstat='git diff --stat'
alias gd='git diff'


##############  application shorthands  #############
#-------              Windows                -------#
if [[ "$OS" = "Windows_NT" ]]; then
	alias sonar-scanner='sonar-scanner.bat'
	alias npp='notepad++'
	alias notepadpp='notepad++'
	alias cygwin_setup='start cmd /c  $(cygpath -w "/cygdrive/c/Users/A1434206/Downloads/setup-x86_64.exe")'
fi

#-------               Linux                 -------#

#-------               MacOS                 -------#

############  end application shorthands  ###########


clear


## bash git prompt setup ##
if [[ -f "/usr/local/opt/bash-git-prompt/share/gitprompt.sh" ]]; then
	__GIT_PROMPT_DIR="/usr/local/opt/bash-git-prompt/share"
	source "/usr/local/opt/bash-git-prompt/share/gitprompt.sh"
fi

## set $miscellany ##
if [[ -z "$miscellany" && "$OSTYPE" = "darwin18" ]]; then
	miscellany=~/miscellany
elif [[ -z "$miscellany" && "$HOSTNAME" = "njsoly-hp" ]]; then
	miscellany=/cygdrive/d/miscellany
fi

export miscellany
##---------------##

########_  functions  _########
if [[ -d ${miscellany}/bash.fxns.d ]]; then
	for f in ${miscellany}/bash.fxns.d/*;
	do {
		[[ -x $(realpath ${f}) ]] && echo "sourcing $f" && . $(realpath ${f}) || :
	}
	done
elif [[ -d $PWD/bash.fxns.d ]]; then
	source $PWD/bash.fxns.d/*
fi


if [[ "$HOSTNAME" = "njsoly-hp" ]] || [[ "$HOSTNAME" = "k55n-w7" ]]; then
	printf "hostname is %s; setting TERM to cygwin." "$HOSTNAME"
	export TERM=cygwin
fi

export PS1='\[\e[36m\][\t]\[\e[0m\] \[\e[32m\][$(branchname||"")]\[\e[0m\] \[\e[33m\]\W\[\e[0m\] $ '


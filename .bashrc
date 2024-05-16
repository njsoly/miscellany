#!/bin/bash

## set the shell ##
export PS1_DRAFT0="\e[32m\e[40m[\t]\e\] \e[0m\e[36m \w \e[35m\n$\[\] \e[0m"
export PS1_DRAFT1='\e[32;40m[\t]\e[m \e[0;36m\w\e[m \e[0;35m$\e[m '

##  exports  ##
export global_ignores="~/.gitignore_global"
export _GRAY_BLOCK=░
export _GRAY_BLOCK_8=░░░░░░░░

[[ -e ./kotlin ]] && export kotlin=${PWD}/kotlin

## aliases to built-ins ##
alias lS='ls -s --block-size=KiB -A --color'
alias lskb='lS'
alias ls='ls -A -F --color'
alias dirs='dirs -v'
alias dirsn='dirs -v'
alias ll='ls -l'

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

## TODO maybe delete
## this may have been for ... CTRL+Direction shortcuts?
## For cygwin?
[[ -x ./.inputrc ]] && bind -f ./.inputrc

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


## set $miscellany ##
# MAC
if [[ -z "$miscellany" && "$OSTYPE" = "darwin18" ]]; then
	miscellany=~/miscellany
# STOUT HP
elif [[ -z "$miscellany" && "$HOSTNAME" = "njsoly-hp" ]]; then
	miscellany=/cygdrive/d/miscellany
# ASUS A-15
elif [[ -z "$miscellany" && "$HOSTNAME" = "njsoly-a15" ]]; then
	if [[ "$OSTYPE" = "cygwin" ]]; then
		miscellany=$HOME/miscellany
	elif [[ "$WSL_DISTRO_NAME" = "Ubuntu" ]]; then
		miscellany=$HOME/miscellany
	elif [[ "$OSTYPE" = "linux-gnu" ]]; then
		miscellany=/mnt/c/Users/njsoly/miscellany
	fi
# ASUS A-15 2023
elif [[ -z "$miscellany" && "$HOSTNAME" = "njsoly-a15-1" ]]; then
	if [[ "$OSTYPE" = "cygwin" ]]; then
		miscellany=$HOME/miscellany
	elif [[ "$WSL_DISTRO_NAME" = "Ubuntu" ]]; then
		miscellany=$HOME/miscellany
	elif [[ -n "$WSL_DISTRO_NAME" ]]; then
		echo "ERROR: check out $BASH_LINENO in $BASH_SOURCE."
	elif [[ "$OSTYPE" = "linux-gnu" ]]; then
		miscellany=/mnt/c/Users/njsoly/miscellany
	fi
# TRY A DEFAULT
elif [[ -z "$miscellany" && -d $HOME/miscellany ]]; then
	miscellany=$HOME/miscellany
fi


export miscellany
##---------------##

## bash git prompt setup ##
if [[ "$OSTYPE" = "cygwin" ]]; then
	source "$miscellany/prompt_color_function.bash.source"
elif [[ -f "/usr/local/opt/bash-git-prompt/share/gitprompt.sh" ]]; then
	__GIT_PROMPT_DIR="/usr/local/opt/bash-git-prompt/share"
	source "/usr/local/opt/bash-git-prompt/share/gitprompt.sh"
fi

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


if [[ "$HOSTNAME" = "njsoly-hp" ]] || [[ "$HOSTNAME" = "k55n-w7" ]] || [[ "$OSTYPE" = "cygwin" ]]; then
	printf "hostname is %s; setting TERM to cygwin\n." "$HOSTNAME"
	export TERM=cygwin
	
	if [[ -x "${miscellany}/windows-start-ssh-agent.bash" ]]; then
		printf "starting ssh agent...\n"
		. ${miscellany}/windows-start-ssh-agent.bash
	fi
fi


if [[ -n "$(command -v branchname)" ]]; then
	export PS1='\[\e[36m\][\t]\[\e[0m\] \[\e[32m\][$(branchname||"")]\[\e[0m\] \[\e[33m\]\w\[\e[0m\] $ '
fi

if [[ -n "$WSL_DISTRO_NAME" && -x $miscellany/.bashrc_wsl ]]; then
	. $miscellany/.bashrc_wsl
fi

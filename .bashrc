#!/usr/bin/env bash

## set the shell ##
export PS1_DRAFT0="\e[32m\e[40m[\t]\e\] \e[0m\e[36m \w \e[35m\n$\[\] \e[0m"
export PS1_DRAFT1='\e[32;40m[\t]\e[m \e[0;36m\w\e[m \e[0;35m$\e[m '

##  exports  ##
export global_ignores="~/.gitignore_global"
export _GRAY_BLOCK=░
export _GRAY_BLOCK_8=░░░░░░░░

##  terminal colors  ##
export __reset=\\e[0m
export __red=\\e[31m
export __green=\\e[32m
export __yellow=\\e[33m
export __blue=\\e[34m
export __purple=\\e[35m
export __cyan=\\e[36m
export __gray=\\e[37m

export __pink=\\e[1\;31m
export __lime=\\e[1\;32m
export __white=\\e[1\;37m
export __magenta=\\e[1\;35m

export __red1=\\e[1\;31m
export __green1=\\e[1\;32m
export __yellow1=\\e[1\;33m
export __blue1=\\e[1\;34m
export __purple1=\\e[1\;35m
export __cyan1=\\e[1\;36m
export __gray1=\\e[1\;37m


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

## Help with CTRL+direction commands, etc
if [[ "$OSTYPE" == "cygwin" ]]; then
	[[ -x ./.inputrc ]] && bind -f ./.inputrc
fi

##############  application shorthands  #############
#-------              Windows                -------#
if [[ "$OS" = "Windows_NT" ]]; then
	alias npp='notepad++'
	alias notepadpp='notepad++'
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
	elif [[ "$OSTYPE" = "linux-gnu" ]]; then
		miscellany=/mnt/c/Users/njsoly/miscellany
	fi
# TRY A DEFAULT
elif [[ -z "$miscellany" && -d $HOME/miscellany ]]; then
	miscellany=$HOME/miscellany
fi
export miscellany

########_  functions  _########
if [[ -d ${miscellany}/bash.fxns.d ]]; then
	echo -e "${__green}sourcing functions from ${__yellow}\$miscellany/bash.fxns.d${__reset}."
	for f in ${miscellany}/bash.fxns.d/*;
	do {
		[[ -x $(realpath ${f}) ]] && echo -e "sourcing ${__cyan}$f${__reset}" && . $(realpath ${f}) || :
	}
	done
elif [[ -d $PWD/bash.fxns.d ]]; then
	source $PWD/bash.fxns.d/*
fi

########  bash git prompt setup ###################
if [[ -f "/usr/local/opt/bash-git-prompt/share/gitprompt.sh" ]]; then
	__GIT_PROMPT_DIR="/usr/local/opt/bash-git-prompt/share"
	source "/usr/local/opt/bash-git-prompt/share/gitprompt.sh"
elif [[ "$OSTYPE" = "cygwin" ||  -n "$(command -v branchname)" ]]; then
	echo -e "using ${__cyan}prompt_color_function.bash.source${__reset} to set ${__blue}PS1.${__reset}"
	source "$miscellany/prompt_color_function.bash.source"
fi


if [[ "$HOSTNAME" = "njsoly-hp" ]] || [[ "$HOSTNAME" = "k55n-w7" ]] || [[ "$OSTYPE" = "cygwin" ]]; then
	printf "hostname is %s; setting TERM to cygwin\n." "$HOSTNAME"
	export TERM=cygwin

	if [[ -x "${miscellany}/windows-start-ssh-agent.bash" ]]; then
		printf "starting ssh agent...\n"
		. ${miscellany}/windows-start-ssh-agent.bash
	fi
fi


if [[ -n "$WSL_DISTRO_NAME" && -x $miscellany/.bashrc_wsl ]]; then
	. $miscellany/.bashrc_wsl
fi

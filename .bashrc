#!/usr/bin/env bash

__err=0

## set the shell ##
export PS1_DRAFT0="\e[32m\e[40m[\t]\e\] \e[0m\e[36m \w \e[35m\n$\[\] \e[0m"
export PS1_DRAFT1='\e[32;40m[\t]\e[m \e[0;36m\w\e[m \e[0;35m$\e[m '

pushd $(dirname ${BASH_SOURCE}) > /dev/null || { echo "could not establish miscellany repo root"; __err=6; return ${__err}; }
source asciiart.bash && echo "sourced asciiart.bash" || { echo "could not find asciiart.bash"; __err=7; }
source terminal_color.bash && echo "sourced terminal_color.bash" || { echo "could not find terminal_color.bash"; __err=8; }

##  exports  ##
export global_ignores="~/.gitignore_global"
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

########  Windows subshell Linux (WSL)  #############
if [[ -n "$WSL_DISTRO_NAME" && -x $miscellany/.bashrc_wsl ]]; then
	. $miscellany/.bashrc_wsl
fi
######################################################


#### end
popd > /dev/null
return ${__err}

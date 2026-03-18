#!/usr/bin/env bash

__err=0

## set the shell ##
export PS1_DRAFT0="\e[32m\e[40m[\t]\e\] \e[0m\e[36m \w \e[35m\n$\[\] \e[0m"
export PS1_DRAFT1='\e[32;40m[\t]\e[m \e[0;36m\w\e[m \e[0;35m$\e[m '

pushd "$(dirname "${BASH_SOURCE[0]}")" > /dev/null || { echo "could not establish miscellany repo root"; __err=6; return ${__err}; }
if [[ -f "terminal_color.bash" ]]; then
	. "terminal_color.bash"
	printf "sourced ${__cyan}terminal_color.bash${__reset}\n"
else
	echo "could not find terminal_color.bash"
	__err=8
fi
if [[ -f "asciiart.bash" ]]; then
	. "asciiart.bash"
	printf "sourced ${__cyan}asciiart.bash${__reset}\n"
else
	echo "could not find asciiart.bash"
	__err=7
fi

##  exports  ##
export global_ignores="$HOME/.gitignore_global"
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
alias ga='git add'
alias gau='git add -u'
alias bra='git branch -a'
alias brch='git branch'
alias remtv='git remote -v'
alias gsuno='git status -uno'
alias gsnu='git status -uno'
alias gco='git checkout'
alias gstat='git diff --stat'
alias gd='git diff'
if [[ -x ./set_git_aliases.bash ]]; then
	. ./set_git_aliases.bash
fi

## other aliases ##
alias dc='docker-compose'

## Help with CTRL+direction commands, etc
if [[ "$OSTYPE" == "cygwin" ]]; then
	[[ -x ./.inputrc ]] && bind -f ./.inputrc
fi

## alias-like others ##
[ `command -v path` ] || path () { echo $PATH; }

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
# skip if already set
if [[ -n "$miscellany" ]]; then
	printf "not setting ${__cyan}miscellany${__reset} variable; already set to $miscellany.\n"
fi
#if [[ `echo $miscellany | grep -e '^C:\\\\'` ]]; then
#	echo "${__yellow1}FYI: ${__cyan}miscellany${__RESET} was set to C:\\."
#	unset miscellany
#fi

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

## set miscellany-me
if [[ -n "$miscellany_me" ]]; then
	printf "${__green}miscellany_me${__reset} has already been set to ${miscellany_me}.\n"
elif [[ -d "${miscellany}-me" ]]; then
	printf "${__green}miscellany-me${__reset} repo found right next to miscellany.\n"
	export miscellany_me="${miscellany}-me"
fi

########_  functions  _########
if [[ -d ${miscellany}/bash.fxns.d ]]; then
	printf "${__green}sourcing functions from ${__yellow}\$miscellany/bash.fxns.d${__reset}.\n"
	for f in "${miscellany}"/bash.fxns.d/*;
	do {
		[[ -x $(realpath "${f}") && -f $(realpath "${f}") ]] && printf "    sourcing ${__cyan}%s${__reset}\n" "$(realpath --relative-to=${HOME} ${f})" && . "$(realpath ${f})" || :
	}
	done
elif [[ -d ${PWD}/bash.fxns.d ]]; then
	source "${PWD}"/bash.fxns.d/*
else
	printf "${__red}bash.fxns.d/ not found.${__reset}\n"
fi



[[ "$OSTYPE" = "cygwin" ]] && _in_cygwin=1 || _in_cygwin=0
[[ "$OSTYPE" = "msys" ]] && _in_mingw=1 || _in_mingw=0
[[ -n "${WSL_DISTRO_NAME}" ]] && _in_wsl=1 || _in_wsl=0
echo "in cygwin: $_in_cygwin"
echo "in mingw: $_in_mingw"
echo "in wsl: $_in_wsl"
[[ $_in_cygwin && $_in_mingw && $_in_wsl ]] && _in_windows=1 || _in_windows=0
echo "in windows: $_in_windows"
[[ "$OSTYPE" = "linux-gnu" ]] && _in_linux=1 || _in_linux=0
echo "in linux: $_in_linux"

if (( $_in_cygwin )); then echo "we're in cygwin"; else echo "we're not in cygwin"; fi
if (( $_in_mingw )); then echo "we're in mingw"; else echo "we're not in mingw"; fi
if (( $_in_wsl )); then echo "we're in wsl"; else echo "we're not in wsl"; fi
if (( $_in_windows )); then echo "we're in windows"; else echo "we're not in windows"; fi
if (( $_in_linux )); then echo "we're in linux"; else echo "not in linux"; fi
if (( 1 )); then echo "blank is good"; else echo "blank is bad"; fi

########  bash git prompt setup ###################
# accept gitprompt.sh from Homebrew first of all
if [[ -f "/usr/local/opt/bash-git-prompt/share/gitprompt.sh" ]]; then
	__GIT_PROMPT_DIR="/usr/local/opt/bash-git-prompt/share"
	source "/usr/local/opt/bash-git-prompt/share/gitprompt.sh"
# then take my locals of in Cygwin, WSL, mingw, or if my function `branchname` has been declared
elif [[ "$OSTYPE" = "cygwin" || -n ${WSL_DISTRO_NAME} || "$OSTYPE" = "msys" || -n "$(command -v branchname)" ]]; then
	echo -e "using ${__cyan}prompt_color_function.bash.source${__reset} to set ${__blue}PS1.${__reset}"
	source "$miscellany/prompt_color_function.bash.source"
else
  printf "%s[INFO]%s Did not source gitprompt decoration for Windows.\n" "${__white}" "${__reset}"
fi


if [[ "$HOSTNAME" = "njsoly-hp" ]] || [[ "$HOSTNAME" = "k55n-w7" ]] || [[ "$OSTYPE" = "cygwin" ]]; then
	printf "hostname is %s; setting TERM to cygwin\n." "$HOSTNAME"
	export TERM=cygwin

	if [[ -x "${miscellany}/windows-start-ssh-agent.bash" ]]; then
		printf "starting ssh agent...\n"
		. "${miscellany}"/windows-start-ssh-agent.bash
	fi
fi

########  Windows subshell Linux (WSL)  #############
if [[ -n "$WSL_DISTRO_NAME" && -x $miscellany/.bashrc_wsl ]]; then
	. "$miscellany"/.bashrc_wsl
fi
######################################################

######### run miscellany-me profile ############
if [[ -n "${miscellany_me}" ]]; then
	if [[ -f ${miscellany_me}/.bashrc ]]; then
		printf "running ${__blue}miscellany-me/.bashrc${__reset}. \n"
		. ${miscellany_me}/.bashrc
	fi
fi

popd > /dev/null
return "${__err}"

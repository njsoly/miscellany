#!/bin/bash

## set the shell ##
#export PS1="\e[32m\e[40m[\t]\e\] \e[0m\e[36m \w \e[35m\n$\[\] \e[0m"
export PS1='\e[32;40m[\t]\e[m \e[0;36m\w\e[m \e[0;35m$\e[m '

##  exports  ##
export global_ignores="~/.gitignore_global"

## aliases to built-ins ##
alias lS='ls -s --block-size=KiB -A --color'
alias lskb='lS'
alias ls='ls -A -F --color'
alias dirs='dirs -p'
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

##  application shorthands  ##
alias npp='notepad++'
alias notepadpp='notepad++'
alias sonar-scanner='sonar-scanner.bat'

## load up the directory stack ##

clear

##  functions  ##

_calc_elapsed () {
    _elapsed=$(bc -l <<< "scale=3; $_finish_milliseconds-$_start_milliseconds");
    echo $_elapsed
}

[[ -x ~/run_this_timed.bash ]] && . ~/run_this_timed.bash

[[ ! -x ~/run_this_timed.bash ]] && \
run_this_timed () { 
    _command="$@";

    _start=$(date +%H:%M:%S.%N);
    _start=${_start:0:12};
    _start_seconds=$(date +%s);
    _start_date=$(date +%F);
    _start_milliseconds=${_start_seconds}${_start:8}
    _msg="starting \"${_command}\" at $_start 
$_start_date ($_start_milliseconds)";
    echo $_msg;
    echo $_msg >> ~/.time_ab.log

    ${_command}

    _finish=$(date +%H:%M:%S.%N);
    _finish=${_finish:0:12};
    _finish_seconds=$(date +%s);
    _finish_date=$(date +%F);
    _finish_milliseconds=${_finish_seconds}${_finish: -3}
    _msg="finished \"${_command}\" at $_finish 
$_finish_date ($_finish_milliseconds)";
    echo $_msg;
    echo $_msg >> ~/.time_ab.log;

    _elapsed=$[ $_finish_milliseconds - $_start_milliseconds ]
    _msg="command \"${_command}\" ran from $_start to $_finish ($_elapsed ms)"
    echo $_msg;
    echo $_msg >> ~/.time_elapsed.log;
}

alias cygwin_setup='start cmd /c  $(cygpath -w "/cygdrive/c/Users/A1434206/Downloads/setup-x86_64.exe")'

_timestamp () 
{ 
	_ts_time="$(date +%H:%M:%S.%N)";
	_ts_seconds=$(date +%s)
	_ts_time=${_ts_time:0:12}
	_ts_date="$(date +%F)"
	_ts="$_ts_time $_ts_date"
	echo "$_ts"
}

if [ -f "/usr/local/opt/bash-git-prompt/share/gitprompt.sh" ]; then
   __GIT_PROMPT_DIR="/usr/local/opt/bash-git-prompt/share"
   source "/usr/local/opt/bash-git-prompt/share/gitprompt.sh"
fi


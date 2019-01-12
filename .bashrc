#!/bin/bash

PATH=$PATH:.

declare -x HOME=$PWD
declare -x BASHES=$PWD/.bash
declare -x BATS=$PWD/.bats
declare -x BASE=$PWD/base
declare -x miscellany=/cygdrive/m/miscellany

export HOME
export BASHES
export BATS
export BASE
export miscellany

[ -x $BASHES/.bashrc ] && . $BASHES/.bashrc
[ -x $BASHES/.bash_aliases ] && . $BASHES/.bash_aliases
[ -x $BASHES/aliases.bash ] && . $BASHES/aliases.bash
[ -x $BASHES/aliases.HOSTNAME ] && . $BASHES/aliases.bash.$HOSTNAME

declare -x USERPROFILE=$HOME
export USERPROFILE

declare -x DROPBOX_BASHES=$dropbox/.bash/
export DROPBOX_BASHES

pathAppends=$(cygpath $XAMPP_DIR/mysql/bin):$(cygpath $XAMPP_DIR/php/bin)
#printf "adding \"%s\" to PATH." "$pathAppends"
declare -x PATH=$PATH:$DROPBOX_BASHES:$pathAppends
export PATH

alias ls='ls -A --color'
alias dirs='dirs -p'
alias lS='ls -s --block-size=KiB'
alias lskb='ls -s --block-size=KiB'

alias gs='git status'
alias bra='git branch -a'
alias remtv='git remote -v'
alias gsnu='git status -uno'


export PS1='\[\e]0;\w\a\]\n\[\e[32m\]\u@\h \[\e[33m\]\w\[\e[0m\]\n$ '

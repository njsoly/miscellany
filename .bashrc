#!/bin/bash

PATH=$PATH:.

declare -x HOME=$PWD && export HOME
[ -d $PWD/.bash ] && {
	declare -x BASHES=$PWD/.bash
	export BASHES }
declare -x BATS=$PWD/.bats && export BATS
declare -x BASE=$PWD/base && export BASE
[ -d "/cygdrive/m" ] && \
	declare -x miscellany=/cygdrive/m/miscellany && \
	export miscellany

export HOME

[ -x $BASHES/.bashrc ] && . $BASHES/.bashrc
[ -x $BASHES/.bash_aliases ] && . $BASHES/.bash_aliases
[ -x $BASHES/aliases.bash ] && . $BASHES/aliases.bash
[ -x $BASHES/aliases.HOSTNAME ] && . $BASHES/aliases.bash.$HOSTNAME

declare -x USERPROFILE=$HOME && export USERPROFILE

[ -d "$dropbox" ] && declare -x DROPBOX_BASHES=$dropbox/.bash/ && export DROPBOX_BASHES

[ -d "$XAMPP_DIR" ] && [ -x "$(command -v cygpath)" ] && 
pathAppends=$(cygpath $XAMPP_DIR/mysql/bin):$(cygpath $XAMPP_DIR/php/bin)

printf "adding \"%s\" to PATH." "$pathAppends"
declare -x PATH=$PATH:$DROPBOX_BASHES:$pathAppends && export PATH

alias ls='ls -A --color'
alias dirs='dirs -p'
alias lS='ls -s --block-size=KiB'
alias lskb='ls -s --block-size=KiB'

alias gs='git status'
alias bra='git branch -a'
alias remtv='git remote -v'
alias gsnu='git status -uno'



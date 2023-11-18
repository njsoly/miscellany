#!/usr/bin/env bash


say () {
	printf "**  $@  **\n"
}

say  "the source is $PWD/$BASH_SOURCE"

## introduce the miscellany repo to the environment ##
if [[ -z "$miscellany" ]]; then
	say "setting \$miscellany to $PWD."
fi

if [[ ! $(cat ~/.bashrc | grep miscellany/.bashrc) ]]; then
	say "adding \$miscellany/.bashrc to \~/.bashrc."
fi

say "now calling ./setup_git.bash"
./setup_git.bash


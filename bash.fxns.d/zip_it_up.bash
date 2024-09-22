#!/usr/bin/env bash

########################################################
# zip_it_up.bash
#
# You zip stuff up.
########################################################

zip_it_up () {
	if [[ "$1" == "-h" || "$1" == "--help" ]]; then
		printf 	"${__blue}zip_it_up${__reset}: A function to help you zip things.\n"
		printf	"what ${__blue1}else${__reset} do you want?\n"
		return 0
	fi

	if [[ -z "$2" ]]; then
		printf "${__red}ERROR:${__reset} You need to specify a destination ${__red}and${__reset} a list of files.\n"
		return 4
	else
		local dest=$1
		local list=$2

		printf "${__green}Compressing ${__white}${list}${__reset} to ${__gray}${dest}${__reset}."
		printf "\n"
		zip -r -m -1 -y ${dest} ${list}
		ls --size --block-size=KiB ${dest}
	fi
}

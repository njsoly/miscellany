#!/usr/bin/env bash

########################################################
# zip_it_up.bash
#
# So, this is pretty coool.....
########################################################

zip_it_up () {
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

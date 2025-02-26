check_reqs () {
	if [[ ! `command -v git` ]]; then
		printf "${__red}ERROR:${__reset} ${__green}git${__reset} isn't installed. Quitting.\n"
		return 44
	elif [[ ! `command -v branchname` ]]; then
		printf "${__red}ERROR:${__reset} the function ${_green}branchname${_reset} is not sourced. Quitting.\n"
		return 22
	fi

}

check_not_already_configured_to_origin () {
	git config -l | grep -e 'config branch' | grep -e $(branchname) | grep -e origin > /dev/null
	if [[ "$?" == "0" ]]; then
		printf "The branch ${__purple}$(branchname)${__reset} is already configured to ${__cyan}origin${__reset}. "
		printf "Will ${__yellow}not${__reset} proceed to configure it to the fork.\n"
		return 98764
	fi
}

check_reqs

[[ "$?" == "0" ]] && \
fork-only-branch-setup () 
{ 
	check_not_already_configured_to_origin
	git config branch.$(branchname).remote fork;
	git config branch.$(branchname).merge refs/heads/$(branchname)
}

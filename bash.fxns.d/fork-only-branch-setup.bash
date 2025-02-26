#!/usr/bin/env bash

## If these aren't satisfied, we don't even want to declare the function. ##
check_hard_reqs () {
	if [[ ! `command -v git` ]]; then
		printf "${__red}ERROR:${__reset} ${__green}git${__reset} isn't installed. Quitting.\n"
		return 44
	fi
}


check_hard_reqs
[[ "$?" == "0" ]] || printf "\n\n\t:${__red}ERROR: the thing didn't quit, even though it should have${__reset}.\n\n"

fork-only-branch-setup () {
        ## Check that my other functions are around.  We can check on these at runtime ##
        check_for_other_nearby_fxns () {
                if [[ ! `command -v branchname` ]]; then
                        printf "${__red}ERROR:${__reset} the function ${_green}branchname${_reset} is not sourced.\n"
                        return 22
                fi
        }

        ## Don't re-configure a branch away from origin remote, if upstream is already configured.  ##
        check_not_already_configured_to_origin () {
                git config -l | grep -e 'branch' | grep -e $(branchname) | grep -e origin > /dev/null
                if [[ "$?" == "0" ]]; then
                        printf "The branch ${__purple}$(branchname)${__reset} is already configured to ${__cyan}origin${__reset}. "
                        printf "Will ${__yellow}not${__reset} proceed to configure it to the fork.\n"
                        return 98764
                fi
        }

        check_not_already_configured () {
                git config -l | grep -e 'branch' | grep -e $(branchname) > /tmp/alreadyConfigged
                if [[ "$?" == "0" ]]; then
                        printf "The branch ${__purple}$(branchname)${__reset} is already configured: \n"
                        cat /tmp/alreadyConfigged
                        printf "\n${__blue}Good day.${__reset}\n"

                        #rm /tmp/alreadyConfigged
                fi
        }

	check_for_other_nearby_fxns || ( printf "${__yellow}BYE${__pink} then.${__reset}.\n" && sleep 10 && exit 77)
	check_not_already_configured_to_origin || return 111
	check_not_already_configured || return 666

	printf "Branch ${__white}%s${__reset} does not have an upstream set.  Pointing it at the fork.\n" $(branchname)
	git config branch.$(branchname).remote fork;
	git config branch.$(branchname).merge refs/heads/$(branchname)
}

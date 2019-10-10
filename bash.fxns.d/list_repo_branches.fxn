#!/usr/bin/env bash
_list_repo_branches ()
{ 
    local projects_directory=${1-HOME/genesis-projects}

    if [[ ! -d $projects_directory ]]; then
        projects_directory=$PWD
    fi

    local branches_file=$projects_directory/.branches
    if [[ ! -w $branches_file ]]; then
        branches_file=$HOME/.branches
    fi

    pushd $projects_directory > /dev/null;
    [ -f $branches_file ] && rm $branches_file;
    for f in $projects_directory ./*;
    do 
        [ -d "$f" ] && [ -d "$f/.git/" ] && { 
            pushd $f > /dev/null;
            printf "%s: %s\n" "${f//*\//}" "$(git branch | grep \*)" >> $branches_file;
            popd > /dev/null
        }
    done;
    popd > /dev/null
    [[ -f $branches_file ]] && cat $branches_file || echo "no branches in \"${projects_directory}\""
}

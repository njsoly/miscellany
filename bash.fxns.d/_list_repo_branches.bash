#!/usr/bin/env bash
_list_repo_branches ()
{ 
    local branches_file=~/.branches
    local projects_directory=~/genesis-projects
    
    if [[ ! -d $projects_directory ]]; then
        projects_directory=$PWD
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
    cat $branches_file
}

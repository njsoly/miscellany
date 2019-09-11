_list_repo_branches () 
{ 
    local branches_file=~/.branches
    local genesis_projects_directory=~/genesis-projects

    pushd $genesis_projects_directory > /dev/null;
    [ -f $branches_file ] && rm $branches_file;
    for f in $genesis_projects_directory ./*;
    do
        { 
            [ -d "$f" ] && [ -d "$f/.git/" ] && { 
                pushd $f > /dev/null;
                printf "%s: %s\n" "${f//*\//}" "$(git branch | grep \*)" >> $branches_file;
                popd > /dev/null
            } > /dev/null
        };
    done;
    popd > /dev/null
    cat $branches_file
}

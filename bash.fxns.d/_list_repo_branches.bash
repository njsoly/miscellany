_list_repo_branches () 
{ 
    cd ~/genesis-projects;
    [ -f ~/.branches ] && rm ~/.branches;
    for f in ./*;
    do
        { 
            [ -d "$f" ] && [ -d "$f/.git/" ] && { 
                pushd $f;
                printf "%s: %s\n" "$f" "$(git branch | grep \*)" >> ~/.branches;
                popd > /dev/null
            } > /dev/null
        };
    done;
    cat ~/.branches
}

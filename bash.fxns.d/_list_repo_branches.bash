_list_repo_branches () 
{ 
    pushd ~/genesis-projects > /dev/null;
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
    popd > /dev/null
    cat ~/.branches
}

git-generate-delete-statements-remote () 
{ 
    for b in $(git for-each-ref refs/remotes/origin --format "%(refname)" "echo %(refname)" | sed 's@refs/heads/@@g' );
    do
        echo "git branch -r -D $b";
    done
}

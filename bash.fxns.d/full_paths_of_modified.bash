full_paths_of_modified () 
{ 
    git status | grep "modified\:" | sed '/\(\ *\)modified\:\(\ *\)/s///' | tr -d '\t'
}

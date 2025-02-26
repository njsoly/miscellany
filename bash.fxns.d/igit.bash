igit () 
{ 
    if [[ -z "$ig" ]]; then
        printf "${__purple}nooope${__orange}:${__reset} You should have set the 'ig' variable for this function.\n";
        exit;
    fi;
    if [[ ! -f ./.gitignore ]]; then
        printf "${__purple}noooooope${__green}: ${__reset}Not going to append an ignore because there isn't a ${__blue}.gitignore${__reset} in this directory.\n";
        exit 2;
    fi;
    printf "${__gray}Ignoring ${__cyan}%s${__reset}.\n" $ig;
    echo $ig >> .gitignore
}

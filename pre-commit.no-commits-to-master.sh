#!/bin/sh

# I found this online at StackOverflow.  
# Should help with preventing some rookie mistakes.
# 
# They say you don't need to set it executable if you're on Windows,
# but we'll see.
#
# This file is to be put inside a repository's .git/ folder,
# as ${REPOSITORY_ROOT}/.git/hooks/pre-commit .
# 

branch="$(git rev-parse --abbrev-ref HEAD)"

if [ "$branch" = "master" ]; then
  echo "You can't commit directly to master branch"
  exit 1
fi

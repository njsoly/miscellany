#!/usr/bin/env bash

## This should be idempotent and fast,                                   ##
## because I don't want to have to deal with checking whether to run it. ##

git config --global user.email "njsoly@syntj.com"
git config --global user.name "njsoly"
git config --global alias.s "status"
git config --global alias.co "checkout"
git config --global pull.rebase false
git config --global push.default upstream
git config --global push.autosetupremote true
git config --global alias.staged "diff --cached"
git config --global core.filemode true
git config --global core.ignorecase true
git config --global core.logallrefupdates true

#!/usr/bin/env bash

_test_subject=../list_repo_branches.fxn

if [ -x ${_test_subject}e ]; then
  source ../list_repo_branches.fxn
  printf "${GRN}Testing ${CYN}${_test_subject}${RST}.\n"
else
  _rel_path=$(realpath --relative-to=${PWD} ${BASH_SOURCE})
  printf "${CYN1}${_rel_path}: ${RED}This is all messed up. Could not find ${CYN}${_test_subject}${RST}.\n"
fi

printf "finished it.\n"

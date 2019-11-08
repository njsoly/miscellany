:::::::::::::::::::::    userStartup.bat    :::::::::::::::::::::::
::
:: This file is intended to be a Windows equivalent for .bashrc. 
:: I leave it up to me (or whomever uses this) to put a call for it in 
:: the Programs/Startup folder in order to use it.
::
::
::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
::
:: example usage for the CMD "if":
::   IF [NOT] ERRORLEVEL number command
::   IF [NOT] string1==string2 command
::   IF [NOT] EXIST filename command
::
:: 	 IF EXIST filename. (
::  	del filename.
::   ) ELSE (
::   	echo filename. missing.
::   )
::
:: IF EXIST filename. (del filename.) ELSE echo filename. missing
::
:: 

:SETUP
	@echo off
	@echo starting "%0"
	echo miscellany is %miscellany%
	
:CHECK_PATH
	where gsnu 2> NUL
	@if ERRORLEVEL 1 (call %miscellany%\batch\.userStartup)
	
:PUSHD_MISC
	@echo on
	pushd %miscellany%

:BYE
	@echo finished "%0"

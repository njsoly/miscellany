:::::::::::::::::::::    userStartup.bat    :::::::::::::::::::::::
::
:: This file is intended to be a Windows equivalent for .bashrc. 
:: I leave it up to me (or whomever uses this) to put a call for it in 
:: the Programs/Startup folder in order to use it.
::
:: ... actually maybe it would be a cool thing to add a section in
:: here that writes a simple .BAT file to that directory, with only
:: one line to call userStartup.bat.
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
	
:DECIDE
	where gsnu 2> NUL
	@if NOT ERRORLEVEL 1 goto ALREADY_THERE
	
:SHRINK_THE_PATH
call shrink_path

:APPEND_BATCH_DIR_TO_PATH
	@echo adding "%CD%" to the path.
	set PATH=%PATH%;%CD%
	setx PATH "%PATH%;%CD%"
	@if ERRORLEVEL 1 (echo PATH changes didn't work.)
	@goto BYE

:ALREADY_THERE
@echo %CD% is already in path.

:BYE
	@echo finished "%0"
	@exit /B


:: .userStartup ::
:: added to registry with:
:: 	
::	reg add "HKCU\Software\Microsoft\Command Processor" /v AutoRun ^
::  	/t REG_EXPAND_SZ /d "%"USERPROFILE"%\.userStartup.bat" /f
::

@echo off
:DECIDE
	where gsnu 2> NUL
	@if NOT ERRORLEVEL 1 goto ALREADY_THERE
	
:APPEND_BATCH_DIR_TO_PATH
	@echo adding "%CD%" to the path.
	call shrink_path
	set PATH=%PATH%;%CD%
	setx PATH "%PATH%;%CD%"
	@if ERRORLEVEL 1 (echo PATH changes didn't work.)
	@goto BYE
	
:ALREADY_THERE
	@echo %CD% is already in path.

:BYE

@echo off
if "%1"=="-h" goto :help
if "%1"=="--help" goto :help
git status %*
goto :end

:help
echo gs.bat - CMD alias for 'git status'
echo     Passthrough for additional arguments.
echo.

:end



@echo off
if "%1"=="-h" goto :help
if "%1"=="--help" goto :help
git branch -a %*
goto :end

:help
echo bra.bat - CMD alias for 'git branch -a'
echo     Passthrough for additional arguments.
echo.
:end

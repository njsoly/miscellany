@echo off
if "%1"=="-h" goto :help
if "%1"=="--help" goto :help
git checkout %*
goto :end

:help
echo gco.bat - CMD alias for 'git checkout'
echo     Passthrough for additional arguments.
echo.

:end

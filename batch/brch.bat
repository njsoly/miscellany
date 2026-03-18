:: brch.bat ::
@echo off
if "%1"=="-h" goto :help
if "%1"=="--help" goto :help
git branch %*
goto :end

:help
echo brch.bat - CMD alias for 'git branch'
echo     Passthrough for additional arguments.
echo.

:end

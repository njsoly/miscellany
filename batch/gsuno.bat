:: gsuno.bat ::
@echo off
if "%1"=="-h" goto :help
if "%1"=="--help" goto :help
git status -uno %*
goto :end

:help
echo gsuno.bat - CMD alias for 'git status -uno'
echo     Passthrough for additional arguments.
echo.

:end

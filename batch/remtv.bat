 :: remtv.bat ::
@echo off
if "%1"=="-h" goto :help
if "%1"=="--help" goto :help
git remote -v %*
goto :end

:help
echo remtv.bat - CMD alias for 'git remote -v'
echo     Passthrough for additional arguments.
echo.

:end
 
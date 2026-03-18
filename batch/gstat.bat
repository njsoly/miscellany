 :: gstat.bat ::
@echo off
if "%1"=="-h" goto :help
if "%1"=="--help" goto :help
git diff --stat %*
goto :end

:help
echo gstat.bat - CMD alias for 'git diff --stat'
echo     Passthrough for additional arguments.
echo.

:end
 
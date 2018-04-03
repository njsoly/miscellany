:: BATCH FOR pulling git repo from Jenkins. ::
@echo off
@echo.
@echo ::   [%DATE% %TIME%]   ENTER   %0.   ::
@echo.

@set out="%CD%\.out"
@echo    output file: %out%

@call :pull M:\Java_Misc
@call :pull M:\miscellany
@call :pull M:\raz-follower
@call :pull M:\arduino-sketchbook
@call :pull D:\Users\njsoly\git\hackerrank
@call :pull D:\Users\njsoly\git\Java_Misc
@call :pull M:\base

@goto done

:pull 
@echo ::   START pull subroutine("%1")   ::
@pushd "%1"
@git fetch --all 2>> %out% 1>&2
@git pull origin 2>> %out% 1>&2
if errorlevel 1 (echo ERROR: failed to pull "%1".)
@popd
@echo ::   END pull subroutine("%1")   ::
@exit /B


:done
@echo ::   [%DATE% %TIME%]   EXITING   %0.   ::
@exit /B
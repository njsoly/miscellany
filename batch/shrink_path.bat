:: shrink path ::

@echo %PATH% | sed "s@Program\ Files\ (x86)@pgf86@g" | sed "s@Program\ Files@pgf@g" > %HOME%\.PATH

@if ERRORLEVEL 1 goto PROBLEM_WITH_SED

@echo %PATH% | wc -m > %TMP%\.sizebefore
@echo %PATH% | sed "s@Program\ Files\ (x86)@pgf86@g" | sed "s@Program\ Files@pgf@g" | wc -m > %TMP%\.sizeafter

@if ERRORLEVEL 1 goto PROBLEM_WITH_SED

:: set %pathread% to the first thing in %HOME%\.PATH
@setx pathread /F %HOME%\.PATH /A 0,0
@setx PATH %pathread%
@echo.
@goto BYE

:PROBLEM_WITH_SED
@echo something went wrong with the sed command.

:BYE
@del %HOME%\.PATH 2> NUL
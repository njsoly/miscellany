@echo off
@echo params are %*
@echo MsgBox %* > %TEMP%\alert.vbs
%TEMP%\alert.vbs

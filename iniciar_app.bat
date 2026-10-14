@echo off
setlocal
set "JAVA_HOME=%~dp0..\jdk-25.0.4+7"
set "PATH=%JAVA_HOME%\bin;%PATH%"
cd /d "%~dp0"
echo Iniciando SIRA Veterinario...
call "%~dp0..\netbeans\java\maven\bin\mvn.cmd" javafx:run
endlocal

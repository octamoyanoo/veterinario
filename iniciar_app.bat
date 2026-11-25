@echo off
setlocal
set "JAVA_HOME=%~dp0..\jdk-25.0.4+7"
set "PATH=%JAVA_HOME%\bin;%PATH%"
cd /d "%~dp0"
echo ==========================================
echo Iniciando SIRA Veterinario...
echo ==========================================
call "%~dp0..\netbeans\java\maven\bin\mvn.cmd" javafx:run
if %ERRORLEVEL% NEQ 0 (
    echo.
    echo Ocurrio un error al ejecutar la aplicacion.
    pause
)
endlocal

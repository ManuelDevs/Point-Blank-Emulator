@echo off
:start
TITLE Project Blackout Battle Server
echo %DATE% %TIME% Battle server is running! >> battle_is_running.tmp
echo.
"C:\Program Files\Java\jre1.8.0_231\bin\java.exe" -Dfile.encoding=UTF-8 -Xms64m -Xmx64m -cp libs/*;configs; ru.pb.battle.Application
if ERRORLEVEL 2 goto restart
if ERRORLEVEL 1 goto error
goto end
:restart
echo.
echo %DATE% %TIME% Battle Server is restarted >> battle_is_running.tmp
echo.
goto start
:error
echo.
echo %DATE% %TIME% Battle Server terminated abnormaly>> battle_is_running.tmp
echo.
:end
echo.
echo %DATE% %TIME% Battle Server terminated >> battle_is_running.tmp
echo.
pause

@echo off
:start
TITLE Project Blackout Game Server
echo %DATE% %TIME% Game server is running! >> game_is_running.tmp
echo.
"C:\Program Files\Java\jre1.8.0_231\bin\java.exe" -Dfile.encoding=UTF-8 -Xms512m -Xmx1024m -cp libs/*;configs; ru.pb.game.Application
if ERRORLEVEL 2 goto restart
if ERRORLEVEL 1 goto error
goto end
:restart
echo.
echo %DATE% %TIME% Game Server is restarted >> game_is_running.tmp
echo.
goto start
:error
echo.
echo %DATE% %TIME% Game Server terminated abnormaly>> game_is_running.tmp
echo.
:end
echo.
echo %DATE% %TIME% Game Server terminated >> game_is_running.tmp
echo.
pause

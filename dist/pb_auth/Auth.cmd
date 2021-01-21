@echo off
:start
TITLE Point Blank Auth Server
echo %DATE% %TIME% Auth server is running! >> auth_is_running.tmp
echo.
"C:\Program Files\Java\jre1.8.0_231\bin\java.exe" -Dfile.encoding=UTF-8 -Xms32m -Xmx32m -cp libs/*;configs; ru.pb.auth.Application
if ERRORLEVEL 2 goto restart
if ERRORLEVEL 1 goto error
goto end
:restart
echo.
echo %DATE% %TIME% Auth Server is restarted >> auth_is_running.tmp
echo.
goto start
:error
echo.
echo %DATE% %TIME% Auth Server terminated abnormaly>> auth_is_running.tmp
echo.
:end
echo.
echo %DATE% %TIME% Auth Server terminated >> auth_is_running.tmp
echo.
pause

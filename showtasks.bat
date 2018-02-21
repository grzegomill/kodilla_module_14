call runcrud.bat

if "%ERRORLEVEL%" == "0" goto open_chrome

goto fail

:open_chrome

call chrome.exe http://localhost:8080/crud/v1/tasks
goto end

:fail
echo  something fails!


:end
echo   script show tasks finished

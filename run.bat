@echo off
setlocal enabledelayedexpansion

set CLASSPATH=target\classes
for %%f in (target\dependency\*.jar) do set CLASSPATH=!CLASSPATH!;%%f

java -cp "%CLASSPATH%" org.example.studentApplication

endlocal
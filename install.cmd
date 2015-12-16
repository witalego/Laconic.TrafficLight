net stop teamcity

del c:\TeamCity\logs\*.* /Q

copy target\*.zip C:\ProgramData\JetBrains\TeamCity\plugins /Y

net start teamcity
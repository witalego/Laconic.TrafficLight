del c:\TeamCity\webapps\ROOT\WEB-INF\plugins\Laconic.TeamCity.TrafficLightListener.zip
for /D %%p in ("c:\TeamCity\webapps\ROOT\WEB-INF\plugins\.unpacked\Laconic.TeamCity.TrafficLightListener") do rmdir "%%p" /s /q
copy Laconic.TeamCity.TrafficLightListener.zip c:\TeamCity\webapps\ROOT\WEB-INF\plugins
del c:\TeamCity\logs\*.log
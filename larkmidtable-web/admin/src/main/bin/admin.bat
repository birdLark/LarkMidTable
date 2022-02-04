@echo off
set home=%~dp0

set conf_dir=%home%..\conf
set lib_dir=%home%..\lib\*
set log_dir=%home%..\logs

java -Dspring.profiles.active=standalone -Dlogging.file=%log_dir%\dbApi.log -classpath %conf_dir%;%lib_dir% com.larkmidtable.admin.AdminApplication
pause
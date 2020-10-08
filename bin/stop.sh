#!/bin/bash

p="$1"
admin="admin"
executor="executor"
RENT_DIR=$(cd $(dirname $0);cd ..; pwd)

if [ "$p" == "$admin" ];then
pid=$(ps -ef | grep "FlinkXAdminApplication" | grep -v grep | awk '{print $2}')
kill -15 $pid
echo "FlinkXAdminApplication的进程已经停止!!!!"
elif [ "$p" == "$executor" ];then
pid=$(ps -ef | grep "FlinkXExecutorApplication" | grep -v grep | awk '{print $2}')
kill -15 $pid
echo "FlinkXExecutorApplication的进程已经停止!!!!"
else
 echo "需要添加参数可选值 {admin,executor}"
 echo "如可以输入: sh start.sh admin"
 echo "如可以输入: sh start.sh executor"
fi
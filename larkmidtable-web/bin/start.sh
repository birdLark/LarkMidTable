#!/bin/bash

p="$1"
admin="admin"
executor="executor"
RENT_DIR=$(cd $(dirname $0);cd ..; pwd)

if [ "$p" == "$admin" ];then
sh $RENT_DIR/packages/admin/bin/admin.sh start
elif [ "$p" == "$executor" ];then
sh $RENT_DIR/packages/executor/bin/executor.sh start
else
 echo "需要添加参数可选值 {admin,executor}"
 echo "如可以输入: sh start.sh admin"
 echo "如可以输入: sh start.sh executor"

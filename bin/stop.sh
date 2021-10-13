#!/usr/bin/env bash

#
# Licensed to the Apache Software Foundation (ASF) under one or more
# contributor license agreements.  See the NOTICE file distributed with
# this work for additional information regarding copyright ownership.
# The ASF licenses this file to You under the Apache License, Version 2.0
# (the "License"); you may not use this file except in compliance with
# the License.  You may obtain a copy of the License at
#
#    http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

p="$1"
admin="admin"
executor="executor"
RENT_DIR=$(cd $(dirname $0);cd ..; pwd)

if [ "$p" == "$admin" ];then
pid=$(ps -ef | grep "Engine" | grep -v grep | awk '{print $2}')
kill -15 $pid
echo "Engine的进程已经停止!!!!"
elif [ "$p" == "$executor" ];then
pid=$(ps -ef | grep "FlinkXExecutorApplication" | grep -v grep | awk '{print $2}')
kill -15 $pid
echo "FlinkXExecutorApplication的进程已经停止!!!!"
else
 echo "需要添加参数可选值 {admin,executor}"
 echo "如可以输入: sh start.sh admin"
 echo "如可以输入: sh start.sh executor"
fi
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

export RENT_DIR=$(cd $(dirname $0);cd ..; pwd)

echo $RENT_DIR
tar -zxvf $RENT_DIR/packages/flinkx-admin_2.1.2_1.tar.gz  -C $RENT_DIR/packages/
tar -zxvf $RENT_DIR/packages/flinkx-executor_2.1.2_1.tar.gz  -C $RENT_DIR/packages/
echo "===admin    package finshed===="
echo "===executor package finshed===="
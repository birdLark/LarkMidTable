#!/usr/bin/env bash
################################################################################
# Licensed to the Apache Software Foundation (ASF) under one
# or more contributor license agreements.  See the NOTICE file
# distributed with this work for additional information
# regarding copyright ownership.  The ASF licenses this file
# to you under the Apache License, Version 2.0 (the
# "License"); you may not use this file except in compliance
# with the License.  You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
################################################################################

source "$(dirname "$0")"/kafka-common.sh

# prepare Kafka
echo "Generating sources..."

create_kafka_topic 1 1 user_behavior
java -cp target/flink-sql-submit.jar com.github.wuchong.sqlsubmit.SourceGenerator 1000 | $KAFKA_DIR/bin/kafka-console-producer.sh --broker-list localhost:9092 --topic user_behavior
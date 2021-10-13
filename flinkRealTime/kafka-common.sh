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

source "$(dirname "$0")"/env.sh

function create_kafka_json_source {
    topicName="$1"
    create_kafka_topic 1 1 $topicName

    # put JSON data into Kafka
    echo "Sending messages to Kafka..."

    send_messages_to_kafka '{"rowtime": "2018-03-12T08:00:00Z", "user_name": "Alice", "event": { "message_type": "WARNING", "message": "This is a warning."}}' $topicName
    send_messages_to_kafka '{"rowtime": "2018-03-12T08:10:00Z", "user_name": "Alice", "event": { "message_type": "WARNING", "message": "This is a warning."}}' $topicName
    send_messages_to_kafka '{"rowtime": "2018-03-12T09:00:00Z", "user_name": "Bob", "event": { "message_type": "WARNING", "message": "This is another warning."}}' $topicName
    send_messages_to_kafka '{"rowtime": "2018-03-12T09:10:00Z", "user_name": "Alice", "event": { "message_type": "INFO", "message": "This is a info."}}' $topicName
    send_messages_to_kafka '{"rowtime": "2018-03-12T09:20:00Z", "user_name": "Steve", "event": { "message_type": "INFO", "message": "This is another info."}}' $topicName
    send_messages_to_kafka '{"rowtime": "2018-03-12T09:30:00Z", "user_name": "Steve", "event": { "message_type": "INFO", "message": "This is another info."}}' $topicName
    send_messages_to_kafka '{"rowtime": "2018-03-12T09:30:00Z", "user_name": null, "event": { "message_type": "WARNING", "message": "This is a bad message because the user is missing."}}' $topicName
    send_messages_to_kafka '{"rowtime": "2018-03-12T10:40:00Z", "user_name": "Bob", "event": { "message_type": "ERROR", "message": "This is an error."}}' $topicName
}

function create_kafka_topic {
    $KAFKA_DIR/bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor $1 --partitions $2 --topic $3
}

function drop_kafka_topic {
    $KAFKA_DIR/bin/kafka-topics.sh --delete --zookeeper localhost:2181 --topic $1
}

function send_messages_to_kafka {
    echo -e $1 | $KAFKA_DIR/bin/kafka-console-producer.sh --broker-list localhost:9092 --topic $2
}

function start_kafka_cluster {
  if [[ -z $KAFKA_DIR ]]; then
    echo "Must run setup kafka dist dir before attempting to start Kafka cluster"
    exit 1
  fi

  $KAFKA_DIR/bin/zookeeper-server-start.sh -daemon $KAFKA_DIR/config/zookeeper.properties
  $KAFKA_DIR/bin/kafka-server-start.sh -daemon $KAFKA_DIR/config/server.properties

  # zookeeper outputs the "Node does not exist" bit to stderr
  while [[ $($KAFKA_DIR/bin/zookeeper-shell.sh localhost:2181 get /brokers/ids/0 2>&1) =~ .*Node\ does\ not\ exist.* ]]; do
    echo "Waiting for broker..."
    sleep 1
  done
}

function stop_kafka_cluster {
  $KAFKA_DIR/bin/kafka-server-stop.sh
  $KAFKA_DIR/bin/zookeeper-server-stop.sh

  # Terminate Kafka process if it still exists
  PIDS=$(jps -vl | grep -i 'kafka\.Kafka' | grep java | grep -v grep | awk '{print $1}'|| echo "")

  if [ ! -z "$PIDS" ]; then
    kill -s TERM $PIDS || true
  fi

  # Terminate QuorumPeerMain process if it still exists
  PIDS=$(jps -vl | grep java | grep -i QuorumPeerMain | grep -v grep | awk '{print $1}'|| echo "")

  if [ ! -z "$PIDS" ]; then
    kill -s TERM $PIDS || true
  fi
}
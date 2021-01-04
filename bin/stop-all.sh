#!/bin/bash

pid=$(ps -ef | grep "Engine" | grep -v grep | awk '{print $2}')
kill -15 $pid
echo "Engine的进程已经停止!!!!"

pid=$(ps -ef | grep "FlinkXExecutorApplication" | grep -v grep | awk '{print $2}')
kill -15 $pid
echo "FlinkXExecutorApplication的进程已经停止!!!!"
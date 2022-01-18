#!/bin/bash

RENT_DIR=$(cd $(dirname $0);cd ..; pwd)
echo "admin starting ..."
sh $RENT_DIR/packages/admin/bin/admin.sh start
echo "executor starting ..."
sh $RENT_DIR/packages/executor/bin/executor.sh start

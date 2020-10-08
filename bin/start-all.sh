#!/bin/bash

RENT_DIR=$(cd $(dirname $0);cd ..; pwd)
echo "flinkx-admin starting ..."
sh $RENT_DIR/packages/flinkx-admin/bin/flinkx-admin.sh start
echo "flinkx-executor starting ..."
sh $RENT_DIR/packages/flinkx-executor/bin/flinkx-executor.sh start
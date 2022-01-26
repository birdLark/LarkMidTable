#!/bin/bash

RENT_DIR=$(cd $(dirname $0);cd ..; pwd)
echo $RENT_DIR
tar -zxvf $RENT_DIR/packages/admin_2.1.2_1.tar.gz  -C $RENT_DIR/packages/
echo "===admin    package finshed===="

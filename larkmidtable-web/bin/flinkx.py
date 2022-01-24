#!/usr/bin/env python
# -*- coding:utf-8 -*-

import sys
import os
import signal
import subprocess
import time
import re
import socket
import json
from optparse import OptionParser
from optparse import OptionGroup
from string import Template
import codecs
import platform

def printCopyright():
    print '''
LarkMidTable (%s), From LarkMidTable !
LarkMidTable All Rights Reserved.

''' 
    sys.stdout.flush()

if __name__ == "__main__":
	printCopyright()
	json_file=sys.argv[1]
	abs_file=sys.path[0]
	startCommand = "java -cp %s/lib/* com.dtstack.flinkx.client.Launcher  -mode local -jobType sync -job %s/job/%s -flinkxDistDir %s/flinkx-dist -flinkConfDir %s/flinkconf"  %(abs_file,abs_file,json_file,abs_file,abs_file)
	child_process = subprocess.Popen(startCommand, shell=True)
	(stdout, stderr) = child_process.communicate()
	
	sys.exit(child_process.returncode)
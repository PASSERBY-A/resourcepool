#!/bin/sh

pid=`ps -ef | grep log4j_cmdb.properties|grep -v grep|awk '{print $2}'`
if [ ${#pid} -ne 0 ]; then
	echo kill cmdb pid:$pid
	kill -9 $pid
else
   echo "cmdb has stopped"
fi
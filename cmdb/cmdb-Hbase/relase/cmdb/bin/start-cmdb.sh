#!/bin/sh

cdir=`pwd`
cd ..
export UIP_HOME=${UIP_HOME:-`pwd`}
echo UIP_HOME=$UIP_HOME
cd $cdir
JRE_HOME=$UIP_HOME/../jdk1.6.0_45
echo JRE_HOME=$JRE_HOME
JAVA_EXEC=$JRE_HOME/bin/java
if [ ! -d $JRE_HOME ]; then JAVA_EXEC=java;fi

JARS=$UIP_HOME/lib/uip-cmdb-hbase.jar
JARS=$JARS:$UIP_HOME/lib/aopalliance.jar
JARS=$JARS:$UIP_HOME/lib/commons-logging.jar
JARS=$JARS:$UIP_HOME/lib/dom4j-1.6.1.jar 
JARS=$JARS:$UIP_HOME/lib/log4j-1.2.14.jar
JARS=$JARS:$UIP_HOME/lib/spring-aop-3.0.3.RELEASE.jar
JARS=$JARS:$UIP_HOME/lib/spring-asm-3.0.3.RELEASE.jar
JARS=$JARS:$UIP_HOME/lib/spring-beans-3.0.3.RELEASE.jar
JARS=$JARS:$UIP_HOME/lib/spring-context-3.0.3.RELEASE.jar
JARS=$JARS:$UIP_HOME/lib/spring-core-3.0.3.RELEASE.jar
JARS=$JARS:$UIP_HOME/lib/spring-expression-3.0.3.RELEASE.jar
JARS=$JARS:$UIP_HOME/lib/spring-jdbc-3.0.3.RELEASE.jar
JARS=$JARS:$UIP_HOME/lib/spring-test-3.0.3.RELEASE.jar
JARS=$JARS:$UIP_HOME/lib/spring-tx-3.0.3.RELEASE.jar
JARS=$JARS:$UIP_HOME/lib/spring-web-3.0.3.RELEASE.jar
JARS=$JARS:$UIP_HOME/lib/spring-webmvc-3.0.0.RELEASE.jar
JARS=$JARS:$UIP_HOME/lib/hadoop-common-2.2.0.jar
JARS=$JARS:$UIP_HOME/lib/hbase-client-0.96.0-hadoop2.jar
JARS=$JARS:$UIP_HOME/lib/hbase-common-0.96.0-hadoop2.jar
JARS=$JARS:$UIP_HOME/lib/guava-12.0.1.jar
JARS=$JARS:$UIP_HOME/lib/protobuf-java-2.5.0.jar  
JARS=$JARS:$UIP_HOME/lib/commons-lang-2.6.jar
JARS=$JARS:$UIP_HOME/lib/commons-configuration-1.6.jar   
JARS=$JARS:$UIP_HOME/lib/hadoop-auth-2.2.0.jar
JARS=$JARS:$UIP_HOME/lib/slf4j-log4j12-1.6.4.jar  
JARS=$JARS:$UIP_HOME/lib/slf4j-api-1.6.4.jar
JARS=$JARS:$UIP_HOME/lib/hbase-protocol-0.96.0-hadoop2.jar
JARS=$JARS:$UIP_HOME/lib/zookeeper-3.4.5.jar 
JARS=$JARS:$UIP_HOME/lib/htrace-core-2.01.jar
JARS=$JARS:$UIP_HOME/lib/netty-3.6.6.Final.jar 
JARS=$JARS:$UIP_HOME/lib/commons-codec-1.7.jar 
JARS=$JARS:$UIP_HOME/lib/jackson-core-asl-1.8.8.jar
JARS=$JARS:$UIP_HOME/lib/jackson-jaxrs-1.8.8.jar   
JARS=$JARS:$UIP_HOME/lib/jackson-mapper-asl-1.8.8.jar
JARS=$JARS:$UIP_HOME/lib/jackson-xc-1.8.8.jar
JARS=$JARS:$UIP_HOME/lib/xstream-1.3.1.jar
JARS=$JARS:$UIP_HOME/lib/commons-beanutils-core-1.8.0.jar
JARS=$JARS:$UIP_HOME/lib/junit-4.8.1.jar
JARS=$JARS:$UIP_HOME/lib/ojdbc14_g.jar

echo $JARS

#main class
MAIN_CLASS=com.hp.xo.uip.cmdb.CMDBMain
#log4j configure key
LOG=log4j_cmdb.properties
pid=`ps -ef | grep log4j_cmdb.properties|grep -v grep|awk '{print $2}'`
if [ ${#pid} -ne 0 ]; then
	echo "Emergency has started"
	exit 0
fi
$JAVA_EXEC  -Xms512M -Xmx1024M -XX:PermSize=128M -XX:MaxPermSize=256M -Dlog4j.configuration=$LOG -classpath "$JARS:$UIP_HOME/conf" -DUIP_HOME=$UIP_HOME -Dfile.encoding=utf8 $MAIN_CLASS %*:
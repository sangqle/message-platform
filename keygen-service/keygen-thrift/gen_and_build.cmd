#!/bin/sh
PRJ_NAME=keygen-thrift

echo ========== Thrift generating 

THRIFT_EXE=xrpcgen-0.9.0

rm -rf $PRJ_NAME/gen-java/*/*/*/*/*/*

$THRIFT_EXE --gen java -o $PRJ_NAME keygen-shared.thrift
$THRIFT_EXE --gen java -o $PRJ_NAME keygen.thrift

#setup JAVA environment
#. /zserver/java/set_env.cmd
#exit; 

echo ========== Jar building

_DEBUG=true
_COMPRESS=true

ant -f $PRJ_NAME/ clean #clean first

_CMD="ant -f $PRJ_NAME jar -Djavac.debug=$_DEBUG -Djar.compress=$_COMPRESS"
$_CMD
echo Done by build command: $_CMD

VERSION_FILE="$PRJ_NAME-`date +%y%m%d`"

cp $PRJ_NAME/dist/$PRJ_NAME.jar jar/$VERSION_FILE.jar

echo create vertion $VERSION_FILE

cp $PRJ_NAME/dist/$PRJ_NAME.jar jar/$PRJ_NAME-latest.jar

echo generate laster done

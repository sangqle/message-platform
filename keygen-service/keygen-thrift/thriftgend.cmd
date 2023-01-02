#!/bin/sh
THRIFT_EXE=xrpcgen-0.9.0
PRJ_NAME=keygen-thrift

echo ========== Thrift generating 
rm -rf /gen/gen-java/*/*/*/*/*/*

$THRIFT_EXE --gen java -o $PRJ_NAME keygen-shared.thrift
$THRIFT_EXE --gen java -o $PRJ_NAME keygen.thrift

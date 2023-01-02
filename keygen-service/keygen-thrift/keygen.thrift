namespace java com.cabin.keygen.thrift
include "keygen-shared.thrift"

service KeyGenService {
    i32 ping();
    string genKey()
}
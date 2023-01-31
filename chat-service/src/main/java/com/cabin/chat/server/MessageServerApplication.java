package com.cabin.chat.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.cassandra.CassandraAutoConfiguration;
import org.springframework.boot.autoconfigure.data.cassandra.CassandraDataAutoConfiguration;

@SpringBootApplication(exclude = { CassandraDataAutoConfiguration.class, CassandraAutoConfiguration.class })
public class MessageServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(MessageServerApplication.class, args);
        System.out.println("Server is running");
    }
}

package com.cabin.chat.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SocketServerApplication{
    public static void main(String[] args) {
        SpringApplication.run(SocketServerApplication.class, args);
        System.out.println("Server is running");
    }
}

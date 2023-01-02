package com.cabin.chat.server;

import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class SocketServerApplication{
    public static void main(String[] args) {
        SpringApplication.run(SocketServerApplication.class, args);
        System.out.println("Server is running");
    }
}

package com.cabin.chat.server.redis.repository;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.web.socket.WebSocketSession;

import java.io.Serializable;

@RedisHash("User")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {

    @Id
    private Long userId;
    private String session;
    private String server;
    private String topic;
    private Long createdAt;
}

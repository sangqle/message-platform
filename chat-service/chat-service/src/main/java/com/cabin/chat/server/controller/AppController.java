package com.cabin.chat.server.controller;

import com.cabin.chat.server.config.WebsocketHandler;
import com.cabin.chat.server.dto.MessageDTO;
import com.cabin.chat.server.redis.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Iterator;

@RestController
public class AppController {
    @Autowired
    WebsocketHandler websocketHandler;

    @Autowired
    UserRepository userRepository;

    @PostMapping("/message")
    public MessageDTO getHello(@RequestBody MessageDTO messageDTO) throws IOException {
        long recipientUserId = messageDTO.getRecipientUserId();
        // save to db
        // sent message to recipientUserId
        websocketHandler.sendMessageToUserId(recipientUserId, messageDTO);

        return  messageDTO;
    }
}

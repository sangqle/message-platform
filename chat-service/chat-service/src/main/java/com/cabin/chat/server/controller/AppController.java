package com.cabin.chat.server.controller;

import com.cabin.chat.server.config.WebsocketHandler;
import com.cabin.chat.server.dto.MessageDTO;
import com.cabin.chat.server.entity.KafkaMessage;
import com.cabin.chat.server.redis.repository.UserRepository;
import com.cabin.chat.server.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
public class AppController {
    @Autowired
    WebsocketHandler websocketHandler;

    @Autowired
    UserRepository userRepository;

    @Autowired
    MessageService messageService;


    @Autowired
    private KafkaTemplate<String, KafkaMessage> kafkaTemplate;

    @PostMapping("/message")
    public MessageDTO postMessage(@RequestBody MessageDTO messageDTO) throws IOException {
        KafkaMessage message = new KafkaMessage();
        message.setMsg(messageDTO.getMessage());
        ListenableFuture<SendResult<String, KafkaMessage>> future =
            kafkaTemplate.send("message-topic", message);

        future.addCallback(new ListenableFutureCallback<SendResult<String, KafkaMessage>>() {

            @Override
            public void onSuccess(SendResult<String, KafkaMessage> result) {
                System.out.println("Sent message=[" + message +
                    "] with offset=[" + result.getRecordMetadata().offset() + "]");
            }
            @Override
            public void onFailure(Throwable ex) {
                System.out.println("Unable to send message=["
                    + message + "] due to : " + ex.getMessage());
            }
        });
        return  messageDTO;
    }
}

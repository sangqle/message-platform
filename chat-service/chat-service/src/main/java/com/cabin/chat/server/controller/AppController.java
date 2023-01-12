package com.cabin.chat.server.controller;

import com.cabin.chat.server.config.WebsocketHandler;
import com.cabin.chat.server.dto.MessageDTO;
import com.cabin.chat.server.entity.KafkaMessage;
import com.cabin.chat.server.redis.repository.User;
import com.cabin.chat.server.service.MessageService;
import com.cabin.chat.server.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Slf4j
@RestController
public class AppController {
    @Autowired
    WebsocketHandler websocketHandler;

    @Autowired
    UserService userService;

    @Autowired
    MessageService messageService;


    @Autowired
    private KafkaTemplate<String, KafkaMessage> kafkaTemplate;

    @PostMapping("/message")
    public MessageDTO postMessage(@RequestBody MessageDTO messageDTO) throws IOException {

        // get userInfo from redis
        User userInfo = userService.getUserInfo(messageDTO.getRecipientUserId());
        if(userInfo == null) {
            System.err.println("Can not get userInfo");
            return null;
        }
        KafkaMessage message = new KafkaMessage();
        message.setMsg(messageDTO.getMessage());
        message.setServer(userInfo.getServer());
        message.setAuthorUserId(messageDTO.getAuthorUserId());
        message.setReplyMessageId(messageDTO.getReplyMessageId());
        message.setRecipientUserId(messageDTO.getRecipientUserId());

        String kafkaTopic = userInfo.getTopic();
        log.info(String.format("Send message to user: %s, topic: %s", userInfo.getUserId(), kafkaTopic));

        ListenableFuture<SendResult<String, KafkaMessage>> future =
            kafkaTemplate.send(kafkaTopic, message);

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

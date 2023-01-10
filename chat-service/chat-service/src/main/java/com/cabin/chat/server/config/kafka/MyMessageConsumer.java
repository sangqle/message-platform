package com.cabin.chat.server.config.kafka;

import com.cabin.chat.server.config.WebsocketHandler;
import com.cabin.chat.server.dto.MessageDTO;
import com.cabin.chat.server.entity.KafkaMessage;
import com.cabin.chat.server.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class MyMessageConsumer {

    @Autowired
    MessageService messageService;

    @KafkaListener(topicPattern = "message-topic", groupId = "message-group")
    public void kafkaMessageListener(KafkaMessage kafkaMessage) throws IOException {
        messageService.sendMessageToClient(kafkaMessage);
    }
}

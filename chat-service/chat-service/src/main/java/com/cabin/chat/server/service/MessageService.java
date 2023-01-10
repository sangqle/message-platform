package com.cabin.chat.server.service;

import com.cabin.chat.server.config.WebsocketHandler;
import com.cabin.chat.server.dto.MessageDTO;
import com.cabin.chat.server.entity.KafkaMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    @Autowired
    WebsocketHandler websocketHandler;

    public void sendMessageToClient(KafkaMessage message)  {
        try {
            MessageDTO messageDto = new MessageDTO();
            messageDto.setMessage(message.getMsg());
            websocketHandler.sendMessageToUserId(276260728L, messageDto);
        } catch (Exception ex) {
            System.err.println(ex);
        }

    }

}

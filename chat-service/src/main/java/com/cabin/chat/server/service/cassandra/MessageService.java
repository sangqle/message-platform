package com.cabin.chat.server.service.cassandra;

import com.cabin.chat.server.entity.cassandra.Message;
import com.cabin.chat.server.repository.cassandra.MessageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Slf4j
@Service
public class MessageService {
    @Autowired
    MessageRepository messageRepository;

    public Flux<Message> getMessages() {
        Flux<Message> all = messageRepository.findAll();
        return all;
    }
}

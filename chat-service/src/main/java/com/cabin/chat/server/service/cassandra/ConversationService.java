package com.cabin.chat.server.service.cassandra;

import com.cabin.chat.server.entity.cassandra.Conversation;
import com.cabin.chat.server.repository.cassandra.ConversationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class ConversationService {
    @Autowired
    ConversationRepository conversationRepository;

    public Conversation saveConversation(Conversation conversation) {
        Mono<Conversation> save = conversationRepository.save(conversation);
        return save.block();
    }
}

package com.cabin.chat.server.controller;

import com.cabin.chat.server.dto.request.CreateConversation;
import com.cabin.chat.server.entity.cassandra.Conversation;
import com.cabin.chat.server.service.cassandra.ConversationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ConversationController {
    @Autowired
    ConversationService conversationService;

    @PostMapping("/conversations")
    public ResponseEntity<Conversation> createConversation(@RequestBody CreateConversation createConversation) {
        List<Long> participantUserIds = createConversation.getParticipantUserIds();
        // Validate participantUserIds;
        Conversation conversation = new Conversation();
        long currentTime = System.currentTimeMillis();
        conversation.setCreatedAt(currentTime);
        conversation.setUpdatedAt(currentTime);
        conversation.setOwnerUserId(createConversation.getOwnerUserId());
        conversation.setTitle(createConversation.getTitle());
        conversation.setParticipantUserIds(participantUserIds);

        Conversation converCreated = conversationService.saveConversation(conversation);
        return ResponseEntity.ok(converCreated);
    }

    @GetMapping("/conversations")
    public ResponseEntity<List<Conversation>> getConversations(@RequestBody CreateConversation createConversation) {
        List<Conversation> conversations = null;
        return ResponseEntity.ok(conversations);
    }
}

package com.cabin.chat.server.entity.cassandra;

import com.cabin.chat.server.dto.MessageSentResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.*;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.UUID;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table("messages")
public class Message {

    @PrimaryKey()
    @Builder.Default
    private String id = UUID.randomUUID().toString();
    private String content;
    private long recipientUserId;
    private long authorUserId;
    private String replyMessageId;
    private long createdAt;
    private long updatedAt;
    private boolean isDeleted;
}

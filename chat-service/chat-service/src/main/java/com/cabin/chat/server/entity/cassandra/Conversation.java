package com.cabin.chat.server.entity.cassandra;

import lombok.*;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.List;
import java.util.UUID;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("conversations")
public class Conversation {
    @PrimaryKey()
    @Builder.Default
    private String id = UUID.randomUUID().toString();
    private String title;
    private long ownerUserId;
    private List<Long> participantUserIds;
    private long createdAt;
    private long updatedAt;
    private boolean isDeleted;

}

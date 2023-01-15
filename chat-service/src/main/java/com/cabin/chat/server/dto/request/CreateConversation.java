package com.cabin.chat.server.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateConversation {
    private String title;
    private long ownerUserId;
    private List<Long> participantUserIds;
}

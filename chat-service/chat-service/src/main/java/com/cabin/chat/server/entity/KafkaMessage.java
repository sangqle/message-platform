package com.cabin.chat.server.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class KafkaMessage {
    private String msg;
    private String server;
    private long recipientUserId;
    private long authorUserId;

    private String replyMessageId;

}

package com.cabin.chat.server.dto;

import com.cabin.chat.server.constant.MessageStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MessageSentResponse {
    private int error;
    private MessageDTO message;
    private MessageStatus status;

    public MessageSentResponse() {
        this.error = -1;
        this.status = MessageStatus.UNSENT;
    }
}

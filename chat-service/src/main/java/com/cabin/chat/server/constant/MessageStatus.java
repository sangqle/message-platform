package com.cabin.chat.server.constant;

public enum MessageStatus {
    UNSENT(-1),
    SENT(0),
    DELIVERED(1),
    SEEN(2);

    private final int value;

    MessageStatus(int i) {
        value = i;
    }

    public int getValue() {
        return value;
    }
}

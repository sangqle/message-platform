package com.cabin.chat.server.stomp;

import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {
    @MessageMapping("/")
    public String getHelloSocket(@Payload String message) {
        System.err.println(message);
        return "Message: " + message;
    }
    @MessageMapping("/message")
    @SendTo("/topic/reply")
    public String replyToMessageFromClient(@Payload String message) { //1
        return String.format("Broadcasting message: %s", message);
    }
    @MessageMapping("/whisper")
    @SendToUser("/queue/reply")
    public String replyToWhisperFromClient(@Payload String message) { //2
        return String.format("Replying to whisper: %s", message);
    }
    @MessageExceptionHandler
    @SendTo("/queue/errors")
    public String handleException(Throwable exception) {              //3
        return exception.getMessage();
    }
}

package com.cabin.chat.server.config;

import com.cabin.chat.server.dto.MessageDTO;
import com.cabin.chat.server.entity.redis.User;
import com.cabin.chat.server.repository.redis.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
public class WebsocketHandler extends TextWebSocketHandler {
    private Map<Long, WebSocketSession> sessions = new ConcurrentHashMap<>();
    private ObjectWriter objectWriter;

    @Autowired
    UserRepository userRepository;

    @Value("${server.websocket.name}")
    String serverAddress;

    @Value("${server.kafka.topic.name}")
    String kafkaTopic;

    public WebsocketHandler(ObjectMapper objectMapper) {
        this.objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
    }

    public long getUserIdFromSession(WebSocketSession session) {
        long userId = 0;
        try {
            HttpHeaders handshakeHeaders = session.getHandshakeHeaders();
            List<String> authorizations = handshakeHeaders.get(HttpHeaders.AUTHORIZATION);
            String bearer = authorizations.get(0);
            String token = bearer.substring(7);
            userId = Long.valueOf(token);
        } catch (Exception ex) {
            log.info(ex.getMessage(), ex);
        }
        return userId;
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable throwable) throws Exception {
        log.error("error occured at sender " + session, throwable);
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("Connected ... " + session.getId());
        HttpHeaders handshakeHeaders = session.getHandshakeHeaders();
        List<String> authorizations = handshakeHeaders.get(HttpHeaders.AUTHORIZATION);

        if(authorizations == null || authorizations.size() < 1) {
            log.info("Close connection: " + session.getId());
            session.close();
            return;
        }

        long userIdFromSession = getUserIdFromSession(session);

        // save current session of user to user mapping service
        User user = new User();
        user.setUserId(userIdFromSession);
        user.setServer(serverAddress);
        user.setTopic(kafkaTopic);
        user.setSession(session.getId());
        userRepository.save(user);
        sessions.put(user.getUserId(), session);
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        int payloadLength = message.getPayloadLength();
        if (message instanceof TextMessage) {
            TextMessage textMessage = (TextMessage) message;
            String payload = textMessage.getPayload();
            JSONObject data = new JSONObject(payload);
            System.err.println(data);
        }
    }


    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        System.err.println("Close connection: " + session.getId());
        long userIdFromSession = getUserIdFromSession(session);
        // remove from user mapping service
        userRepository.deleteById(String.valueOf(userIdFromSession));
    }

    public void sendMessageToUserId(MessageDTO messageDTO) throws IOException {
        /**
         * 1. Save message to db
         * 2. Forward message to kafka
         * 3. Send message to client via websocket
         */
        log.info("Sent message to client: " + messageDTO.toString());
        long userId = messageDTO.getRecipientUserId();
        TextMessage textMessage = new TextMessage(messageDTO.toString());
        WebSocketSession webSocketSession = sessions.get(userId);
        webSocketSession.sendMessage(textMessage);
    }

    public void sendMessageToAll(String message) {
        TextMessage textMessage = new TextMessage(message);
        sessions.forEach((key, value) -> {
            try {
                value.sendMessage(textMessage);
                log.info("Send message {} to socketId: {}", message, key);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}

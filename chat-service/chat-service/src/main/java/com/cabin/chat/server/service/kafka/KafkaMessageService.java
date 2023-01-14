package com.cabin.chat.server.service.kafka;

import com.cabin.chat.server.config.WebsocketHandler;
import com.cabin.chat.server.constant.MessageStatus;
import com.cabin.chat.server.dto.MessageDTO;
import com.cabin.chat.server.entity.KafkaMessage;
import com.cabin.chat.server.redis.repository.User;
import com.cabin.chat.server.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
@Slf4j
public class KafkaMessageService {
    @Autowired
    UserService userService;

    @Autowired
    WebsocketHandler websocketHandler;

    @Autowired
    private KafkaTemplate<String, KafkaMessage> kafkaTemplate;

    public KafkaMessage producerMessage(MessageDTO messageDTO) {
        KafkaMessage rs = null;
        try {
            // get userInfo from redis
            User userInfo = userService.getUserInfo(messageDTO.getRecipientUserId());
            String kafkaTopic = "message-topic";
            if (userInfo != null) {
                kafkaTopic = userInfo.getTopic();
            }

            KafkaMessage kafkaMessage = new KafkaMessage();
            kafkaMessage.setMsg(messageDTO.getMessage());
            kafkaMessage.setAuthorUserId(messageDTO.getAuthorUserId());
            kafkaMessage.setReplyMessageId(messageDTO.getReplyMessageId());
            kafkaMessage.setRecipientUserId(messageDTO.getRecipientUserId());

            log.info(String.format("Send message to user: %s, topic: %s", messageDTO.getRecipientUserId(), kafkaTopic));

            ListenableFuture<SendResult<String, KafkaMessage>> future = kafkaTemplate.send(kafkaTopic, kafkaMessage);
            future.addCallback(new ListenableFutureCallback<SendResult<String, KafkaMessage>>() {
                @Override
                public void onSuccess(SendResult<String, KafkaMessage> result) {
                    kafkaMessage.setStatus(MessageStatus.SENT);
                    System.out.println("Sent message=[" + kafkaMessage + "] with offset=[" + result.getRecordMetadata().offset() + "]");
                }
                @Override
                public void onFailure(Throwable ex) {
                    System.out.println("Unable to send message=[" + kafkaMessage + "] due to : " + ex.getMessage());
                }
            });
            SendResult<String, KafkaMessage> stringKafkaMessageSendResult = future.get();
            ProducerRecord<String, KafkaMessage> producerRecord = stringKafkaMessageSendResult.getProducerRecord();
            rs = producerRecord.value();
        } catch (Throwable ex) {
            log.error(ex.getMessage(), ex);
        }
        return rs;
    }

    public void sendMessageToClient(KafkaMessage message) {
        try {
            MessageDTO messageDto = new MessageDTO();
            messageDto.setMessage(message.getMsg());
            messageDto.setRecipientUserId(message.getRecipientUserId());
            messageDto.setAuthorUserId(message.getAuthorUserId());
            messageDto.setReplyMessageId(message.getReplyMessageId());

            websocketHandler.sendMessageToUserId(messageDto);
        } catch (Exception ex) {
            System.err.println(ex);
        }
    }
}

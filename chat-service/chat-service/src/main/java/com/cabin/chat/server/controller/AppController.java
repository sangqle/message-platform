package com.cabin.chat.server.controller;

import com.cabin.chat.server.dto.MessageDTO;
import com.cabin.chat.server.dto.MessageSentResponse;
import com.cabin.chat.server.entity.KafkaMessage;
import com.cabin.chat.server.entity.cassandra.Message;
import com.cabin.chat.server.entity.cassandra.Post;
import com.cabin.chat.server.repository.cassandra.MessageRepository;
import com.cabin.chat.server.repository.cassandra.PostRepository;
import com.cabin.chat.server.service.kafka.KafkaMessageService;
import com.cabin.chat.server.service.cassandra.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@RestController
public class AppController {

    @Autowired
    KafkaMessageService kafkaMessageService;

    @Autowired
    private KafkaTemplate<String, KafkaMessage> kafkaTemplate;

    @Autowired
    MessageService messageService;

    @Autowired
    PostRepository postRepository;

    @Autowired
    MessageRepository messageRepository;

    @PostMapping("/message")
    public MessageSentResponse postMessage(@RequestBody MessageDTO messageDTO) throws IOException {
        MessageSentResponse response = new MessageSentResponse();
        // get userInfo from redis
        Message message = new Message();
        message.setContent(messageDTO.getMessage());
        message.setReplyMessageId(messageDTO.getReplyMessageId());
        message.setAuthorUserId(messageDTO.getAuthorUserId());
        message.setRecipientUserId(messageDTO.getRecipientUserId());

        Mono<Message> save = messageRepository.save(message);
        Message block = save.block();
        System.err.println(block.toString());

        KafkaMessage kafkaMessage = kafkaMessageService.producerMessage(messageDTO);
        if (kafkaMessage == null) {
            response.setError(-1);
        } else {
            response.setMessage(messageDTO);
            response.setStatus(kafkaMessage.getStatus());
        }
        return response;
    }

    @GetMapping("/messages")
    public Flux<Message> getMessages() {
        return messageService.getMessages();
    }

    @GetMapping("/posts")
    public Flux<Post> all() {
        return postRepository.findAll();
    }
    @PostMapping("/posts")
    public Post createPost() {
        Post post = new Post();
        post.setContent("new Post");
        post.setTitle("title of post");

        Mono<Post> save = postRepository.save(post);
        Optional<Post> post1 = save.blockOptional();
        Post post2 = post1.get();

        return post2;
    }

}

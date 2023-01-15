package com.cabin.chat.server.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class ApplicationConfiguration {

    @Value(value = "${server.kafka.topic.name}")
    private String topicName;


    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        JedisConnectionFactory jedisConFactory
            = new JedisConnectionFactory();
        jedisConFactory.setHostName("localhost");
        jedisConFactory.setPort(6379);
        return jedisConFactory;
    }
    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        return template;
    }
}

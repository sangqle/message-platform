package com.cabin.chat.server.service.redis;

import com.cabin.chat.server.entity.redis.User;
import com.cabin.chat.server.repository.redis.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserMappingService {
    @Autowired
    UserRepository userRepository;

    public User getUserInfo(long userId) {
        User user = null;
        Optional<User> byId = userRepository.findById(String.valueOf(userId));
        if (byId.isPresent()) {
            user = byId.get();
        }
        return user;
    }
}

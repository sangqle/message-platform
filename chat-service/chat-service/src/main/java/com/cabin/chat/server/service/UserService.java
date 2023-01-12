package com.cabin.chat.server.service;

import com.cabin.chat.server.redis.repository.User;
import com.cabin.chat.server.redis.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
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

package com.cabin.chat.server.controller;

import com.cabin.chat.server.service.mysql.UserInfo;
import com.cabin.chat.server.service.mysql.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    UserService userService;
    @GetMapping("/users/{userId}")
    public ResponseEntity<UserInfo> getUserInfo(@PathVariable long userId) {
        UserInfo userInfo = userService.getUserInfo(userId);
        return ResponseEntity.ok(userInfo);
    }
}

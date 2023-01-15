package com.cabin.chat.server.service.mysql;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Sample class userInfo, which will be covered from another service
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {
    private long userId;
    private String userName;
    private String displayName;
    private String avatarUrl;
}

package com.cabin.chat.server.service.mysql;

import org.springframework.stereotype.Service;

@Service
public class UserService {
   public UserInfo getUserInfo(long userId) {
       UserInfo userInfo = null;
        if(userId == 276260728) {
            userInfo = new UserInfo();
            userInfo.setUserId(userId);
            userInfo.setUserName("sangle28");
            userInfo.setDisplayName("sangle 28");
            userInfo.setAvatarUrl("https://image.com/sangle28");
        } else if(userId == 276260729) {
            userInfo = new UserInfo();
            userInfo.setUserId(userId);
            userInfo.setUserName("Sangle 29");
            userInfo.setDisplayName("sangle 29");
            userInfo.setAvatarUrl("https://image.com/sangle29");
        }
        return userInfo;
   }
}

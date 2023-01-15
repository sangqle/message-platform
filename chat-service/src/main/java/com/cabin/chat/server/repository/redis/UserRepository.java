package com.cabin.chat.server.repository.redis;

import com.cabin.chat.server.entity.redis.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, String> {
}

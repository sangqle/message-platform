package com.cabin.chat.server.repository.cassandra;

import com.cabin.chat.server.entity.cassandra.Message;
import com.cabin.chat.server.entity.cassandra.Post;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;

public interface MessageRepository extends ReactiveCassandraRepository<Message, String> {}


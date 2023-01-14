package com.cabin.chat.server.repository.cassandra;

import com.cabin.chat.server.entity.cassandra.Post;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;

public interface PostRepository extends ReactiveCassandraRepository<Post, String> {}

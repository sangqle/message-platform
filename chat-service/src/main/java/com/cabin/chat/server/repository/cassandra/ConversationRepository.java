package com.cabin.chat.server.repository.cassandra;

import com.cabin.chat.server.entity.cassandra.Conversation;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;

public interface ConversationRepository extends ReactiveCassandraRepository<Conversation, String> {}

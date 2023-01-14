package com.cabin.chat.server.entity.cassandra;

import lombok.*;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.UUID;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("posts")
public class Post {

    @PrimaryKey()
    @Builder.Default
    private String id = UUID.randomUUID().toString();
    private String title;
    private String content;

}

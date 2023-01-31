//package com.cabin.chat.server.config.cassandra;
//
//import com.datastax.driver.core.Cluster;
//import com.datastax.driver.core.Session;
//import com.datastax.driver.mapping.*;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.cassandra.config.AbstractReactiveCassandraConfiguration;
//
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//
//import static com.datastax.driver.mapping.NamingConventions.LOWER_CAMEL_CASE;
//import static com.datastax.driver.mapping.NamingConventions.LOWER_SNAKE_CASE;
//
//@Configuration
//public class CassandraConfig {
//
//    @Value("${spring.data.cassandra.contact-points}")
//    String contactPoints;
//
//    @Value("${spring.data.cassandra.keyspace-name}")
//    String keyspace;
//
//    @Bean
//    public Cluster cluster(
//        @Value("${cassandra.host:127.0.0.1}") String host,
//        @Value("${cassandra.cluster.name:cluster}") String clusterName,
//        @Value("${cassandra.port:9042}") int port) {
//        return Cluster.builder()
//            .addContactPoint(host)
//            .withPort(port)
//            .withClusterName(clusterName)
//            .build();
//    }
//
//    @Bean
//    public Session session(Cluster cluster)
//        throws IOException {
//        final Session session = cluster.connect();
//        return session;
//    }
//    @Bean
//    public MappingManager mappingManager(Session session) {
//        final PropertyMapper propertyMapper =
//            new DefaultPropertyMapper()
//                .setNamingStrategy(new DefaultNamingStrategy(LOWER_CAMEL_CASE, LOWER_SNAKE_CASE));
//        final MappingConfiguration configuration =
//            MappingConfiguration.builder().withPropertyMapper(propertyMapper).build();
//        return new MappingManager(session, configuration);
//    }
//}

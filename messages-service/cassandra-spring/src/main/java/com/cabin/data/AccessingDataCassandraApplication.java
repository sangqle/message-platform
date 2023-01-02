package com.cabin.data;

import java.util.Arrays;
import java.util.HashSet;
import java.util.UUID;

import com.cabin.data.contact.UserContact;
import com.cabin.data.contact.UserContactRepository;
import com.cabin.data.vet.Vet;
import com.cabin.data.vet.VetRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AccessingDataCassandraApplication {

    private final static Logger log = LoggerFactory.getLogger(AccessingDataCassandraApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(AccessingDataCassandraApplication.class, args);
    }

    @Bean
    public CommandLineRunner clr(UserContactRepository vetRepository) {
        return args -> {
            vetRepository.deleteAll();

            UserContact userContact = new UserContact();
            long currentTime = System.currentTimeMillis();
            userContact.setId(1L);
            userContact.setUserId(276260728L);
            userContact.setBio("no bio");
            userContact.setCreatedAt(currentTime);
            userContact.setUpdatedAt(currentTime);

            vetRepository.save(userContact);

        };
    }
}

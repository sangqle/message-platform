package com.cabin.data.contact;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface UserContactRepository extends CrudRepository<UserContact, UUID> {
}

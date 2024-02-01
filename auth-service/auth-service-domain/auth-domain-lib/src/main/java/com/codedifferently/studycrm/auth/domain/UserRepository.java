package com.codedifferently.studycrm.auth.domain;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, UUID> {

    User findByUsername(String username);

    boolean existsByUsername(String username);

}
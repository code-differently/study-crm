package com.codedifferently.studycrm.auth.domain;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, String> {

    User findByUsername(String username);

    boolean existsByUsername(String username);

}
package com.codedifferently.studycrm.organizations.domain;

import java.util.UUID;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, UUID> {

  User findByUsername(String username);
}

package com.codedifferently.studycrm.auth.domain;

import java.util.UUID;
import org.springframework.data.repository.CrudRepository;

public interface UserAuthorityRepository extends CrudRepository<UserAuthority, UUID> {}

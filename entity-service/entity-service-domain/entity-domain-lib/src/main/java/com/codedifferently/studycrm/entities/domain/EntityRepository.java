package com.codedifferently.studycrm.entities.domain;

import java.util.UUID;
import org.springframework.data.repository.CrudRepository;

public interface EntityRepository extends CrudRepository<Entity, UUID> {}

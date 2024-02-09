package com.codedifferently.studycrm.entities.domain;

import java.util.UUID;
import org.springframework.data.repository.CrudRepository;

public interface EntityPropertyRepository extends CrudRepository<EntityProperty, UUID> {}

package com.codedifferently.studycrm.entities.domain;

import java.util.UUID;
import org.springframework.data.repository.CrudRepository;

public interface EntityTypeRepository extends CrudRepository<EntityType, UUID> {

  public EntityType findByName(String name);
}

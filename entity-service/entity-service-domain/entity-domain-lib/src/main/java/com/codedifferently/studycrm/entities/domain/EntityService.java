package com.codedifferently.studycrm.entities.domain;

import jakarta.transaction.Transactional;
import java.util.Objects;

public class EntityService {

  private EntityRepository EntityRepository;

  public EntityService(EntityRepository EntityRepository) {
    this.EntityRepository = EntityRepository;
  }

  @Transactional
  public Entity createEntity(String firstName, String lastName) {
    Entity entity = Entity.builder().firstName(firstName).lastName(lastName).build();
    Objects.requireNonNull(entity);
    return EntityRepository.save(entity);
  }
}

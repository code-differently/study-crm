package com.codedifferently.studycrm.entities.domain;

import com.codedifferently.studycrm.common.domain.EntityNotFoundException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EntityService {

  @Autowired private EntityRepository entityRepository;

  @Autowired private EntityTypeRepository entityTypeRepository;

  public Entity createEntity(UUID organizationId, String entityTypeName) {
    EntityType entityType =
        entityTypeRepository
            .findByName(entityTypeName)
            .orElseThrow(
                () -> new EntityNotFoundException("EntityType not found: " + entityTypeName));
    var entity = Entity.builder().organizationId(organizationId).entityType(entityType).build();
    Objects.requireNonNull(entity);
    return entityRepository.save(entity);
  }

  public List<Entity> findAllByEntityType(UUID organizationId, String entityTypeName)
      throws EntityNotFoundException {
    EntityType entityType =
        entityTypeRepository
            .findByName(entityTypeName)
            .orElseThrow(
                () -> new EntityNotFoundException("EntityType not found: " + entityTypeName));
    return entityRepository.findAllByOrganizationIdAndEntityType(organizationId, entityType);
  }

  public Optional<Entity> findById(UUID organizationId, UUID entityId) {
    return entityRepository.findByIdAndOrganizationId(entityId, organizationId);
  }
}

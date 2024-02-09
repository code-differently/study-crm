package com.codedifferently.studycrm.entities.domain;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;

public interface EntityRepository extends CrudRepository<Entity, UUID> {

  public Optional<Entity> findByIdAndOrganizationId(UUID entityId, UUID organizationId);

  public List<Entity> findAllByOrganizationIdAndEntityType(
      UUID organizationId, EntityType entityType);
}

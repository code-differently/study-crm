package com.codedifferently.studycrm.entities.layout.domain;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface LayoutRepository extends CrudRepository<Layout, UUID> {

  Optional<Layout> findByEntityType(String name);

  @Query("SELECT l FROM Layout l WHERE (l.organizationId IS NULL OR l.organizationId = ?1) AND l.entityType = ?2 AND (?3 IS NULL OR l.type IN ?3)")
  List<Layout> findAllByTypesForOrganization(UUID organizationId, String entityType, List<String> types);
}

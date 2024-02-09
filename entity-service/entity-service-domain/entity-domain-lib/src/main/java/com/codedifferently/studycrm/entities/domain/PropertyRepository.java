package com.codedifferently.studycrm.entities.domain;

import java.util.UUID;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface PropertyRepository extends CrudRepository<Property, UUID> {

  @Query("SELECT e FROM Property e WHERE e.organizationId = ?1 OR e.organizationId IS NULL")
  public Iterable<Property> findAllByOrganizationId(UUID organizationId);
}

package com.codedifferently.studycrm.entities.domain;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;

public interface PropertyTypeRepository extends CrudRepository<PropertyType, UUID> {

  Optional<PropertyType> findByName(String name);
}

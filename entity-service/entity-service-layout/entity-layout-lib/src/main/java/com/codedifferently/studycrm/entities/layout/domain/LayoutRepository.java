package com.codedifferently.studycrm.entities.layout.domain;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;

public interface LayoutRepository extends CrudRepository<Layout, UUID> {

  Optional<Layout> findByEntityType(String name);
}

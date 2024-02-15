package com.codedifferently.studycrm.entities.domain;

import java.util.Objects;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PropertyService {

  @Autowired private PropertyRepository propertyRepository;

  public Iterable<Property> getProperties(Iterable<UUID> propertyIds) {
    Objects.requireNonNull(propertyIds, "Property ids cannot be null");
    return propertyRepository.findAllById(propertyIds);
  }
}

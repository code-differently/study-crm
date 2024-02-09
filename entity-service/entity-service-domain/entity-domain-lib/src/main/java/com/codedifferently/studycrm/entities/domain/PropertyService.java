package com.codedifferently.studycrm.entities.domain;

import com.codedifferently.studycrm.common.domain.EntityNotFoundException;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PropertyService {

  @Autowired private EntityTypeRepository entityTypeRepository;

  @Autowired private PropertyRepository propertyRepository;

  @Autowired private PropertyTypeRepository propertyTypeRepository;

  public Property createProperty(Property property, String propertyTypeName) {
    PropertyType propertyType =
        propertyTypeRepository
            .findByName(propertyTypeName)
            .orElseThrow(
                () -> new EntityNotFoundException("PropertyType not found: " + propertyTypeName));
    property.setPropertyType(propertyType);
    return propertyRepository.save(property);
  }

  public Iterable<PropertyGroup> getPropertiesGroupsByEntityType(
      UUID organizationId, String entityTypeName) {
    EntityType entityType =
        entityTypeRepository
            .findByName(entityTypeName)
            .orElseThrow(
                () -> new EntityNotFoundException("EntityType not found: " + entityTypeName));
    return entityType.getPropertyGroups();
  }

  public Iterable<PropertyType> getPropertyTypes() {
    return propertyTypeRepository.findAll();
  }
}

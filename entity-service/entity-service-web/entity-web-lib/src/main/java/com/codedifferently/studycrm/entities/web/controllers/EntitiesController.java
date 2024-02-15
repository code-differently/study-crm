package com.codedifferently.studycrm.entities.web.controllers;

import com.codedifferently.studycrm.entities.api.web.*;
import com.codedifferently.studycrm.entities.domain.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/organizations/{organizationId}/entities")
public class EntitiesController {

  @Autowired private EntityService entityService;

  @RequestMapping(method = RequestMethod.POST)
  public CreateEntityResponse createEntity(
      @PathVariable("organizationId") UUID organizationId,
      @RequestBody CreateEntityRequest createEntityRequest) {
    Entity entity = entityService.createEntity(organizationId, createEntityRequest.getEntityType());
    return new CreateEntityResponse(entity.getId());
  }

  @RequestMapping(method = RequestMethod.GET)
  public ResponseEntity<GetEntitiesResponse> getAll(
      @PathVariable("organizationId") UUID organizationId,
      @RequestParam("type") String entityType,
      @RequestParam(name = "properties", required = false) List<String> properties) {
    var propertyNames =
        new HashSet<String>(properties == null ? Collections.<String>emptyList() : properties);
    List<Entity> entities = entityService.findAllByEntityType(organizationId, entityType);
    return ResponseEntity.ok(
        GetEntitiesResponse.builder()
            .entities(
                StreamSupport.stream(entities.spliterator(), false)
                    .map(c -> getEntityResponse(c, propertyNames))
                    .collect(Collectors.toList()))
            .build());
  }

  @RequestMapping(value = "/{entityId}", method = RequestMethod.GET)
  public ResponseEntity<GetEntityResponse> getEntity(
      @PathVariable("organizationId") UUID organizationId,
      @PathVariable("entityId") UUID entityId,
      @RequestParam(name = "properties", required = false) List<String> properties) {
    var propertyNames =
        new HashSet<String>(properties == null ? Collections.<String>emptyList() : properties);
    return entityService
        .findById(organizationId, entityId)
        .map(c -> new ResponseEntity<>(getEntityResponse(c, propertyNames), HttpStatus.OK))
        .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  private GetEntityResponse getEntityResponse(Entity entity, Set<String> propertyNames) {
    var properties =
        entity.getProperties().stream()
            .filter(
                property ->
                    propertyNames.isEmpty()
                        || propertyNames.contains(property.getProperty().getName()))
            .map(this::getEntityPropertyResponse)
            .collect(Collectors.toList());
    return GetEntityResponse.builder()
        .id(entity.getId())
        .type(entity.getEntityType().getName())
        .properties(properties)
        .createdAt(entity.getCreatedAt())
        .updatedAt(entity.getUpdatedAt())
        .build();
  }

  private EntityPropertyResponse getEntityPropertyResponse(EntityProperty entityProperty) {
    Property property = entityProperty.getProperty();
    return EntityPropertyResponse.builder()
        .name(property.getName())
        .typeName(property.getPropertyType().getName())
        .value(entityProperty.getValue())
        .updatedAt(entityProperty.getUpdatedAt())
        .build();
  }
}

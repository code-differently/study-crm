package com.codedifferently.studycrm.entities.web;

import com.codedifferently.studycrm.entities.api.web.CreateEntityRequest;
import com.codedifferently.studycrm.entities.api.web.CreateEntityResponse;
import com.codedifferently.studycrm.entities.api.web.GetEntityResponse;
import com.codedifferently.studycrm.entities.api.web.GetEntitiesResponse;
import com.codedifferently.studycrm.entities.domain.Entity;
import com.codedifferently.studycrm.entities.domain.EntityRepository;
import com.codedifferently.studycrm.entities.domain.EntityService;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EntitiesController {

  @Autowired private EntityService entityService;

  @Autowired private EntityRepository entityRepository;

  @RequestMapping(value = "/entities", method = RequestMethod.POST)
  public CreateEntityResponse createEntity(
      @RequestBody CreateEntityRequest createEntityRequest) {
    Entity entity =
        entityService.createEntity(
            createEntityRequest.getFirstName(), createEntityRequest.getLastName());
    return new CreateEntityResponse(entity.getId());
  }

  @RequestMapping(value = "/entities", method = RequestMethod.GET)
  public ResponseEntity<GetEntitiesResponse> getAll() {
    return ResponseEntity.ok(
        GetEntitiesResponse.builder()
            .entities(
                StreamSupport.stream(entityRepository.findAll().spliterator(), false)
                    .map(c -> getEntityResponse(c))
                    .collect(Collectors.toList()))
            .build());
  }

  @RequestMapping(value = "/entities/{entityId}", method = RequestMethod.GET)
  public ResponseEntity<GetEntityResponse> getEntity(@PathVariable("entityId") UUID entityId) {
    return entityRepository
        .findById(entityId)
        .map(c -> new ResponseEntity<>(getEntityResponse(c), HttpStatus.OK))
        .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  private GetEntityResponse getEntityResponse(Entity entity) {
    return GetEntityResponse.builder()
        .entityId(entity.getId())
        .firstName(entity.getFirstName())
        .lastName(entity.getLastName())
        .build();
  }
}

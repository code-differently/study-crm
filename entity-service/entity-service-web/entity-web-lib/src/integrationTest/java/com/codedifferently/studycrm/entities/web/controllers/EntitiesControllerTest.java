package com.codedifferently.studycrm.entities.web.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.codedifferently.studycrm.common.domain.EntityNotFoundException;
import com.codedifferently.studycrm.entities.domain.Entity;
import com.codedifferently.studycrm.entities.domain.EntityProperty;
import com.codedifferently.studycrm.entities.domain.EntityService;
import com.codedifferently.studycrm.entities.domain.EntityType;
import com.codedifferently.studycrm.entities.domain.Property;
import com.codedifferently.studycrm.entities.domain.PropertyType;
import com.codedifferently.studycrm.entities.web.TestConfiguration;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@ContextConfiguration(classes = TestConfiguration.class)
class EntitiesControllerTest {

  @Autowired private EntityService entityService;

  private static MockMvc mockMvc;

  @BeforeAll
  static void setUp(WebApplicationContext wac) {
    mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
  }

  @Test
  void testCreateEntity_createsEntity() throws Exception {
    // Arrange
    UUID orgId = UUID.randomUUID();
    UUID entityId = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
    when(entityService.createEntity(orgId, "contact"))
        .thenReturn(Entity.builder().id(entityId).build());

    // Act
    mockMvc
        .perform(
            post("/organizations/{orgId}/entities", orgId)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"entityType\": \"contact\"}"))
        .andExpect(status().isOk())
        .andExpect(content().json("{\"entityId\": \"123e4567-e89b-12d3-a456-426614174000\"}"));
  }

  @Test
  void testGetAll() throws Exception {
    // Arrange
    var orgId = UUID.randomUUID();
    var entityType = EntityType.builder().name("contact").build();
    Entity entity1 = new Entity(entityType, orgId, Collections.<EntityProperty>emptyList());
    entity1.setId(UUID.fromString("123e4567-e89b-12d3-a456-426614174000"));
    Entity entity2 = new Entity(entityType, orgId, Collections.<EntityProperty>emptyList());
    entity2.setId(UUID.fromString("123e4567-e89b-12d3-a456-426614174532"));
    when(entityService.findAllByEntityType(orgId, "contact"))
        .thenReturn(Arrays.asList(entity1, entity2));

    // Act
    mockMvc
        .perform(get("/organizations/{orgId}/entities?type=contact", orgId))
        .andExpect(status().isOk())
        .andExpect(
            content()
                .json(
                    "{"
                        + "\"entities\": ["
                        + "{\"id\": \"123e4567-e89b-12d3-a456-426614174000\", \"type\": \"contact\"},"
                        + "{\"id\": \"123e4567-e89b-12d3-a456-426614174532\", \"type\": \"contact\"}"
                        + "]"
                        + "}"));
  }

  @Test
  void testGetAll_invalidEntityType() throws Exception {
    // Arrange
    var orgId = UUID.randomUUID();
    when(entityService.findAllByEntityType(orgId, "blah"))
        .thenThrow(new EntityNotFoundException("Entity type not found"));

    // Act
    mockMvc
        .perform(get("/organizations/{orgId}/entities?type=blah", orgId))
        .andExpect(status().isNotFound())
        .andExpect(content().json("{\"message\": \"Entity type not found\"}"));
  }

  @Test
  void testGetEntity() throws Exception {
    // Arrange
    var orgId = UUID.randomUUID();
    var entityId = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
    var entityType = EntityType.builder().name("contact").build();
    Entity entity =
        Entity.builder()
            .id(entityId)
            .entityType(entityType)
            .properties(Collections.<EntityProperty>emptyList())
            .build();
    when(entityService.findById(orgId, entityId)).thenReturn(Optional.of(entity));

    // Act
    mockMvc
        .perform(get("/organizations/{orgId}/entities/{id}", orgId, entityId))
        .andExpect(status().isOk())
        .andExpect(
            content()
                .json("{\"id\": \"123e4567-e89b-12d3-a456-426614174000\", \"type\": \"contact\"}"));
  }

  @Test
  void testGetEntity_withFilteredProperties() throws Exception {
    // Arrange
    var orgId = UUID.randomUUID();
    var entityId = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
    var entityType = EntityType.builder().name("contact").build();
    Entity entity =
        Entity.builder()
            .id(entityId)
            .entityType(entityType)
            .properties(
                Arrays.<EntityProperty>asList(
                    EntityProperty.builder()
                        .property(
                            Property.builder()
                                .name("firstName")
                                .propertyType(PropertyType.builder().name("string").build())
                                .build())
                        .value("John")
                        .build(),
                    EntityProperty.builder()
                        .property(
                            Property.builder()
                                .name("lastName")
                                .propertyType(PropertyType.builder().name("string").build())
                                .build())
                        .value("Doe")
                        .build()))
            .build();
    when(entityService.findById(orgId, entityId)).thenReturn(Optional.of(entity));

    // Act
    mockMvc
        .perform(get("/organizations/{orgId}/entities/{id}?properties=firstName", orgId, entityId))
        .andExpect(status().isOk())
        .andExpect(
            content()
                .json(
                    "{\"id\": \"123e4567-e89b-12d3-a456-426614174000\", \"type\": \"contact\", \"properties\": [{\"name\": \"firstName\", \"typeName\": \"string\", \"value\": \"John\"}]}"));
  }

  @Test
  void testGetEntity_notFound() throws Exception {
    // Arrange
    when(entityService.findById(any(), any())).thenReturn(Optional.empty());

    // Act
    mockMvc
        .perform(get("/organizations/{orgId}/entities/{id}", UUID.randomUUID(), UUID.randomUUID()))
        .andExpect(status().isNotFound());
  }
}

package com.codedifferently.studycrm.entities.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.codedifferently.studycrm.entities.domain.Entity;
import com.codedifferently.studycrm.entities.domain.EntityRepository;
import com.codedifferently.studycrm.entities.domain.EntityService;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

class EntitiesControllerTest {

  @Mock private EntityService entityService;

  @Mock private EntityRepository entityRepository;

  @InjectMocks private EntitiesController entitiesController;

  private MockMvc mockMvc;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    mockMvc = MockMvcBuilders.standaloneSetup(entitiesController).build();
  }

  @Test
  void testCreateEntity_createsEntity() throws Exception {
    // Arrange
    UUID entityId = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
    when(entityService.createEntity("John", "Doe"))
        .thenReturn(Entity.builder().id(entityId).firstName("John").lastName("Doe").build());

    // Act
    mockMvc
        .perform(
            post("/entities")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"firstName\": \"John\", \"lastName\": \"Doe\"}"))
        .andExpect(status().isOk())
        .andExpect(content().json("{\"entityId\": \"123e4567-e89b-12d3-a456-426614174000\"}"));
  }

  @Test
  void testGetAll() throws Exception {
    // Arrange
    Entity entity1 = new Entity("John", "Doe");
    entity1.setId(UUID.fromString("123e4567-e89b-12d3-a456-426614174000"));
    Entity entity2 = new Entity("Jane", "Smith");
    entity2.setId(UUID.fromString("123e4567-e89b-12d3-a456-426614174532"));
    when(entityRepository.findAll()).thenReturn(Arrays.asList(entity1, entity2));

    // Act
    mockMvc
        .perform(get("/entities"))
        .andExpect(status().isOk())
        .andExpect(
            content()
                .json(
                    "{"
                        + "\"entities\": ["
                        + "{\"entityId\": \"123e4567-e89b-12d3-a456-426614174000\", \"firstName\": \"John\", \"lastName\": \"Doe\"},"
                        + "{\"entityId\": \"123e4567-e89b-12d3-a456-426614174532\", \"firstName\": \"Jane\", \"lastName\": \"Smith\"}"
                        + "]"
                        + "}"));
  }

  @Test
  void testGetEntity() throws Exception {
    // Arrange
    UUID entityId = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
    Entity entity = Entity.builder().id(entityId).firstName("John").lastName("Doe").build();
    when(entityRepository.findById(entityId)).thenReturn(Optional.of(entity));

    // Act
    mockMvc
        .perform(get("/entities/" + entityId))
        .andExpect(status().isOk())
        .andExpect(
            content()
                .json(
                    "{\"entityId\": \"123e4567-e89b-12d3-a456-426614174000\", \"firstName\": \"John\", \"lastName\": \"Doe\"}"));
  }

  @Test
  void testGetEntity_notFound() throws Exception {
    // Arrange
    when(entityRepository.findById(any())).thenReturn(Optional.empty());

    // Act
    mockMvc
        .perform(get("/entities/123e4567-e89b-12d3-a456-426614174000"))
        .andExpect(status().isNotFound());
  }
}

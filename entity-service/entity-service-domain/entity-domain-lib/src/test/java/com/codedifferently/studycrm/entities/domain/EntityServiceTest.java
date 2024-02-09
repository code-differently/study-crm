package com.codedifferently.studycrm.entities.domain;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import com.codedifferently.studycrm.common.domain.EntityNotFoundException;
import java.util.*;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.mockito.stubbing.Answer;

class EntityServiceTest {

  @Mock private EntityRepository entityRepository;
  @Mock private EntityTypeRepository entityTypeRepository;

  @InjectMocks private EntityService entityService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testCreateEntity_validEntityType_createsEntity() {
    // Arrange
    UUID organizationId = UUID.randomUUID();
    String entityTypeName = "contact";
    EntityType entityType = EntityType.builder().name(entityTypeName).build();
    when(entityTypeRepository.findByName(entityTypeName)).thenReturn(Optional.of(entityType));
    when(entityRepository.save(any(Entity.class)))
        .then(
            (Answer<Entity>)
                invocation -> {
                  Entity entity = invocation.getArgument(0);
                  entity.setId(UUID.randomUUID());
                  return entity;
                });

    // Act
    Entity result = entityService.createEntity(organizationId, entityTypeName);

    // Assert
    assertNotNull(result);
    assertEquals(organizationId, result.getOrganizationId());
    assertEquals(entityType, result.getEntityType());
    verify(entityRepository, times(1)).save(any(Entity.class));
  }

  @Test
  void testCreateEntity_invalidEntityType_throwsException() {
    // Arrange
    UUID organizationId = UUID.randomUUID();
    String entityTypeName = "invalid";
    when(entityTypeRepository.findByName(entityTypeName)).thenReturn(Optional.empty());

    // Act & Assert
    assertThrows(
        EntityNotFoundException.class,
        () -> {
          entityService.createEntity(organizationId, entityTypeName);
        });
  }

  @Test
  void testFindAllByEntityType_validEntityType_returnsEntities() {
    // Arrange
    UUID organizationId = UUID.randomUUID();
    String entityTypeName = "contact";
    EntityType entityType = EntityType.builder().name(entityTypeName).build();
    when(entityTypeRepository.findByName(entityTypeName)).thenReturn(Optional.of(entityType));
    when(entityRepository.findAllByOrganizationIdAndEntityType(organizationId, entityType))
        .thenReturn(Collections.singletonList(new Entity()));

    // Act
    List<Entity> result = entityService.findAllByEntityType(organizationId, entityTypeName);

    // Assert
    assertNotNull(result);
    assertEquals(1, result.size());
    verify(entityRepository, times(1))
        .findAllByOrganizationIdAndEntityType(organizationId, entityType);
  }

  @Test
  void testFindAllByEntityType_invalidEntityType_throwsException() {
    // Arrange
    UUID organizationId = UUID.randomUUID();
    String entityTypeName = "invalid";
    when(entityTypeRepository.findByName(entityTypeName)).thenReturn(Optional.empty());

    // Act & Assert
    assertThrows(
        EntityNotFoundException.class,
        () -> {
          entityService.findAllByEntityType(organizationId, entityTypeName);
        });
  }

  @Test
  void testFindById_existingEntity_returnsOptionalEntity() {
    // Arrange
    UUID organizationId = UUID.randomUUID();
    UUID entityId = UUID.randomUUID();
    when(entityRepository.findByIdAndOrganizationId(entityId, organizationId))
        .thenReturn(Optional.of(new Entity()));

    // Act
    Optional<Entity> result = entityService.findById(organizationId, entityId);

    // Assert
    assertNotNull(result);
    assertTrue(result.isPresent());
    verify(entityRepository, times(1)).findByIdAndOrganizationId(entityId, organizationId);
  }

  @Test
  void testFindById_nonExistingEntity_returnsEmptyOptional() {
    // Arrange
    UUID organizationId = UUID.randomUUID();
    UUID entityId = UUID.randomUUID();
    when(entityRepository.findByIdAndOrganizationId(entityId, organizationId))
        .thenReturn(Optional.empty());

    // Act
    Optional<Entity> result = entityService.findById(organizationId, entityId);

    // Assert
    assertNotNull(result);
    assertFalse(result.isPresent());
    verify(entityRepository, times(1)).findByIdAndOrganizationId(entityId, organizationId);
  }
}

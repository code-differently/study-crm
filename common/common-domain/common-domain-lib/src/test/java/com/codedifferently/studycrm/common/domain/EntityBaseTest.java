package com.codedifferently.studycrm.common.domain;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class EntityBaseTest {

  @Test
  void buildsEmptyEntity() {
    // Act
    var entity = EntityBaseImpl.builder().build();

    // Assert
    assertEquals(null, entity.getId());
    assertEquals(0, entity.getVersion());
    assertEquals(null, entity.getCreatedAt());
    assertEquals(null, entity.getCreatedBy());
    assertEquals(null, entity.getUpdatedAt());
    assertEquals(null, entity.getUpdatedBy());
  }

  @Test
  void buildsAllFields() {
    // Arrange
    var id = UUID.randomUUID();
    var date = new Date();

    // Act
    var entity =
        EntityBaseImpl.builder()
            .id(id)
            .version(1)
            .createdAt(date)
            .createdBy("me")
            .updatedAt(date)
            .updatedBy("someone")
            .updatedAt(date)
            .build();

    // Assert
    assertEquals(id, entity.getId());
    assertEquals(1, entity.getVersion());
    assertEquals(date, entity.getCreatedAt());
    assertEquals("me", entity.getCreatedBy());
    assertEquals(date, entity.getUpdatedAt());
    assertEquals("someone", entity.getUpdatedBy());
  }

  @Test
  void createsEmptyEntity() {
    // Act
    var entity = new EntityBaseImpl();

    // Assert
    assertEquals(null, entity.getId());
    assertEquals(0, entity.getVersion());
    assertEquals(null, entity.getCreatedAt());
    assertEquals(null, entity.getCreatedBy());
    assertEquals(null, entity.getUpdatedAt());
    assertEquals(null, entity.getUpdatedBy());
  }

  @Test
  void createsEntityWithFields() {
    // Arrange
    var id = UUID.randomUUID();
    var date = new Date();

    // Act
    var entity = new EntityBaseImpl();
    entity.setId(id);
    entity.setVersion(1);
    entity.setCreatedAt(date);
    entity.setCreatedBy("me");
    entity.setUpdatedAt(date);
    entity.setUpdatedBy("someone");
    entity.setUpdatedAt(date);

    // Assert
    assertEquals(id, entity.getId());
    assertEquals(1, entity.getVersion());
    assertEquals(date, entity.getCreatedAt());
    assertEquals("me", entity.getCreatedBy());
    assertEquals(date, entity.getUpdatedAt());
    assertEquals("someone", entity.getUpdatedBy());
  }

  @Test
  void createsEntityWithConstructor() {
    // Arrange
    var id = UUID.randomUUID();
    var date = new Date();

    // Act
    var entity = new EntityBaseImpl(id, 1, date, "me", date, "someone");

    // Assert
    assertEquals(id, entity.getId());
    assertEquals(1, entity.getVersion());
    assertEquals(date, entity.getCreatedAt());
    assertEquals("me", entity.getCreatedBy());
    assertEquals(date, entity.getUpdatedAt());
    assertEquals("someone", entity.getUpdatedBy());
  }

  @Test
  void assertEqualsInstances() {
    // Arrange
    var id = UUID.randomUUID();
    var date = new Date();

    // Act
    var entity1 =
        EntityBaseImpl.builder()
            .id(id)
            .version(1)
            .createdAt(date)
            .createdBy("me")
            .updatedAt(date)
            .updatedBy("someone")
            .updatedAt(date)
            .build();

    var entity2 =
        EntityBaseImpl.builder()
            .id(id)
            .version(1)
            .createdAt(date)
            .createdBy("me")
            .updatedAt(date)
            .updatedBy("someone")
            .updatedAt(date)
            .build();

    // Assert
    assertEquals(entity1, entity2);
    assertEquals(entity1.hashCode(), entity2.hashCode());
  }

  @Test
  void assertUnequalsInstances() {
    // Act
    var entity1 = EntityBaseImpl.builder().id(UUID.randomUUID()).build();

    var entity2 = EntityBaseImpl.builder().id(UUID.randomUUID()).build();

    // Assert
    assertNotEquals(entity1, entity2);
    assertNotEquals(entity1.hashCode(), entity2.hashCode());
  }
}

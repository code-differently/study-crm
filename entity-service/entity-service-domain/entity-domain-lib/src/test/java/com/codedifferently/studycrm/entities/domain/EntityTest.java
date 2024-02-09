package com.codedifferently.studycrm.entities.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class EntityTest {
  @Test
  void testGetId() {
    Entity entity = Entity.builder().build();
    assertNull(entity.getId());
  }
}

package com.codedifferently.studycrm.auth.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;

@DataJpaTest
@ContextConfiguration(classes = AuthDomainConfiguration.class)
class UserTest {

  @Autowired private TestEntityManager entityManager;

  @Test
  void someLibraryMethodReturnsTrue() {
    User classUnderTest = new User();
    assertTrue(classUnderTest.isAccountEnabled(), "getIsAccountEnabled should return 'true'");
  }

  @Test
  void userPersists() {
    // Arrange
    var user = User.builder().username("user").email("email").password("password").build();

    // Act
    var persistedUser = entityManager.persistFlushFind(user);

    // Assert
    assertNotNull(persistedUser.getId());
    assertNotNull(persistedUser.getCreatedAt());
    assertNotNull(persistedUser.getUpdatedAt());
    assertEquals(user.getUsername(), persistedUser.getUsername());
    assertEquals(user.getEmail(), persistedUser.getEmail());
    assertEquals(user.getPassword(), persistedUser.getPassword());
  }
}

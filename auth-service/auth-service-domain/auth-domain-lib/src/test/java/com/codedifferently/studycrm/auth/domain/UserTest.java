package com.codedifferently.studycrm.auth.domain;

import static org.junit.jupiter.api.Assertions.*;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;

@DataJpaTest
@ContextConfiguration(classes = TestConfiguration.class)
class UserTest {

  @Autowired private TestEntityManager entityManager;
  private static Validator validator;

  @BeforeAll
  public static void setup() {
    final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  @Test
  void buildsUser() {
    // Act
    var user =
        User.builder()
            .username("john.doe@example.com")
            .email("dummy@example.com")
            .password("somePassword")
            .firstName("John")
            .lastName("Doe")
            .build();

    // Assert
    assertEquals("john.doe@example.com", user.getUsername());
    assertEquals("dummy@example.com", user.getEmail());
    assertEquals("somePassword", user.getPassword());
    assertEquals("John", user.getFirstName());
    assertEquals("Doe", user.getLastName());
  }

  @Test
  void validatesUser() {
    // Arrange
    var user = new User();

    // Act
    var violations = validator.validate(user);

    // Assert
    assertEquals(2, violations.size());
    var iterator = violations.iterator();
    var violation1 = iterator.next();
    assertEquals("email", violation1.getPropertyPath().toString());
    assertEquals("Email is required", violation1.getMessage());
    var violation2 = iterator.next();
    assertEquals("username", violation2.getPropertyPath().toString());
    assertEquals("Username is required", violation2.getMessage());
  }

  @Test
  void comparesLikeUser() {
    // Arrange
    var userDetails1 = new User();
    userDetails1.setUsername("john.doe@example.com");
    userDetails1.setEmail("dummy@example.com");
    userDetails1.setPassword("somePassword");
    userDetails1.setFirstName("John");
    userDetails1.setLastName("Doe");
    var userDetails2 = new User();
    userDetails2.setUsername("john.doe@example.com");
    userDetails2.setEmail("dummy@example.com");
    userDetails2.setPassword("somePassword");
    userDetails2.setFirstName("John");
    userDetails2.setLastName("Doe");

    // Assert
    assertTrue(userDetails1.equals(userDetails2), "The two objects should be equal");
    assertEquals(
        userDetails1.hashCode(),
        userDetails2.hashCode(),
        "The two object hash codes should be equal");
  }

  @Test
  void comparesUnlikeUser() {
    // Arrange
    var userDetails1 =
        User.builder().username("john.doe1@example.com").email("john.doe1@example.com").build();
    var userDetails2 =
        User.builder().username("john.doe2@example.com").email("john.doe2@example.com").build();

    // Assert
    assertFalse(userDetails1.equals(userDetails2), "The two objects should not be equal");
    assertNotEquals(
        userDetails1.hashCode(),
        userDetails2.hashCode(),
        "The two object hash codes should not be equal");
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

  @Test
  void throwsSavingDuplicateNewUser() {
    // Arrange
    var user1 = User.builder().username("user").email("email").password("password").build();
    var user2 = User.builder().username("user").email("email").password("password").build();

    // Act
    entityManager.persistFlushFind(user1);
    assertThrows(
        Exception.class,
        () -> {
          entityManager.persistFlushFind(user2);
        });
  }
}

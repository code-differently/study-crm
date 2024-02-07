package com.codedifferently.studycrm.auth.domain;

import static org.junit.jupiter.api.Assertions.*;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;

@DataJpaTest
@ContextConfiguration(classes = AuthDomainConfiguration.class)
class UserTest {

  @Autowired private TestEntityManager entityManager;
  private static Validator validator;

  @BeforeAll
  public static void setup() {
    final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  @Test
  void testUserIsBuildable() {
    // Act
    var user =
        User.builder()
            .username("john.doe@example.com")
            .email("dummy@example.com")
            .password("somePassword")
            .firstName("John")
            .lastName("Doe")
            .authorities(new ArrayList<UserAuthority>())
            .isAccountEnabled(true)
            .isAccountLocked(false)
            .clearAuthorities()
            .build();

    // Assert
    assertEquals("john.doe@example.com", user.getUsername());
    assertEquals("dummy@example.com", user.getEmail());
    assertEquals("somePassword", user.getPassword());
    assertEquals("John", user.getFirstName());
    assertEquals("Doe", user.getLastName());
  }

  @Test
  void testUserValdation() {
    // Arrange
    var user = new User();

    // Act
    var violations = validator.validate(user);

    // Assert
    var violationMap =
        violations.stream()
            .collect(
                Collectors.toMap(
                    v -> v.getPropertyPath().toString(), v -> v.getMessage().toString()));
    assertEquals(2, violations.size());
    assertEquals("Email is required", violationMap.get("email"));
    assertEquals("Username is required", violationMap.get("username"));
  }

  @Test
  void testUserEqualsAndHashCode() {
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
  void testUserNotEqualsAndHashCode() {
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
  void testUserPersists() {
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
  void testUserNotDuplicatedOnSave() {
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

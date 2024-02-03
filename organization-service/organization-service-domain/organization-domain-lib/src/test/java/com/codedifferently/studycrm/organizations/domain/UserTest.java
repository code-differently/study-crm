package com.codedifferently.studycrm.organizations.domain;

import static org.junit.jupiter.api.Assertions.*;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.UUID;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;

@DataJpaTest
@ContextConfiguration(classes = OrganizationDomainConfiguration.class)
class UserTest {

  @Autowired private TestEntityManager entityManager;
  private static Validator validator;

  @BeforeAll
  public static void setup() {
    final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  @Test
  void testUser_isBuildable() {
    // Act
    var organizationId = UUID.randomUUID();
    var user =
        User.builder()
            .username("john.doe@example.com")
            .email("dummy@example.com")
            .firstName("John")
            .lastName("Doe")
            .defaultOrganizationId(organizationId)
            .build();

    // Assert
    assertEquals("john.doe@example.com", user.getUsername());
    assertEquals("dummy@example.com", user.getEmail());
    assertEquals("John", user.getFirstName());
    assertEquals("Doe", user.getLastName());
    assertEquals(organizationId, user.getDefaultOrganizationId());
  }

  @Test
  void testUser_valdation() {
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
  void testUser_equalsAndHashCode() {
    // Arrange
    var organizationId = UUID.randomUUID();
    var userDetails1 = new User();
    userDetails1.setUsername("john.doe@example.com");
    userDetails1.setEmail("dummy@example.com");
    userDetails1.setFirstName("John");
    userDetails1.setLastName("Doe");
    userDetails1.setDefaultOrganizationId(organizationId);
    var userDetails2 = new User();
    userDetails2.setUsername("john.doe@example.com");
    userDetails2.setEmail("dummy@example.com");
    userDetails2.setFirstName("John");
    userDetails2.setLastName("Doe");
    userDetails2.setDefaultOrganizationId(organizationId);

    // Assert
    assertTrue(userDetails1.equals(userDetails2), "The two objects should be equal");
    assertEquals(
        userDetails1.hashCode(),
        userDetails2.hashCode(),
        "The two object hash codes should be equal");
  }

  @Test
  void testUser_notEqualsAndHashCode() {
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
  void testUser_persists() {
    // Arrange
    var user = User.builder().username("user").email("email").firstName("").lastName("").build();

    // Act
    var persistedUser = entityManager.persistFlushFind(user);

    // Assert
    assertNotNull(persistedUser.getId());
    assertNotNull(persistedUser.getCreatedAt());
    assertNotNull(persistedUser.getUpdatedAt());
    assertEquals(user.getUsername(), persistedUser.getUsername());
    assertEquals(user.getEmail(), persistedUser.getEmail());
  }

  @Test
  void testUser_notDuplicatedOnSave() {
    // Arrange
    var user1 = User.builder().username("user").email("email").firstName("").lastName("").build();
    var user2 = User.builder().username("user").email("email").firstName("").lastName("").build();

    // Act
    entityManager.persistFlushFind(user1);
    assertThrows(
        Exception.class,
        () -> {
          entityManager.persistFlushFind(user2);
        });
  }
}

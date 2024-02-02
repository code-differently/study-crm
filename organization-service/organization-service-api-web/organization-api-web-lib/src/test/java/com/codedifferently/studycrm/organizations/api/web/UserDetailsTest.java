package com.codedifferently.studycrm.organizations.api.web;

import static org.junit.jupiter.api.Assertions.*;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class UserDetailsTest {

  private static Validator validator;

  @BeforeAll
  public static void setup() {
    final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  @Test
  void buildsUserDetails() {
    // Act
    var user =
        UserDetails.builder()
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
  void validatesUserDetails() {
    // Arrange
    var user = new UserDetails();

    // Act
    var violations = validator.validate(user);

    // Assert
    assertEquals(2, violations.size());
    var iterator = violations.iterator();
    var violation1 = iterator.next();
    assertEquals("username", violation1.getPropertyPath().toString());
    assertEquals("Username is required", violation1.getMessage());
    var violation2 = iterator.next();
    assertEquals("email", violation2.getPropertyPath().toString());
    assertEquals("Email is required", violation2.getMessage());
  }

  @Test
  void comparesLikeUserDetails() {
    // Arrange
    var userDetails1 = new UserDetails();
    userDetails1.setUsername("john.doe@example.com");
    userDetails1.setEmail("dummy@example.com");
    userDetails1.setPassword("somePassword");
    userDetails1.setFirstName("John");
    userDetails1.setLastName("Doe");
    var userDetails2 = new UserDetails();
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
  void comparesUnlikeUserDetails() {
    // Arrange
    var userDetails1 =
        UserDetails.builder()
            .username("john.doe1@example.com")
            .email("john.doe1@example.com")
            .build();
    var userDetails2 =
        UserDetails.builder()
            .username("john.doe2@example.com")
            .email("john.doe2@example.com")
            .build();

    // Assert
    assertFalse(userDetails1.equals(userDetails2), "The two objects should not be equal");
    assertNotEquals(
        userDetails1.hashCode(),
        userDetails2.hashCode(),
        "The two object hash codes should not be equal");
  }
}

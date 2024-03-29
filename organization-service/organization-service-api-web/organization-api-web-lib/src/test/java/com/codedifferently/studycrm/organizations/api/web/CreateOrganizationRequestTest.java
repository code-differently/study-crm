package com.codedifferently.studycrm.organizations.api.web;

import static org.junit.jupiter.api.Assertions.*;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class CreateOrganizationRequestTest {

  private static Validator validator;

  @BeforeAll
  public static void setup() {
    final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  @Test
  void buildsRequest() {
    // Act
    var organization =
        CreateOrganizationRequest.builder()
            .organizationName("My Organization Inc.")
            .userDetails(
                UserDetails.builder()
                    .username("john.doe@example.com")
                    .email("dummy@example.com")
                    .password("somePassword")
                    .firstName("John")
                    .lastName("Doe")
                    .build())
            .build();

    // Assert
    assertEquals("My Organization Inc.", organization.getOrganizationName());
    assertNotNull(organization.getUserDetails());
    assertEquals("john.doe@example.com", organization.getUserDetails().getUsername());
    assertEquals("dummy@example.com", organization.getUserDetails().getEmail());
    assertEquals("somePassword", organization.getUserDetails().getPassword());
    assertEquals("John", organization.getUserDetails().getFirstName());
    assertEquals("Doe", organization.getUserDetails().getLastName());
  }

  @Test
  void validatesRequest() {
    // Arrange
    var organization = new CreateOrganizationRequest();

    // Act
    var violations = validator.validate(organization);

    // Assert
    var violationMap =
        violations.stream()
            .collect(
                Collectors.toMap(
                    v -> v.getPropertyPath().toString(), v -> v.getMessage().toString()));
    assertEquals(2, violations.size());
    assertEquals("Organization name is required", violationMap.get("organizationName"));
    assertEquals("User details are required", violationMap.get("userDetails"));
  }

  @Test
  void comparesLikeRequests() {
    // Arrange
    var userDetails =
        UserDetails.builder()
            .username("john.doe@example.com")
            .email("dummy@example.com")
            .password("somePassword")
            .firstName("John")
            .lastName("Doe")
            .build();
    var organization1 = new CreateOrganizationRequest();
    organization1.setOrganizationName("My Organization Inc.");
    organization1.setUserDetails(userDetails);
    var organization2 = new CreateOrganizationRequest();
    organization2.setOrganizationName("My Organization Inc.");
    organization2.setUserDetails(userDetails);

    // Assert
    assertTrue(organization1.equals(organization2), "The two requests should be equal");
    assertEquals(
        organization1.hashCode(),
        organization2.hashCode(),
        "The two requests hash codes should be equal");
  }

  @Test
  void comparesUnlikeRequests() {
    // Arrange
    var userDetails =
        UserDetails.builder()
            .username("john.doe@example.com")
            .email("dummy@example.com")
            .password("somePassword")
            .firstName("John")
            .lastName("Doe")
            .build();
    var organization1 = new CreateOrganizationRequest();
    organization1.setOrganizationName("My Organization 1");
    organization1.setUserDetails(userDetails);
    var organization2 = new CreateOrganizationRequest();
    organization2.setOrganizationName("My Organization 2");
    organization2.setUserDetails(userDetails);

    // Assert
    assertFalse(organization1.equals(organization2), "The two requests should not be equal");
    assertNotEquals(
        organization1.hashCode(),
        organization2.hashCode(),
        "The two requests hash codes should not be equal");
  }

  @Test
  void testCreateOrganizationRequest_toString() {
    // Arrange
    var userDetails =
        UserDetails.builder()
            .username("john.doe@example.com")
            .email("dummy@example.com")
            .password("somePassword")
            .firstName("John")
            .lastName("Doe")
            .build();
    var organization =
        CreateOrganizationRequest.builder()
            .organizationName("My Organization Inc.")
            .userDetails(userDetails)
            .build();

    // Act
    var toString = organization.toString();

    // Assert
    assertNotNull(toString);
    assertTrue(toString.contains("organizationName=My Organization Inc."));
    assertTrue(
        toString.contains(
            "userDetails=UserDetails(username=john.doe@example.com, email=dummy@example.com, password=somePassword, firstName=John, lastName=Doe)"));
  }
}

package com.codedifferently.studycrm.organizations.domain;

import static org.junit.jupiter.api.Assertions.*;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;

@DataJpaTest
@ContextConfiguration(classes = OrganizationDomainConfiguration.class)
class OrganizationTest {

  @Autowired private TestEntityManager entityManager;
  private static Validator validator;

  @BeforeAll
  public static void setup() {
    final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  @Test
  void testOrganization_isBuildable() {
    // Act
    var organization = Organization.builder().name("The Company Corp.").build();

    // Assert
    assertEquals("The Company Corp.", organization.getName());
    assertEquals(false, organization.isActive());
  }

  @Test
  void testOrganization_valdation() {
    // Arrange
    var organization = new Organization();

    // Act
    var violations = validator.validate(organization);

    // Assert
    var violationMap =
        violations.stream()
            .collect(
                Collectors.toMap(
                    v -> v.getPropertyPath().toString(), v -> v.getMessage().toString()));
    assertEquals(1, violations.size());
    assertEquals("Name is required", violationMap.get("name"));
  }

  @Test
  void testOrganization_equalsAndHashCode() {
    // Arrange
    var organization1 = Organization.builder().name("Company name here").build();
    var organization2 = Organization.builder().name("Company name here").build();

    // Assert
    assertTrue(organization1.equals(organization2), "The two objects should be equal");
    assertEquals(
        organization1.hashCode(),
        organization2.hashCode(),
        "The two object hash codes should be equal");
  }

  @Test
  void testOrganization_notEqualsAndHashCode() {
    // Arrange
    var organization1 = Organization.builder().name("1 Company").build();
    var organization2 = Organization.builder().name("Company 2").build();

    // Assert
    assertFalse(organization1.equals(organization2), "The two objects should not be equal");
    assertNotEquals(
        organization1.hashCode(),
        organization2.hashCode(),
        "The two object hash codes should not be equal");
  }

  @Test
  void testOrganization_persists() {
    // Arrange
    var organization = Organization.builder().name("My Company LLC").build();

    // Act
    var persistedOrganization = entityManager.persistFlushFind(organization);

    // Assert
    assertEquals(organization.getName(), persistedOrganization.getName());
  }
}

package com.codedifferently.studycrm.auth.domain;

import static org.junit.jupiter.api.Assertions.*;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.Arrays;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;

@DataJpaTest
@ContextConfiguration(classes = AuthDomainConfiguration.class)
class UserAuthorityTest {

  @Autowired private TestEntityManager entityManager;
  private static Validator validator;

  @BeforeAll
  public static void setup() {
    final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  @Test
  void testUserAuthorityBuilder() {
    // Arrange
    var userAuthority = UserAuthority.builder().authority("ROLE_ADMIN").build();

    // Assert
    assertEquals("ROLE_ADMIN", userAuthority.getAuthority());
  }

  @Test
  void testUserAuthorityEqualsAndHashCode() {
    // Arrange
    var userAuthority1 = UserAuthority.builder().authority("ROLE_ADMIN").build();
    var userAuthority2 = UserAuthority.builder().authority("ROLE_ADMIN").build();

    // Assert
    assertTrue(userAuthority1.equals(userAuthority2), "The two objects should be equal");
    assertEquals(
        userAuthority1.hashCode(),
        userAuthority2.hashCode(),
        "The two object hash codes should be equal");
  }

  @Test
  void testUserAuthorityNotEqualsAndHashCode() {
    // Arrange
    var userAuthority1 = UserAuthority.builder().authority("ROLE_ADMIN").build();
    var userAuthority2 = UserAuthority.builder().authority("ROLE_USER").build();

    // Assert
    assertFalse(userAuthority1.equals(userAuthority2), "The two objects should not be equal");
    assertNotEquals(
        userAuthority1.hashCode(),
        userAuthority2.hashCode(),
        "The two object hash codes should not be equal");
  }

  @Test
  void testUserAuthorityValidation() {
    // Arrange
    var userAuthority = UserAuthority.builder().build();

    // Act
    var violations = validator.validate(userAuthority);

    // Assert
    var violationMap =
        violations.stream()
            .collect(
                Collectors.toMap(
                    v -> v.getPropertyPath().toString(), v -> v.getMessage().toString()));
    assertEquals(1, violations.size());
    assertEquals("Authority is required", violationMap.get("authority"));
  }

  @Test
  void testUserAuthorityThrowsOnEmptyAuthority() {
    // Arrange
    var user = User.builder().username("user").email("email").password("password").build();
    var userAuthority = UserAuthority.builder().user(user).build();
    user.setAuthorities(Arrays.asList(userAuthority));

    // Act
    var exception =
        assertThrows(
            ConstraintViolationException.class, () -> entityManager.persistFlushFind(user));
    var violationMap =
        exception.getConstraintViolations().stream()
            .collect(
                Collectors.toMap(
                    v -> v.getPropertyPath().toString(), v -> v.getMessage().toString()));
    assertEquals("Authority is required", violationMap.get("authority"));
  }
}

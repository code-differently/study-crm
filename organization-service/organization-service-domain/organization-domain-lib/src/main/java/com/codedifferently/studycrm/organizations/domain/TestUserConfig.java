package com.codedifferently.studycrm.organizations.domain;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class TestUserConfig implements CommandLineRunner {

  static final String TEST_USER_EMAIL = "testuser@studycrm.com";
  static final String TEST_USER_FIRST_NAME = "Test";
  static final String TEST_USER_LAST_NAME = "User";

  @Autowired private UserRepository repository;

  @Override
  public void run(String... args) throws Exception {
    User user = repository.findByUsername(TEST_USER_EMAIL).orElse(null);

    if (user != null) {
      return;
    }

    var newUser =
        User.builder()
            .username(TEST_USER_EMAIL)
            .email(TEST_USER_EMAIL)
            .firstName(TEST_USER_FIRST_NAME)
            .lastName(TEST_USER_LAST_NAME)
            .defaultOrganizationId(UUID.fromString("123641f5-bdfd-4f27-915c-cc0b3c12a57a"))
            .build();

    repository.save(newUser);
  }
}

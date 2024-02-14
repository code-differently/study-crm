package com.codedifferently.studycrm.auth.domain;

import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ExampleUserConfig implements CommandLineRunner {

  static final String TEST_USER_EMAIL = "testuser@studycrm.com";
  static final String TEST_USER_PASSWORD =
      "{bcrypt}$2a$10$dTa5JIMbEKIYs3F06yqqvOqmrT3PHXQnl2j1ccKCv9B2mDadQeZ22";
  static final String TEST_USER_FIRST_NAME = "Test";
  static final String TEST_USER_LAST_NAME = "User";
  static final String TEST_USER_AUTHORITY = "org:123641f5-bdfd-4f27-915c-cc0b3c12a57a:admin";

  @Autowired private UserRepository repository;

  @Override
  public void run(String... args) throws Exception {
    User user = repository.findByUsername(TEST_USER_EMAIL);

    if (user != null) {
      return;
    }

    var newUser =
        User.builder()
            .username(TEST_USER_EMAIL)
            .password(TEST_USER_PASSWORD)
            .email(TEST_USER_EMAIL)
            .firstName(TEST_USER_FIRST_NAME)
            .lastName(TEST_USER_LAST_NAME)
            .build();
    var authorities =
        Arrays.<UserAuthority>asList(
            UserAuthority.builder().authority(TEST_USER_AUTHORITY).user(newUser).build());
    newUser.setAuthorities(authorities);

    repository.save(newUser);
  }
}

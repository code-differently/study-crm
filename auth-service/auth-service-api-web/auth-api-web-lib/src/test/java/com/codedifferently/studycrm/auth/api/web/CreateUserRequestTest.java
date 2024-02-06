package com.codedifferently.studycrm.auth.api.web;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CreateUserRequestTest {
  @Test
  void createUserRequest_builds() {
    CreateUserRequest classUnderTest =
        CreateUserRequest.builder().username("someUsername").password("test123").build();
    assertEquals("someUsername", classUnderTest.getUsername());
    assertEquals("test123", classUnderTest.getPassword());
  }
}

package com.codedifferently.studycrm.auth.api.web;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CreateUserRequestTest {
  @Test
  void someLibraryMethodReturnsTrue() {
    CreateUserRequest classUnderTest = CreateUserRequest.builder().username("someUsername").build();
    assertEquals("someUsername", classUnderTest.getUsername(), "Username should return match");
  }
}

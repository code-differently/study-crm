package com.codedifferently.studycrm.auth.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class UserTest {
  @Test
  void someLibraryMethodReturnsTrue() {
    User classUnderTest = new User();
    assertTrue(classUnderTest.isAccountEnabled(), "getIsAccountEnabled should return 'true'");
  }
}

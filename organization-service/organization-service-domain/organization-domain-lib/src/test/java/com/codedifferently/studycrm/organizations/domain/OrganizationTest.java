package com.codedifferently.studycrm.organizations.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class OrganizationTest {
  @Test
  void testGetId() {
    Organization organization = Organization.builder().name("Morgan Latimer LLC").build();
    assertNull(organization.getId());
  }

  @Test
  void testGetFirstName() {
    Organization organization = Organization.builder().name("Morgan Latimer LLC").build();
    assertEquals("Morgan Latimer LLC", organization.getName());
  }
}

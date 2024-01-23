package com.codedifferently.studycrm.organizations.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class OrganizationTest {
    @Test
    void testGetId() {
        Organization organization = new Organization("John", "Doe");
        assertNull(organization.getId());
    }

    @Test
    void testGetFirstName() {
        Organization organization = new Organization("John", "Doe");
        assertEquals("John", organization.getFirstName());
    }

    @Test
    void testGetLastName() {
        Organization organization = new Organization("John", "Doe");
        assertEquals("Doe", organization.getLastName());
    }
}
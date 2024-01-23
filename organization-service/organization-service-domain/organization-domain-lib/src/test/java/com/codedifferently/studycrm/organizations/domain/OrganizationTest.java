package com.codedifferently.studycrm.organizations.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class OrganizationTest {
    @Test
    void testGetId() {
        Organization organization = new Organization("Morgan Latimer LLC");
        assertNull(organization.getId());
    }

    @Test
    void testGetFirstName() {
        Organization organization = new Organization("Morgan Latimer LLC");
        assertEquals("Morgan Latimer LLC", organization.getOrganizationName());
    }
}
package com.codedifferently.studycrm.organizations.api.web;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CreateOrganizationRequestTest {

    @Test
    void testBuildRequest() {
        // Act
        var organization = CreateOrganizationRequest.builder()
                .organizationName("My Organization Inc.")
                .userDetails(UserDetails.builder()
                        .username("john.doe@example.com")
                        .email("dummy@example.com")
                        .password("somePassword")
                        .firstName("John")
                        .lastName("Doe")
                        .build())
                .build();

        // Assert
        assertEquals("My Organization Inc.", organization.getOrganizationName());
        assertNotNull(organization.getUserDetails());
        assertEquals("john.doe@example.com", organization.getUserDetails().getUsername());
        assertEquals("dummy@example.com", organization.getUserDetails().getEmail());
        assertEquals("somePassword", organization.getUserDetails().getPassword());
        assertEquals("John", organization.getUserDetails().getFirstName());
        assertEquals("Doe", organization.getUserDetails().getLastName());
    }

}
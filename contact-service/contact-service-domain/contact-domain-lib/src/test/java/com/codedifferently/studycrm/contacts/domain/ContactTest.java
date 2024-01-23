package com.codedifferently.studycrm.contacts.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ContactTest {
    @Test
    void testGetId() {
        Contact contact = new Contact("John", "Doe");
        assertNull(contact.getId());
    }

    @Test
    void testGetFirstName() {
        Contact contact = new Contact("John", "Doe");
        assertEquals("John", contact.getFirstName());
    }

    @Test
    void testGetLastName() {
        Contact contact = new Contact("John", "Doe");
        assertEquals("Doe", contact.getLastName());
    }
}
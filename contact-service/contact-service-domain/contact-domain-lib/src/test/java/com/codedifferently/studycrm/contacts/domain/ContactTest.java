package com.codedifferently.studycrm.contacts.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ContactTest {
    @Test
    void testGetId() {
        Contact contact = Contact.builder()
                .firstName("Doe")
                .lastName("John")
                .build();
        assertNull(contact.getId());
    }

    @Test
    void testGetFirstName() {
        Contact contact = Contact.builder()
                .firstName("Doe")
                .lastName("John")
                .build();
        assertEquals("John", contact.getFirstName());
    }

    @Test
    void testGetLastName() {
        Contact contact = Contact.builder()
                .firstName("Doe")
                .lastName("John")
                .build();
        assertEquals("Doe", contact.getLastName());
    }
}
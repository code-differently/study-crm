package com.codedifferently.studycrm.contacts.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ContactTest {
    @Test
    void testGetId() {
        Contact contact = Contact.builder()
                .firstName("John")
                .lastName("Doe")
                .build();
        assertNull(contact.getId());
        assertEquals("John", contact.getFirstName());
        assertEquals("Doe", contact.getLastName());
    }
}
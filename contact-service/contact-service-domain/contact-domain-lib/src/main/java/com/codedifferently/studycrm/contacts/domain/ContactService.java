package com.codedifferently.studycrm.contacts.domain;

import jakarta.transaction.Transactional;

import java.util.Objects;

public class ContactService {

    private ContactRepository ContactRepository;

    public ContactService(ContactRepository ContactRepository) {
        this.ContactRepository = ContactRepository;
    }

    @Transactional
    public Contact createContact(String firstName, String lastName) {
        Contact contact = Contact.builder()
                .firstName(firstName)
                .lastName(lastName)
                .build();
        Objects.requireNonNull(contact);
        return ContactRepository.save(contact);
    }
}
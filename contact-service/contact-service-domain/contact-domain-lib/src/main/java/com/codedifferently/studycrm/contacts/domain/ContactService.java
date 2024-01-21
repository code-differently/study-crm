package com.codedifferently.studycrm.contacts.domain;

import jakarta.transaction.Transactional;

public class ContactService {

    private ContactRepository ContactRepository;

    public ContactService(ContactRepository ContactRepository) {
        this.ContactRepository = ContactRepository;
    }

    @Transactional
    public Contact createContact(String firstName, String lastName) {
        Contact Contact = new Contact(firstName, lastName);
        return ContactRepository.save(Contact);
    }
}
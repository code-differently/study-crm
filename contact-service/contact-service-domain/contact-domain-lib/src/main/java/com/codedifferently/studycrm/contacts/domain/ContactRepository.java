package com.codedifferently.studycrm.contacts.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ContactRepository extends CrudRepository<Contact, UUID> {
}
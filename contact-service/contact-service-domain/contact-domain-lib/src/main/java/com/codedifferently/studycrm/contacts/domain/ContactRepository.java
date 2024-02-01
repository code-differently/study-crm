package com.codedifferently.studycrm.contacts.domain;

import java.util.UUID;
import org.springframework.data.repository.CrudRepository;

public interface ContactRepository extends CrudRepository<Contact, UUID> {}

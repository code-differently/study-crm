package com.codedifferently.studycrm.contacts.persistence;

import com.codedifferently.studycrm.contacts.domain.Contact;
import com.codedifferently.studycrm.contacts.domain.ContactRepository;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackageClasses = { ContactRepository.class })
@EntityScan(basePackageClasses = { Contact.class })
public class ContactPersistenceConfiguration {

}
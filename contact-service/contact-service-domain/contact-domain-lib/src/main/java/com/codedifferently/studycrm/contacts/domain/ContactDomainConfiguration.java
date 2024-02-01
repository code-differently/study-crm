package com.codedifferently.studycrm.contacts.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ContactDomainConfiguration {

  @Bean
  public ContactService contactService(ContactRepository contactRepository) {
    return new ContactService(contactRepository);
  }
}

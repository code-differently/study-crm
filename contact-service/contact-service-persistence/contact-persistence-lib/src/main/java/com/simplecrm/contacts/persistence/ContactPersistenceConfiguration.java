
package com.simplecrm.contacts.persistence;

import com.simplecrm.contacts.domain.Contact;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan(basePackageClasses = { Contact.class })
public class ContactPersistenceConfiguration {

}
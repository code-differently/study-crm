package com.codedifferently.studycrm.organizations.persistence;

import com.codedifferently.studycrm.organizations.domain.Organization;
import com.codedifferently.studycrm.organizations.domain.OrganizationRepository;
import com.codedifferently.studycrm.organizations.domain.UserRepository;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackageClasses = { OrganizationRepository.class, UserRepository.class })
@EntityScan(basePackageClasses = { Organization.class })
public class OrganizationPersistenceConfiguration {

}
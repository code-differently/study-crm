package com.codedifferently.studycrm.organizations.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrganizationDomainConfiguration {

    @Bean
    public OrganizationService organizationService(OrganizationRepository organizationRepository) {
        return new OrganizationService(organizationRepository);
    }
}

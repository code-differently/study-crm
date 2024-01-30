package com.codedifferently.studycrm.organizations.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.acls.model.MutableAclService;

@Configuration
public class OrganizationDomainConfiguration {

    @Bean
    public OrganizationService organizationService(MutableAclService mutableAclService, UserRepository userRepository,
            OrganizationRepository organizationRepository) {
        return new OrganizationService(mutableAclService, userRepository, organizationRepository);
    }
}

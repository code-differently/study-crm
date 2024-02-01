package com.codedifferently.studycrm.organizations.domain;

import com.codedifferently.studycrm.common.domain.EntityAclManager;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.acls.model.MutableAclService;

@Configuration
public class OrganizationDomainConfiguration {

    @Bean
    public OrganizationService organizationService(EntityAclManager entityAclManager, UserRepository userRepository,
            OrganizationRepository organizationRepository) {
        return new OrganizationService(entityAclManager, userRepository, organizationRepository);
    }

}

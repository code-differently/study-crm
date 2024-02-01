package com.codedifferently.studycrm.organizations.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.acls.AclPermissionEvaluator;
import org.springframework.security.acls.model.AclService;
import org.springframework.security.acls.model.MutableAclService;

@Configuration
@EnableMethodSecurity
public class OrganizationDomainConfiguration {

    @Bean
    public OrganizationService organizationService(MutableAclService mutableAclService, UserRepository userRepository,
            OrganizationRepository organizationRepository) {
        return new OrganizationService(mutableAclService, userRepository, organizationRepository);
    }

    @Bean
    public MethodSecurityExpressionHandler defaultMethodSecurityExpressionHandler(AclService aclService) {
        DefaultMethodSecurityExpressionHandler expressionHandler = new DefaultMethodSecurityExpressionHandler();
        expressionHandler.setPermissionEvaluator(permissionEvaluator(aclService));
        return expressionHandler;
    }

    @Bean
    public PermissionEvaluator permissionEvaluator(AclService aclService) {
        return new AclPermissionEvaluator(aclService);
    }

}

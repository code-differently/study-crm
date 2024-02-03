package com.codedifferently.studycrm.organizations.domain;

import static org.mockito.Mockito.mock;

import com.codedifferently.studycrm.common.domain.EntityAclManager;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.acls.AclPermissionEvaluator;
import org.springframework.security.acls.model.MutableAclService;

@TestConfiguration
@EnableAutoConfiguration
class OrganizationDomainTestConfiguration {
  private static PermissionEvaluator permissionEvaluator;

  @Primary
  @Bean
  public OrganizationRepository mockOrganizationRepository(
      OrganizationRepository organizationRepository) {
    return mock(OrganizationRepository.class);
  }

  @Primary
  @Bean
  public UserRepository mockUserRepository(UserRepository userRepository) {
    return mock(UserRepository.class);
  }

  @Bean
  public PermissionEvaluator mockPermissionEvaluator() {
    if (permissionEvaluator == null) {
      permissionEvaluator = mock(AclPermissionEvaluator.class);
    }
    return permissionEvaluator;
  }

  @Primary
  @Bean
  public MethodSecurityExpressionHandler mockExpressionHandler() {
    var expressionHandler = new DefaultMethodSecurityExpressionHandler();
    expressionHandler.setPermissionEvaluator(mockPermissionEvaluator());
    return expressionHandler;
  }

  @Bean
  public EntityAclManager mockEntityAclManager() {
    return mock(EntityAclManager.class);
  }

  @Bean
  public MutableAclService mockMutableAclService() {
    return mock(MutableAclService.class);
  }
}

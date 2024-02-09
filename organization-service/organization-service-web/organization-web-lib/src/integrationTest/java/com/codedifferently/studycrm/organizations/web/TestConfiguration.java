package com.codedifferently.studycrm.organizations.web;

import static org.mockito.Mockito.mock;

import com.codedifferently.studycrm.StudyCrmPackageMarker;
import com.codedifferently.studycrm.organizations.domain.OrganizationService;
import com.codedifferently.studycrm.organizations.sagas.OrganizationSagaService;
import io.eventuate.tram.sagas.spring.inmemory.TramSagaInMemoryConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.acls.AclPermissionEvaluator;

@SpringBootApplication(scanBasePackageClasses = {StudyCrmPackageMarker.class})
@Configuration
@Import({TramSagaInMemoryConfiguration.class})
public class TestConfiguration {
  private static PermissionEvaluator permissionEvaluator;

  @Primary
  @Bean
  public OrganizationService mockOrganizationService() {
    return mock(OrganizationService.class);
  }

  @Primary
  @Bean
  public OrganizationSagaService mockOrganizationSagaService() {
    return mock(OrganizationSagaService.class);
  }

  @Primary
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
}

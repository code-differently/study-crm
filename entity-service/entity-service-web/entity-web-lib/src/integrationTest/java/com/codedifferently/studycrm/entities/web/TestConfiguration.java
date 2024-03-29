package com.codedifferently.studycrm.entities.web;

import static org.mockito.Mockito.mock;

import com.codedifferently.studycrm.StudyCrmPackageMarker;
import com.codedifferently.studycrm.entities.domain.EntityService;
import com.codedifferently.studycrm.entities.domain.PropertyService;
import com.codedifferently.studycrm.entities.layout.domain.LayoutService;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.acls.AclPermissionEvaluator;

@SpringBootApplication(scanBasePackageClasses = {StudyCrmPackageMarker.class})
@Configuration
public class TestConfiguration {
  private static PermissionEvaluator permissionEvaluator;

  @Primary
  @Bean
  public EntityService mockEntityService() {
    return mock(EntityService.class);
  }

  @Primary
  @Bean
  public LayoutService mockLayoutService() {
    return mock(LayoutService.class);
  }

  @Primary
  @Bean
  public PropertyService mockPropertyService() {
    return mock(PropertyService.class);
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

package com.codedifferently.studycrm.organizations.web;

import com.codedifferently.studycrm.common.domain.AclSecurityConfiguration;
import com.codedifferently.studycrm.common.web.exceptions.GlobalExceptionHandler;
import java.util.List;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ComponentScan
@Import({GlobalExceptionHandler.class, AclSecurityConfiguration.class})
public class OrganizationsWebConfiguration implements WebMvcConfigurer {

  @Override
  public void extendMessageConverters(@NonNull List<HttpMessageConverter<?>> converters) {
    converters.add(new MappingJackson2HttpMessageConverter());
  }
}

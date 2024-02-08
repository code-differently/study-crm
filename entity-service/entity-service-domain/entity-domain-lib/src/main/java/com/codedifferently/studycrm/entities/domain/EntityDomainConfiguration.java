package com.codedifferently.studycrm.entities.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EntityDomainConfiguration {

  @Bean
  public EntityService entityService(EntityRepository entityRepository) {
    return new EntityService(entityRepository);
  }
}

package com.codedifferently.studycrm.auth.messaging;

import io.eventuate.tram.sagas.spring.inmemory.TramSagaInMemoryConfiguration;
import io.eventuate.tram.spring.optimisticlocking.OptimisticLockingDecoratorConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("development")
@Import({
  OptimisticLockingDecoratorConfiguration.class,
  AuthCommandHandlerConfiguration.class,
  TramSagaInMemoryConfiguration.class
})
public class AuthMessagingDevConfiguration {}

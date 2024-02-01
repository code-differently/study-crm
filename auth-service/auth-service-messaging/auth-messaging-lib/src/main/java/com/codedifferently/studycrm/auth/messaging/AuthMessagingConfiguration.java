package com.codedifferently.studycrm.auth.messaging;

import io.eventuate.tram.spring.optimisticlocking.OptimisticLockingDecoratorConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({OptimisticLockingDecoratorConfiguration.class, AuthCommandHandlerConfiguration.class})
public class AuthMessagingConfiguration {}

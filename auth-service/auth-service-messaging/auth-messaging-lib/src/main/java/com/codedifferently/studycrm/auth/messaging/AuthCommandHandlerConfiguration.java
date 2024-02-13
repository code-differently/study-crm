package com.codedifferently.studycrm.auth.messaging;

import io.eventuate.tram.commands.consumer.CommandDispatcher;
import io.eventuate.tram.sagas.participant.SagaCommandDispatcherFactory;
import io.eventuate.tram.sagas.spring.participant.SagaParticipantConfiguration;
import io.eventuate.tram.spring.optimisticlocking.OptimisticLockingDecoratorConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
  SagaParticipantConfiguration.class,
  OptimisticLockingDecoratorConfiguration.class,
})
public class AuthCommandHandlerConfiguration {
  @Bean
  public CommandDispatcher consumerCommandDispatcher(
      AuthCommandHandler target, SagaCommandDispatcherFactory sagaCommandDispatcherFactory) {
    return sagaCommandDispatcherFactory.make(
        "authCommandDispatcher", target.commandHandlerDefinitions());
  }
}

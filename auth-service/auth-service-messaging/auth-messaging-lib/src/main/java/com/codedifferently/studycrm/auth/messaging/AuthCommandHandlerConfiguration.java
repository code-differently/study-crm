package com.codedifferently.studycrm.auth.messaging;

import com.codedifferently.studycrm.auth.domain.AuthService;

import io.eventuate.tram.commands.consumer.CommandDispatcher;
import io.eventuate.tram.sagas.participant.SagaCommandDispatcherFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthCommandHandlerConfiguration {

    @Bean
    public AuthCommandHandler authCommandHandler(AuthService authService) {
        return new AuthCommandHandler(authService);
    }

    @Bean
    public CommandDispatcher consumerCommandDispatcher(AuthCommandHandler target,
            SagaCommandDispatcherFactory sagaCommandDispatcherFactory) {
        return sagaCommandDispatcherFactory.make("authCommandDispatcher", target.commandHandlerDefinitions());
    }

}
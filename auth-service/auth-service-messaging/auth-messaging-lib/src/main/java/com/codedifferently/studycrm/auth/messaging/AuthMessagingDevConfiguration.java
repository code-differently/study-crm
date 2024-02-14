package com.codedifferently.studycrm.auth.messaging;

import io.eventuate.tram.sagas.spring.inmemory.TramSagaInMemoryConfiguration;
import io.eventuate.tram.sagas.spring.participant.SagaParticipantConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("development")
@Import({SagaParticipantConfiguration.class, TramSagaInMemoryConfiguration.class})
public class AuthMessagingDevConfiguration {}

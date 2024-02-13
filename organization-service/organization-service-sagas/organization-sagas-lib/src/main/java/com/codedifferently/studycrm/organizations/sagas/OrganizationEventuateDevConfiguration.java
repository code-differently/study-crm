package com.codedifferently.studycrm.organizations.sagas;

import io.eventuate.tram.sagas.spring.inmemory.TramSagaInMemoryConfiguration;
import io.eventuate.tram.sagas.spring.orchestration.SagaOrchestratorConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("development")
@Import({SagaOrchestratorConfiguration.class, TramSagaInMemoryConfiguration.class})
public class OrganizationEventuateDevConfiguration {}

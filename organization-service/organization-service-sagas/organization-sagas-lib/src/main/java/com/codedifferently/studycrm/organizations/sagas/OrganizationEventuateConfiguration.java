package com.codedifferently.studycrm.organizations.sagas;

import io.eventuate.tram.sagas.spring.orchestration.SagaOrchestratorConfiguration;
import io.eventuate.tram.spring.consumer.kafka.EventuateTramKafkaMessageConsumerConfiguration;
import io.eventuate.tram.spring.messaging.producer.jdbc.TramMessageProducerJdbcConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("production")
@Import({
  SagaOrchestratorConfiguration.class,
  TramMessageProducerJdbcConfiguration.class,
  EventuateTramKafkaMessageConsumerConfiguration.class
})
public class OrganizationEventuateConfiguration {}

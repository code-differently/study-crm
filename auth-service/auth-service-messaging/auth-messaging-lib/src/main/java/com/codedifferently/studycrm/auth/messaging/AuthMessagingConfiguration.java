package com.codedifferently.studycrm.auth.messaging;

import io.eventuate.tram.sagas.spring.participant.SagaParticipantConfiguration;
import io.eventuate.tram.spring.consumer.kafka.EventuateTramKafkaMessageConsumerConfiguration;
import io.eventuate.tram.spring.messaging.producer.jdbc.TramMessageProducerJdbcConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("production")
@Import({
  SagaParticipantConfiguration.class,
  TramMessageProducerJdbcConfiguration.class,
  EventuateTramKafkaMessageConsumerConfiguration.class
})
public class AuthMessagingConfiguration {}

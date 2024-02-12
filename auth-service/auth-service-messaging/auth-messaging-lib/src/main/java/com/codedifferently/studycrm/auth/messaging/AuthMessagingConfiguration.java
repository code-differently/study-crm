package com.codedifferently.studycrm.auth.messaging;

import io.eventuate.tram.sagas.spring.participant.SagaParticipantConfiguration;
import io.eventuate.tram.spring.consumer.kafka.EventuateTramKafkaMessageConsumerConfiguration;
import io.eventuate.tram.spring.messaging.producer.common.TramMessagingCommonProducerConfiguration;
import io.eventuate.tram.spring.optimisticlocking.OptimisticLockingDecoratorConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("production")
@Import({
  OptimisticLockingDecoratorConfiguration.class,
  AuthCommandHandlerConfiguration.class,
  SagaParticipantConfiguration.class,
  TramMessagingCommonProducerConfiguration.class,
  EventuateTramKafkaMessageConsumerConfiguration.class
})
public class AuthMessagingConfiguration {}

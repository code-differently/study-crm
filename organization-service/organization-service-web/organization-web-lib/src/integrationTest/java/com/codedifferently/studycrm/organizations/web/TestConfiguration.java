package com.codedifferently.studycrm.organizations.web;

import io.eventuate.tram.sagas.spring.inmemory.TramSagaInMemoryConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@SpringBootApplication(scanBasePackages = "com.codedifferently.studycrm.organizations")
@Configuration
@Import({TramSagaInMemoryConfiguration.class})
public class TestConfiguration {}

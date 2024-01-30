package com.codedifferently.studycrm.organizations.sagas;

import com.codedifferently.studycrm.organizations.domain.OrganizationRepository;
import com.codedifferently.studycrm.organizations.domain.OrganizationService;

import io.eventuate.tram.sagas.orchestration.SagaInstanceFactory;
import io.eventuate.tram.spring.optimisticlocking.OptimisticLockingDecoratorConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories
@EnableAutoConfiguration
@Import(OptimisticLockingDecoratorConfiguration.class)
public class OrganizationSagasConfiguration {

    @Bean
    public OrganizationSagaService organizationSagaService(
            OrganizationRepository organizationRepository,
            SagaInstanceFactory sagaInstanceFactory,
            CreateOrganizationSaga createOrganizationSaga) {
        return new OrganizationSagaService(organizationRepository, sagaInstanceFactory, createOrganizationSaga);
    }

    @Bean
    public CreateOrganizationSaga createOrganizationSaga(
            OrganizationService organizationService,
            AuthServiceProxy authServiceProxy) {
        return new CreateOrganizationSaga(organizationService, authServiceProxy);
    }

    @Bean
    public AuthServiceProxy authServiceProxy() {
        return new AuthServiceProxy();
    }

}

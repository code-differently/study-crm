package com.codedifferently.studycrm.organizations.sagas;

import com.codedifferently.studycrm.organizations.api.messaging.sagas.createOrganization.CreateOrganizationSagaData;
import com.codedifferently.studycrm.organizations.api.web.UserDetails;
import com.codedifferently.studycrm.organizations.domain.Organization;
import com.codedifferently.studycrm.organizations.domain.OrganizationRepository;
import io.eventuate.tram.sagas.orchestration.SagaInstanceFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrganizationSagaService {

  private final OrganizationRepository organizationRepository;
  private final SagaInstanceFactory sagaInstanceFactory;
  private final CreateOrganizationSaga createOrganizationSaga;

  public OrganizationSagaService(
      OrganizationRepository organizationRepository,
      SagaInstanceFactory sagaInstanceFactory,
      CreateOrganizationSaga createOrganizationSaga) {
    this.organizationRepository = organizationRepository;
    this.sagaInstanceFactory = sagaInstanceFactory;
    this.createOrganizationSaga = createOrganizationSaga;
  }

  @Transactional
  public Organization createOrganization(Organization organization, UserDetails user) {
    var data =
        CreateOrganizationSagaData.builder().organization(organization).userDetails(user).build();
    sagaInstanceFactory.create(createOrganizationSaga, data);
    return organizationRepository.findById(data.getOrganizationId()).get();
  }
}

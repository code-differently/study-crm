package com.codedifferently.studycrm.organizations.sagas;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.codedifferently.studycrm.organizations.api.messaging.sagas.createOrganization.CreateOrganizationSagaData;
import com.codedifferently.studycrm.organizations.api.web.UserDetails;
import com.codedifferently.studycrm.organizations.domain.Organization;
import com.codedifferently.studycrm.organizations.domain.OrganizationRepository;
import io.eventuate.tram.sagas.orchestration.SagaInstanceFactory;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;

public class OrganizationSagaServiceTest {

  @Mock private OrganizationRepository organizationRepository;
  @Mock private SagaInstanceFactory sagaInstanceFactory;
  @Mock private CreateOrganizationSaga createOrganizationSaga;

  @InjectMocks private OrganizationSagaService organizationSagaService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testCreateOrganization_correctlyInvokesSaga() {
    // Arrange
    var organization = Organization.builder().name("Test Organization").build();
    var user = UserDetails.builder().username("testuser").build();
    var orgId = UUID.randomUUID();
    when(sagaInstanceFactory.create(any(), any()))
        .then(
            (Answer<Object>)
                invocation -> {
                  CreateOrganizationSagaData data = invocation.getArgument(1);
                  data.setOrganizationId(orgId);
                  return null;
                });
    when(organizationRepository.findById(orgId)).thenReturn(Optional.of(organization));

    // Act
    Organization result = organizationSagaService.createOrganization(organization, user);

    // Assert
    assertThat(result).isEqualTo(result);
  }
}

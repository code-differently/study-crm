package com.codedifferently.studycrm.organizations.sagas;

import static io.eventuate.tram.commands.consumer.CommandWithDestinationBuilder.send;
import static io.eventuate.tram.sagas.testing.SagaUnitTestSupport.given;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.codedifferently.studycrm.auth.api.messaging.commands.CreateAuthUserCommand;
import com.codedifferently.studycrm.organizations.api.messaging.sagas.createOrganization.CreateOrganizationSagaData;
import com.codedifferently.studycrm.organizations.api.web.UserDetails;
import com.codedifferently.studycrm.organizations.domain.Organization;
import com.codedifferently.studycrm.organizations.domain.OrganizationService;
import com.codedifferently.studycrm.organizations.domain.User;
import java.util.Arrays;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;

public class CreateOrganizationSagaTest {

  @Mock private OrganizationService organizationService;
  @Mock private AuthServiceProxy authServiceProxy;

  @InjectMocks private CreateOrganizationSaga createOrganizationSaga;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void testCreateOrganizationSaga_saveOrgAndUser() {
    // Arrange
    var orgId = UUID.randomUUID();
    var userId = UUID.randomUUID();
    var orgRole = String.format("org:%s:admin", orgId);
    var data =
        CreateOrganizationSagaData.builder()
            .organization(Organization.builder().name("Test organization").build())
            .userDetails(
                UserDetails.builder()
                    .username("testUser")
                    .email("test@user.com")
                    .password("somePassword")
                    .firstName("Test")
                    .lastName("User")
                    .build())
            .build();
    UserDetails user = data.getUserDetails();
    var command =
        CreateAuthUserCommand.builder()
            .userId(UUID.randomUUID())
            .username(user.getUsername())
            .email(user.getEmail())
            .password(user.getPassword())
            .firstName(user.getFirstName())
            .lastName(user.getLastName())
            .grantedAuthorities(Arrays.asList(orgRole))
            .build();

    when(organizationService.saveOrganization(data.getOrganization()))
        .then(
            (Answer<Organization>)
                invocation -> {
                  var org = (Organization) invocation.getArgument(0);
                  org.setId(orgId);
                  return org;
                });
    when(organizationService.findUserByUsername(user.getUsername())).thenReturn(null);
    when(organizationService.saveUser(any()))
        .then(
            (Answer<User>)
                invocation -> {
                  var userToSave = (User) invocation.getArgument(0);
                  userToSave.setId(userId);
                  return userToSave;
                });
    when(authServiceProxy.createUser(userId, user, data.getOrganization()))
        .thenReturn(send(command).to("authService").build());

    // Act
    var result = given().saga(createOrganizationSaga, data);

    // Assert
    result
        .expect()
        .command(command)
        .to("authService")
        .andGiven()
        .successReply()
        .expectCompletedSuccessfully();
  }
}

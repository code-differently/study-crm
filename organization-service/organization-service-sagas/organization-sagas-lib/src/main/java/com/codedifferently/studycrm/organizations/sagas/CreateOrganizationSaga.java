package com.codedifferently.studycrm.organizations.sagas;

import com.codedifferently.studycrm.auth.api.messaging.replies.AuthUserCreated;
import com.codedifferently.studycrm.auth.api.messaging.replies.AuthUserNotCreated;
import com.codedifferently.studycrm.organizations.api.messaging.sagas.createOrganization.CreateOrganizationSagaData;
import com.codedifferently.studycrm.organizations.api.web.UserDetails;
import com.codedifferently.studycrm.organizations.domain.CreateOrganizationAndUserResult;
import com.codedifferently.studycrm.organizations.domain.Organization;
import com.codedifferently.studycrm.organizations.domain.OrganizationService;
import com.codedifferently.studycrm.organizations.domain.User;

import io.eventuate.tram.commands.consumer.CommandWithDestination;
import io.eventuate.tram.sagas.orchestration.SagaDefinition;
import io.eventuate.tram.sagas.simpledsl.SimpleSaga;

import java.util.Objects;

public class CreateOrganizationSaga implements SimpleSaga<CreateOrganizationSagaData> {

    private OrganizationService organizationService;
    private AuthServiceProxy authServiceProxy;

    private SagaDefinition<CreateOrganizationSagaData> sagaDefinition = step()
            .invokeLocal(this::createOrganizationAndUser)
            .step()
            .invokeParticipant(this::createAuthUser)
            .onReply(AuthUserNotCreated.class, this::handleAuthUserNotCreated)
            .build();

    public CreateOrganizationSaga(OrganizationService organizationService, AuthServiceProxy authServiceProxy) {
        this.organizationService = organizationService;
        this.authServiceProxy = authServiceProxy;
    }

    @Override
    public SagaDefinition<CreateOrganizationSagaData> getSagaDefinition() {
        return this.sagaDefinition;
    }

    private void createOrganizationAndUser(CreateOrganizationSagaData data) {
        var newUser = User.builder()
                .username(data.getUserDetails().getUsername())
                .email(data.getUserDetails().getEmail())
                .firstName(data.getUserDetails().getFirstName())
                .lastName(data.getUserDetails().getLastName())
                .build();

        CreateOrganizationAndUserResult result = organizationService.saveOrganizationAndUser(
                data.getOrganization(), newUser);

        User user = result.getUser();
        Objects.requireNonNull(user);
        data.setUserId(user.getUuid());

        Organization org = result.getOrganization();
        Objects.requireNonNull(org);
        data.setOrganizationId(org.getUuid());
    }

    private CommandWithDestination createAuthUser(CreateOrganizationSagaData data) {
        UserDetails user = data.getUserDetails();
        Organization organization = data.getOrganization();
        return authServiceProxy.createUser(data.getUserId(), user, organization);
    }

    private void handleAuthUserNotCreated(CreateOrganizationSagaData data, AuthUserNotCreated reply) {
        throw new RuntimeException("Not implemented");
    }
}

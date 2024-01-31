package com.codedifferently.studycrm.organizations.sagas;

import com.codedifferently.studycrm.auth.api.messaging.replies.AuthUserCreated;
import com.codedifferently.studycrm.auth.api.messaging.replies.AuthUserNotCreated;
import com.codedifferently.studycrm.organizations.api.messaging.sagas.createOrganization.CreateOrganizationSagaData;
import com.codedifferently.studycrm.organizations.api.web.UserDetails;
import com.codedifferently.studycrm.organizations.domain.Organization;
import com.codedifferently.studycrm.organizations.domain.OrganizationService;
import com.codedifferently.studycrm.organizations.domain.User;

import java.util.Objects;

import io.eventuate.tram.commands.consumer.CommandWithDestination;
import io.eventuate.tram.sagas.orchestration.SagaDefinition;
import io.eventuate.tram.sagas.simpledsl.SimpleSaga;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.stereotype.Service;

@Service
public class CreateOrganizationSaga implements SimpleSaga<CreateOrganizationSagaData> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CreateOrganizationSaga.class);
    private OrganizationService organizationService;
    private AuthServiceProxy authServiceProxy;

    private SagaDefinition<CreateOrganizationSagaData> sagaDefinition = step()
            .invokeLocal(this::saveOrganizationAndUser)
            .step()
            .invokeParticipant(this::createAuthUser)
            .onReply(AuthUserNotCreated.class, this::handleAuthUserNotCreated)
            .step()
            .invokeLocal(this::handleAuthUserCreated)
            .build();

    public CreateOrganizationSaga(OrganizationService organizationService, AuthServiceProxy authServiceProxy) {
        this.organizationService = organizationService;
        this.authServiceProxy = authServiceProxy;
    }

    @Override
    public SagaDefinition<CreateOrganizationSagaData> getSagaDefinition() {
        return this.sagaDefinition;
    }

    @Transactional
    private void saveOrganizationAndUser(CreateOrganizationSagaData data) {
        // Save organization
        Organization organization = organizationService.saveOrganization(data.getOrganization());

        // Create user if not exists
        UserDetails userDetails = data.getUserDetails();
        User user = organizationService.findUserByUsername(userDetails.getUsername());
        if (user == null) {
            user = User.builder()
                    .username(userDetails.getUsername())
                    .email(userDetails.getEmail())
                    .firstName(userDetails.getFirstName())
                    .lastName(userDetails.getLastName())
                    .defaultOrganizationId(organization.getUuid())
                    .build();
            user = organizationService.saveUser(user);
        }

        // Grant user administrative access to organization
        organizationService.grantUserOrganizationAccess(user, organization, BasePermission.ADMINISTRATION);

        // If auth user saved, activate the organization.
        data.setOrganizationId(organization.getUuid());
        data.setUserId(user.getUuid());
    }

    private CommandWithDestination createAuthUser(CreateOrganizationSagaData data) {
        UserDetails user = data.getUserDetails();
        Organization organization = data.getOrganization();
        return authServiceProxy.createUser(data.getUserId(), user, organization);
    }

    private void handleAuthUserCreated(CreateOrganizationSagaData data) {
        organizationService.activateOrganization(data.getOrganizationId());
    }

    private void handleAuthUserNotCreated(CreateOrganizationSagaData data, AuthUserNotCreated reply) {
        LOGGER.error("Auth user not created for {}.", data.getUserDetails().getEmail());
    }
}

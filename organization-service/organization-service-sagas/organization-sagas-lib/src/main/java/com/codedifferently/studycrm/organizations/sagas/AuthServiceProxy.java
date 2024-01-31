package com.codedifferently.studycrm.organizations.sagas;

import com.codedifferently.studycrm.auth.api.messaging.commands.CreateAuthUserCommand;
import com.codedifferently.studycrm.organizations.api.web.UserDetails;
import com.codedifferently.studycrm.organizations.domain.Organization;

import java.util.Arrays;

import io.eventuate.tram.commands.consumer.CommandWithDestination;
import org.springframework.stereotype.Service;

import static io.eventuate.tram.commands.consumer.CommandWithDestinationBuilder.send;

@Service
public class AuthServiceProxy {

    CommandWithDestination createUser(String userId, UserDetails user, Organization organization) {
        var command = CreateAuthUserCommand.builder()
                .userId(userId)
                .username(user.getUsername())
                .email(user.getEmail())
                .password(user.getPassword())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .grantedAuthorities(Arrays.asList(String.format("org:%s:admin", organization.getUuid())))
                .build();
        return send(command).to("authService").build();
    }

}

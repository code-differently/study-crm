package com.codedifferently.studycrm.organizations.sagas;

import static io.eventuate.tram.commands.consumer.CommandWithDestinationBuilder.send;

import com.codedifferently.studycrm.auth.api.messaging.commands.CreateAuthUserCommand;
import com.codedifferently.studycrm.organizations.api.web.UserDetails;
import com.codedifferently.studycrm.organizations.domain.Organization;
import io.eventuate.tram.commands.consumer.CommandWithDestination;
import java.util.Arrays;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceProxy {

  CommandWithDestination createUser(UUID userId, UserDetails user, Organization organization) {
    var command =
        CreateAuthUserCommand.builder()
            .userId(userId)
            .username(user.getUsername())
            .email(user.getEmail())
            .password(user.getPassword())
            .firstName(user.getFirstName())
            .lastName(user.getLastName())
            .grantedAuthorities(Arrays.asList(String.format("org:%s:admin", organization.getId())))
            .build();
    return send(command).to("authService").build();
  }
}

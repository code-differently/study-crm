package com.codedifferently.studycrm.auth.messaging;

import static io.eventuate.tram.commands.consumer.CommandHandlerReplyBuilder.withFailure;
import static io.eventuate.tram.commands.consumer.CommandHandlerReplyBuilder.withSuccess;

import com.codedifferently.studycrm.auth.api.messaging.commands.CreateAuthUserCommand;
import com.codedifferently.studycrm.auth.api.messaging.replies.AuthUserAlreadyExists;
import com.codedifferently.studycrm.auth.api.messaging.replies.AuthUserCreated;
import com.codedifferently.studycrm.auth.api.messaging.replies.AuthUserNotCreated;
import com.codedifferently.studycrm.auth.domain.AuthService;
import com.codedifferently.studycrm.auth.domain.User;
import com.codedifferently.studycrm.auth.domain.UserAuthority;
import io.eventuate.tram.commands.consumer.CommandHandlers;
import io.eventuate.tram.commands.consumer.CommandMessage;
import io.eventuate.tram.messaging.common.Message;
import io.eventuate.tram.sagas.participant.SagaCommandHandlersBuilder;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthCommandHandler {

  @Autowired private PasswordEncoder passwordEncoder;

  @Autowired private AuthService authService;

  public CommandHandlers commandHandlerDefinitions() {
    return SagaCommandHandlersBuilder.fromChannel("authService")
        .onMessage(CreateAuthUserCommand.class, this::createUser)
        .build();
  }

  public Message createUser(CommandMessage<CreateAuthUserCommand> cm) {
    try {
      CreateAuthUserCommand cmd = cm.getCommand();
      String username = cmd.getUsername();
      User user = authService.findUserByUsername(username);

      if (user != null) {
        log.error("User {} already exists. No user created.", username);
        authService.addUserAuthorities(user, cmd.getGrantedAuthorities());
        return withSuccess(new AuthUserAlreadyExists());
      }

      var newUser =
          User.builder()
              .id(cmd.getUserId())
              .username(cmd.getUsername())
              .email(cmd.getEmail())
              .password(passwordEncoder.encode(cmd.getPassword()))
              .firstName(cmd.getFirstName())
              .lastName(cmd.getLastName())
              .build();
      List<UserAuthority> authorities =
          cmd.getGrantedAuthorities().stream()
              .map(authority -> UserAuthority.builder().authority(authority).user(newUser).build())
              .collect(Collectors.toList());
      newUser.setAuthorities(authorities);
      authService.saveUser(newUser);
      return withSuccess(new AuthUserCreated());
    } catch (Exception e) {
      var message =
          String.format("Error occurred creating auth user {}.", cm.getCommand().getUsername());
      log.error(message, e);
      return withFailure(new AuthUserNotCreated());
    }
  }
}

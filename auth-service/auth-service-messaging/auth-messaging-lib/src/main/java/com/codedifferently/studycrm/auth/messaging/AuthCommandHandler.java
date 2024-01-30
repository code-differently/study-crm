package com.codedifferently.studycrm.auth.messaging;

import com.codedifferently.studycrm.auth.api.messaging.commands.CreateAuthUserCommand;
import com.codedifferently.studycrm.auth.api.messaging.replies.AuthUserCreated;
import com.codedifferently.studycrm.auth.api.messaging.replies.AuthUserNotCreated;
import com.codedifferently.studycrm.auth.domain.AuthService;
import com.codedifferently.studycrm.auth.domain.User;
import com.codedifferently.studycrm.auth.domain.UserAuthority;

import io.eventuate.tram.commands.consumer.CommandHandlers;
import io.eventuate.tram.commands.consumer.CommandMessage;
import io.eventuate.tram.messaging.common.Message;
import io.eventuate.tram.sagas.participant.SagaCommandHandlersBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static io.eventuate.tram.commands.consumer.CommandHandlerReplyBuilder.withFailure;
import static io.eventuate.tram.commands.consumer.CommandHandlerReplyBuilder.withSuccess;

import java.util.stream.Collectors;
import java.util.List;

public class AuthCommandHandler {

    private PasswordEncoder passwordEncoder;
    private AuthService authService;

    public AuthCommandHandler(AuthService authService, PasswordEncoder passwordEncoder) {
        this.authService = authService;
        this.passwordEncoder = passwordEncoder;
    }

    public CommandHandlers commandHandlerDefinitions() {
        return SagaCommandHandlersBuilder
                .fromChannel("authService")
                .onMessage(CreateAuthUserCommand.class, this::createUser)
                .build();
    }

    public Message createUser(CommandMessage<CreateAuthUserCommand> cm) {
        try {
            CreateAuthUserCommand cmd = cm.getCommand();
            User user = User.builder()
                    .uuid(cmd.getUserId())
                    .username(cmd.getUsername())
                    .email(cmd.getEmail())
                    .password(passwordEncoder.encode(cmd.getPassword()))
                    .firstName(cmd.getFirstName())
                    .lastName(cmd.getLastName())
                    .build();
            List<UserAuthority> authorities = cmd.getGrantedAuthorities().stream()
                    .map(authority -> UserAuthority.builder()
                            .authority(authority)
                            .user(user)
                            .build())
                    .collect(Collectors.toList());
            user.setAuthorities(authorities);
            authService.saveUser(user);
            return withSuccess(new AuthUserCreated());
        } catch (Exception e) {
            System.out.println(e);
            return withFailure(new AuthUserNotCreated());
        }
    }

}

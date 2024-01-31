package com.codedifferently.studycrm.auth.messaging;

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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.eventuate.tram.commands.consumer.CommandHandlerReplyBuilder.withFailure;
import static io.eventuate.tram.commands.consumer.CommandHandlerReplyBuilder.withSuccess;

import java.util.stream.Collectors;
import java.util.List;

@Service
public class AuthCommandHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthCommandHandler.class);

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthService authService;

    public CommandHandlers commandHandlerDefinitions() {
        return SagaCommandHandlersBuilder
                .fromChannel("authService")
                .onMessage(CreateAuthUserCommand.class, this::createUser)
                .build();
    }

    public Message createUser(CommandMessage<CreateAuthUserCommand> cm) {
        try {
            String username = cm.getCommand().getUsername();
            User user = authService.findUserByUsername(username);
            CreateAuthUserCommand cmd = cm.getCommand();

            if (user != null) {
                LOGGER.error("User {} already exists. No user created.", username);
                authService.addUserAuthorities(user, cmd.getGrantedAuthorities());
                return withSuccess(new AuthUserAlreadyExists());
            }

            var newUser = User.builder()
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
                            .user(newUser)
                            .build())
                    .collect(Collectors.toList());
            newUser.setAuthorities(authorities);
            authService.saveUser(newUser);
            return withSuccess(new AuthUserCreated());
        } catch (Exception e) {
            System.out.println(e);
            return withFailure(new AuthUserNotCreated());
        }
    }

}

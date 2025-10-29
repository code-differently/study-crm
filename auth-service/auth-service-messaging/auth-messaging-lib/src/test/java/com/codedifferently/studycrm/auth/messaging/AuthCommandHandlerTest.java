package com.codedifferently.studycrm.auth.messaging;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.codedifferently.studycrm.auth.api.messaging.commands.CreateAuthUserCommand;
import com.codedifferently.studycrm.auth.api.messaging.replies.AuthUserAlreadyExists;
import com.codedifferently.studycrm.auth.api.messaging.replies.AuthUserCreated;
import com.codedifferently.studycrm.auth.api.messaging.replies.AuthUserNotCreated;
import com.codedifferently.studycrm.auth.domain.AuthService;
import com.codedifferently.studycrm.auth.domain.User;

import static io.eventuate.tram.commands.consumer.CommandHandlerReplyBuilder.withFailure;
import static io.eventuate.tram.commands.consumer.CommandHandlerReplyBuilder.withSuccess;
import io.eventuate.tram.commands.consumer.CommandMessage;
import io.eventuate.tram.messaging.common.Message;

class AuthCommandHandlerTest {
  @Mock private PasswordEncoder passwordEncoder;
  @Mock private AuthService authService;
  @InjectMocks private AuthCommandHandler authCommandHandler;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void createUser_WhenUserExists_repliesAlreadyCreated() {
    // Arrange
    var message = mock(CommandMessage.class);
    when(message.getCommand())
        .thenReturn(
            CreateAuthUserCommand.builder()
                .username("testUser")
                .grantedAuthority("ROLE_USER")
                .grantedAuthority("ROLE_ADMIN")
                .build());
    when(authService.findUserByUsername("testUser")).thenReturn(User.builder().build());

    // Act
    var result = authCommandHandler.createUser(message);

    // Assert
    var expected = withSuccess(new AuthUserAlreadyExists());
    assertMessageEquals(expected, result);
  }

  @Test
  void createUser_WhenCreatingUser_reportsErrors() {
    // Arrange
    var message = mock(CommandMessage.class);
    when(message.getCommand())
        .thenReturn(
            CreateAuthUserCommand.builder()
                .username("testUser")
                .grantedAuthority("ROLE_USER")
                .grantedAuthority("ROLE_ADMIN")
                .build());
    when(authService.findUserByUsername("testUser")).thenThrow(new RuntimeException("Test exception"));

    // Act
    var result = authCommandHandler.createUser(message);

    // Assert
    var expected = withFailure(new AuthUserNotCreated());
    assertMessageEquals(expected, result);
  }

  @Test
  void createUser_WhenCreatingUser_savesNewUser() {
    // Arrange
    var message = mock(CommandMessage.class);
    when(message.getCommand())
        .thenReturn(
            CreateAuthUserCommand.builder()
                .username("testUser")
                .email("test@user.com")
                .password("somePassword")
                .firstName("Test")
                .lastName("User")
                .grantedAuthority("ROLE_USER")
                .grantedAuthority("ROLE_ADMIN")
                .build());
    when(authService.findUserByUsername("testUser")).thenReturn(null);
    when(passwordEncoder.encode("somePassword")).thenReturn("encodedPassword");

    // Act
    var result = authCommandHandler.createUser(message);

    // Assert
    var expected = withSuccess(new AuthUserCreated());
    assertMessageEquals(expected, result);
    // TODO(anthonydmays): Verify that the user is saved with correct properties.
    verify(authService, times(1)).saveUser(any(User.class));
  }

  private void assertMessageEquals(Message expected, Message actual) {
    assertThat(actual.getHeaders()).isEqualTo(expected.getHeaders());
    assertThat(actual.getPayload()).isEqualTo(expected.getPayload());
  }
}

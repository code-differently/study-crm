package com.codedifferently.studycrm.auth.web.security;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import com.codedifferently.studycrm.auth.domain.User;
import com.codedifferently.studycrm.auth.domain.UserAuthority;
import com.codedifferently.studycrm.auth.domain.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;

public class OidcUserInfoServiceTest {
  @Mock private UserRepository userRepository;

  @InjectMocks private OidcUserInfoService oidcUserInfoService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void loadUser_WhenUserNotFound_ThrowsRuntimeException() {
    // Arrange
    String username = "username";
    when(userRepository.findByUsername(username)).thenReturn(null);

    // Act
    Throwable thrown =
        assertThrows(RuntimeException.class, () -> oidcUserInfoService.loadUser(username));

    // Assert
    assertThat(thrown.getMessage()).isEqualTo("User not found");
  }

  @Test
  void loadUser_WhenUserFound_ReturnsOidcUserInfo() {
    // Arrange
    String username = "testuser";
    when(userRepository.findByUsername(username))
        .thenReturn(
            User.builder()
                .username(username)
                .firstName("Test")
                .lastName("User")
                .email("test@user.com")
                .authority(UserAuthority.builder().authority("ROLE_USER").build())
                .build());

    // Act
    OidcUserInfo result = oidcUserInfoService.loadUser(username);

    // Assert
    assertThat(result.getSubject()).isEqualTo(username);
    assertThat(result.getFullName()).isEqualTo("Test User");
    assertThat(result.getGivenName()).isEqualTo("Test");
    assertThat(result.getFamilyName()).isEqualTo("User");
    assertThat(result.getEmail()).isEqualTo("test@user.com");
    assertThat(result.getClaim("https://studycrm.com/roles").toString()).isEqualTo("ROLE_USER");
  }
}

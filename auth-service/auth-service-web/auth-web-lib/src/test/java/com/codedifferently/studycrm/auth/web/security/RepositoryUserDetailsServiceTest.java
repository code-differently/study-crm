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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class RepositoryUserDetailsServiceTest {
  @Mock private UserRepository userRepository;

  @InjectMocks private RepositoryUserDetailsService repositoryUserDetailsService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void loadUserByUsername_WhenUserNotFound_ThrowsRuntimeException() {
    // Arrange
    String username = "username";
    when(userRepository.findByUsername(username)).thenReturn(null);

    // Act / Assert
    assertThrows(
        UsernameNotFoundException.class,
        () -> repositoryUserDetailsService.loadUserByUsername(username));
  }

  @Test
  void loadUser_WhenUserFound_ReturnsUserDetails() {
    // Arrange
    String username = "testuser";
    when(userRepository.findByUsername(username))
        .thenReturn(
            User.builder()
                .username(username)
                .password("test123")
                .firstName("Test")
                .lastName("User")
                .email("test@user.com")
                .authority(UserAuthority.builder().authority("ROLE_STUFF").build())
                .build());

    // Act
    UserDetails result = repositoryUserDetailsService.loadUserByUsername(username);

    // Assert
    assertThat(result.getUsername()).isEqualTo(username);
    assertThat(result.getPassword()).isEqualTo("test123");
    assertThat(result.getAuthorities()).anySatisfy(a -> a.getAuthority().equals("ROLE_STUFF"));
  }
}

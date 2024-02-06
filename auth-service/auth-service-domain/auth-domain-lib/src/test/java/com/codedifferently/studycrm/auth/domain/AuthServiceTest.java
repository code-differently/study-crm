package com.codedifferently.studycrm.auth.domain;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class AuthServiceTest {

  @Mock private UserRepository userRepository;
  @Mock private UserAuthorityRepository userAuthorityRepository;

  @InjectMocks private AuthService authService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void saveUser_ShouldReturnSavedUser() {
    User user = new User();
    when(userRepository.save(user)).thenReturn(user);

    User savedUser = authService.saveUser(user);

    assertEquals(user, savedUser);
    verify(userRepository).save(user);
  }

  @Test
  void userExists_WithExistingUsername_ShouldReturnTrue() {
    String username = "testuser";
    when(userRepository.existsByUsername(username)).thenReturn(true);

    boolean exists = authService.userExists(username);

    assertTrue(exists);
    verify(userRepository).existsByUsername(username);
  }

  @Test
  void userExists_WithNonExistingUsername_ShouldReturnFalse() {
    String username = "testuser";
    when(userRepository.existsByUsername(username)).thenReturn(false);

    boolean exists = authService.userExists(username);

    assertFalse(exists);
    verify(userRepository).existsByUsername(username);
  }

  @Test
  void findUserByUsername_ShouldReturnUser() {
    String username = "testuser";
    User user = new User();
    when(userRepository.findByUsername(username)).thenReturn(user);

    User foundUser = authService.findUserByUsername(username);

    assertEquals(user, foundUser);
    verify(userRepository).findByUsername(username);
  }

  @Test
  void addUserAuthorities_ShouldAddNewAuthorities() {
    User user = mock(User.class);
    List<String> authoritiesToAdd = Arrays.asList("ROLE_ADMIN", "ROLE_USER");

    List<UserAuthority> userAuthorities =
        Arrays.asList(
            UserAuthority.builder().authority("ROLE_ADMIN").user(user).build(),
            UserAuthority.builder().authority("ROLE_MANAGER").user(user).build());
    when(user.getAuthorities()).thenReturn(userAuthorities);

    authService.addUserAuthorities(user, authoritiesToAdd);

    verify(userAuthorityRepository).saveAll(anyList());
  }
}

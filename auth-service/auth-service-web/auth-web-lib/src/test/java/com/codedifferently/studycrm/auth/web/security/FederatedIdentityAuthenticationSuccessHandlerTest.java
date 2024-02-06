package com.codedifferently.studycrm.auth.web.security;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

class FederatedIdentityAuthenticationSuccessHandlerTest {

  @Mock private HttpServletRequest request;
  @Mock private HttpServletResponse response;
  @Mock private AuthenticationManager authenticationManager;
  @Mock private OAuth2AuthenticationToken authentication;
  @Mock private AuthenticationSuccessHandler delegate;
  @Mock private UserRepositoryOAuth2UserHandler oauth2UserHandler;
  @Mock private UserRepositoryOidcUserHandler oidcUserHandler;

  @InjectMocks private FederatedIdentityAuthenticationSuccessHandler successHandler;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    successHandler.setOAuth2UserHandler(oauth2UserHandler);
    successHandler.setOidcUserHandler(oidcUserHandler);
    successHandler.setDelegate(delegate);
  }

  @Test
  void onAuthenticationSuccess_WithOidcUser_CallsOidcUserHandler() throws Exception {
    // Arrange
    OidcUser oidcUser = mock(OidcUser.class);
    when(authentication.getPrincipal()).thenReturn(oidcUser);

    // Act
    successHandler.onAuthenticationSuccess(request, response, authentication);

    // Assert
    verify(oidcUserHandler, times(1)).accept(oidcUser);
    verify(delegate, times(1)).onAuthenticationSuccess(request, response, authentication);
  }

  @Test
  void onAuthenticationSuccess_WithOAuth2User_CallsOAuth2UserHandler() throws Exception {
    // Arrange
    OAuth2User oauth2User = mock(OAuth2User.class);
    when(authentication.getPrincipal()).thenReturn(oauth2User);

    // Act
    successHandler.onAuthenticationSuccess(request, response, authentication);

    // Assert
    verify(oauth2UserHandler, times(1)).accept(oauth2User);
    verify(delegate, times(1)).onAuthenticationSuccess(request, response, authentication);
  }

  @Test
  void onAuthenticationSuccess_WithNonOAuth2Authentication_DoesNotCallHandlers() throws Exception {
    // Arrange
    var usernamePasswordAuthentication = mock(UsernamePasswordAuthenticationToken.class);
    when(usernamePasswordAuthentication.getPrincipal()).thenReturn(new Object());

    // Act
    successHandler.onAuthenticationSuccess(request, response, authentication);

    // Assert
    verify(oauth2UserHandler, never()).accept(any(OAuth2User.class));
    verify(oidcUserHandler, never()).accept(any(OidcUser.class));
    verify(delegate, times(1)).onAuthenticationSuccess(request, response, authentication);
  }
}

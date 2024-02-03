package com.codedifferently.studycrm.auth.web.controllers;

import static org.mockito.Mockito.mock;

import com.codedifferently.studycrm.auth.domain.AuthService;
import com.codedifferently.studycrm.auth.domain.UserAuthorityRepository;
import com.codedifferently.studycrm.auth.domain.UserRepository;
import com.codedifferently.studycrm.auth.web.security.OidcUserInfoService;
import com.codedifferently.studycrm.auth.web.security.RepositoryUserDetailsService;
import com.codedifferently.studycrm.auth.web.security.UserRepositoryOAuth2UserHandler;
import com.codedifferently.studycrm.auth.web.security.UserRepositoryOidcUserHandler;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;

@TestConfiguration
public class AuthWebTestConfiguration {
  @Primary
  @Bean
  public RegisteredClientRepository mockRegisteredClientRepository() {
    return mock(RegisteredClientRepository.class);
  }

  @Bean
  public AuthService mockAuthService() {
    return mock(AuthService.class);
  }

  @Primary
  @Bean
  public UserRepository mockUserRepository() {
    return mock(UserRepository.class);
  }

  @Bean
  public UserAuthorityRepository mockUserAuthorityRepository() {
    return mock(UserAuthorityRepository.class);
  }

  @Bean
  public JdbcTemplate mockJdbcTemplate() {
    return mock(JdbcTemplate.class);
  }

  @Bean
  public OidcUserInfoService mockOidcUserService() {
    return mock(OidcUserInfoService.class);
  }

  @Bean
  public ClientRegistrationRepository mockClientRegistrationRepository() {
    return mock(ClientRegistrationRepository.class);
  }

  @Bean
  public UserRepositoryOAuth2UserHandler userRepositoryOAuth2UserHandler(
      UserRepository userRepository) {
    return new UserRepositoryOAuth2UserHandler(userRepository);
  }

  @Bean
  public UserRepositoryOidcUserHandler userRepositoryOidcHandler(UserRepository userRepository) {
    return new UserRepositoryOidcUserHandler(userRepository);
  }

  @Bean
  public RepositoryUserDetailsService repositoryUserDetailsService(UserRepository userRepository) {
    return new RepositoryUserDetailsService(userRepository);
  }
}

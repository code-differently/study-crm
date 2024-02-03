package com.codedifferently.studycrm.auth.web;

import static org.mockito.Mockito.mock;

import com.codedifferently.studycrm.auth.domain.UserAuthorityRepository;
import com.codedifferently.studycrm.auth.domain.UserRepository;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;

@TestConfiguration
@SpringBootApplication
public class AuthWebTestConfiguration {

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
  public ClientRegistrationRepository mockClientRegistrationRepository() {
    return mock(ClientRegistrationRepository.class);
  }
}

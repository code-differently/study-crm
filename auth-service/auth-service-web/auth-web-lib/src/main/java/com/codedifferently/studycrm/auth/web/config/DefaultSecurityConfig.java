package com.codedifferently.studycrm.auth.web.config;

import com.codedifferently.studycrm.auth.web.security.FederatedIdentityConfigurer;
import com.codedifferently.studycrm.auth.web.security.RepositoryUserDetailsService;
import com.codedifferently.studycrm.auth.web.security.UserRepositoryOAuth2UserHandler;
import com.codedifferently.studycrm.auth.web.security.UserRepositoryOidcUserHandler;
import java.util.HashMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.session.HttpSessionEventPublisher;

/**
 * @author Steve Riesenberg
 * @since 0.2.3
 */
@EnableWebSecurity
@Configuration(proxyBeanMethods = false)
public class DefaultSecurityConfig {

  @Bean
  public SecurityFilterChain defaultSecurityFilterChain(
      HttpSecurity http,
      UserRepositoryOAuth2UserHandler userRepositoryOAuth2UserHandler,
      UserRepositoryOidcUserHandler userRepositoryOidcUserHandler)
      throws Exception {
    FederatedIdentityConfigurer federatedIdentityConfigurer = new FederatedIdentityConfigurer();
    federatedIdentityConfigurer.oauth2UserHandler(userRepositoryOAuth2UserHandler);
    federatedIdentityConfigurer.oidcUserHandler(userRepositoryOidcUserHandler);
    http.authorizeHttpRequests(
            authorize ->
                authorize
                    .requestMatchers("/v3/api-docs")
                    .permitAll()
                    .requestMatchers("/actuator/**")
                    .permitAll()
                    .requestMatchers("/assets/**")
                    .permitAll()
                    .requestMatchers("/webjars/**")
                    .permitAll()
                    .requestMatchers("/login")
                    .permitAll()
                    .anyRequest()
                    .authenticated())
        .formLogin(Customizer.withDefaults())
        .apply(federatedIdentityConfigurer);
    return http.build();
  }

  @Bean
  public AuthenticationProvider authenticationProvider(
      RepositoryUserDetailsService repositoryUserDetailsService) {
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    provider.setUserDetailsService(repositoryUserDetailsService);
    provider.setPasswordEncoder(passwordEncoder());
    return provider;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    var defaultEncoder = new BCryptPasswordEncoder();
    var encoders = new HashMap<String, PasswordEncoder>();
    encoders.put("bcrypt", defaultEncoder);

    DelegatingPasswordEncoder passworEncoder = new DelegatingPasswordEncoder("bcrypt", encoders);
    passworEncoder.setDefaultPasswordEncoderForMatches(defaultEncoder);

    return passworEncoder;
  }

  @Bean
  public SessionRegistry sessionRegistry() {
    return new SessionRegistryImpl();
  }

  @Bean
  public HttpSessionEventPublisher httpSessionEventPublisher() {
    return new HttpSessionEventPublisher();
  }
}

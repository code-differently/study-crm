package com.codedifferently.studycrm.entities.web.config;

import static org.springframework.security.config.Customizer.withDefaults;

import com.codedifferently.studycrm.common.web.config.JwtConverterConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

/** Configures our application with Spring Security to restrict access to our API endpoints. */
@Configuration
@Import(JwtConverterConfig.class)
public class EntitiesSecurityConfiguration {

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    /*
     * This is where we configure the security required for our endpoints and setup
     * our app to serve as
     * an OAuth2 Resource Server, using JWT validation.
     */
    return http.csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(
            (authorize) ->
                authorize
                    .requestMatchers("/actuator/**")
                    .permitAll()
                    .requestMatchers("/v3/api-docs")
                    .permitAll()
                    .anyRequest()
                    .authenticated())
        .sessionManagement(
            session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .cors(withDefaults())
        .oauth2ResourceServer(oauth2 -> oauth2.jwt(withDefaults()))
        .build();
  }
}

package com.codedifferently.studycrm.auth.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthDomainConfiguration {

    @Bean
    public AuthService authService(UserRepository userRepository) {
        return new AuthService(userRepository);
    }

}
package com.codedifferently.studycrm.auth.web.config;

import com.codedifferently.studycrm.auth.web.security.FederatedIdentityConfigurer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.session.HttpSessionEventPublisher;

/**
 * @author Steve Riesenberg
 * @since 0.2.3
 */
@EnableWebSecurity
@Configuration(proxyBeanMethods = false)
public class DefaultSecurityConfig {

	// @formatter:off
	@Bean
	public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		FederatedIdentityConfigurer federatedIdentityConfigurer = new FederatedIdentityConfigurer();
		http
			.authorizeHttpRequests(authorize ->
				authorize
					.requestMatchers("/v3/api-docs").permitAll()
					.requestMatchers("/actuator/**").permitAll()
					.requestMatchers("/assets/**").permitAll()
					.requestMatchers("/webjars/**").permitAll()
					.requestMatchers("/login").permitAll()
					.anyRequest().authenticated()
			)
			.formLogin(Customizer.withDefaults())
			.apply(federatedIdentityConfigurer);
		return http.build();
	}

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails userDetails = User.withDefaultPasswordEncoder()
                .username("user")
                .password("password")
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(userDetails);
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

package com.codedifferently.studycrm.auth.web.security;

import java.util.function.Consumer;
import org.springframework.context.ApplicationContext;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.util.Assert;

/**
 * A configurer for setting up Federated Identity Management.
 *
 * @author Steve Riesenberg
 * @since 0.2.3
 */
public final class FederatedIdentityConfigurer
    extends AbstractHttpConfigurer<FederatedIdentityConfigurer, HttpSecurity> {

  private String loginPageUrl = "/login";

  private Consumer<OAuth2User> oauth2UserHandler;

  private Consumer<OidcUser> oidcUserHandler;

  /**
   * @param oauth2UserHandler The {@link Consumer} for performing JIT account provisioning with an
   *     OAuth 2.0 IDP
   * @return This configurer for additional configuration
   */
  public FederatedIdentityConfigurer oauth2UserHandler(Consumer<OAuth2User> oauth2UserHandler) {
    Assert.notNull(oauth2UserHandler, "oauth2UserHandler cannot be null");
    this.oauth2UserHandler = oauth2UserHandler;
    return this;
  }

  /**
   * @param oidcUserHandler The {@link Consumer} for performing JIT account provisioning with an
   *     OpenID Connect 1.0 IDP
   * @return This configurer for additional configuration
   */
  public FederatedIdentityConfigurer oidcUserHandler(Consumer<OidcUser> oidcUserHandler) {
    Assert.notNull(oidcUserHandler, "oidcUserHandler cannot be null");
    this.oidcUserHandler = oidcUserHandler;
    return this;
  }

  @Override
  public void init(HttpSecurity http) throws Exception {
    ApplicationContext applicationContext = http.getSharedObject(ApplicationContext.class);
    ClientRegistrationRepository clientRegistrationRepository =
        applicationContext.getBean(ClientRegistrationRepository.class);
    var authenticationEntryPoint =
        new FederatedIdentityAuthenticationEntryPoint(
            this.loginPageUrl, clientRegistrationRepository);

    var authenticationSuccessHandler = new FederatedIdentityAuthenticationSuccessHandler();
    if (this.oauth2UserHandler != null) {
      authenticationSuccessHandler.setOAuth2UserHandler(this.oauth2UserHandler);
    }
    if (this.oidcUserHandler != null) {
      authenticationSuccessHandler.setOidcUserHandler(this.oidcUserHandler);
    }

    http.exceptionHandling(
            exceptionHandling ->
                exceptionHandling.authenticationEntryPoint(authenticationEntryPoint))
        .oauth2Login(
            oauth2Login -> {
              oauth2Login.successHandler(authenticationSuccessHandler);
            });
  }
}

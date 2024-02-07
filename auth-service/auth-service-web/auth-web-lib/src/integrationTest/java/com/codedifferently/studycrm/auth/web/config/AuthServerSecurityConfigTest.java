package com.codedifferently.studycrm.auth.web.config;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

import com.codedifferently.studycrm.auth.web.TestConfiguration;
import com.codedifferently.studycrm.auth.web.security.OidcUserInfoService;
import java.time.Instant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;

@SpringBootTest
public class AuthServerSecurityConfigTest {

  @Configuration
  @Import(TestConfiguration.class)
  static class Config {
    @Primary
    @Bean
    public OidcUserInfoService mockUserInfoService() {
      return mock(OidcUserInfoService.class);
    }
  }

  @Autowired private OidcUserInfoService userInfoService;

  @Autowired private OAuth2TokenCustomizer<JwtEncodingContext> tokenCustomizer;

  @BeforeEach
  void setUp() {
    reset(userInfoService);
  }

  @Test
  void testTokenCustomizer_customizesAccessToken() {
    // Arrange
    JwtEncodingContext context = getJwtEncodingContext(OAuth2TokenType.ACCESS_TOKEN);

    // Act
    tokenCustomizer.customize(context);

    // Assert
    var claimsSet = context.getClaims().build();
    assertThat(claimsSet.hasClaim("roles")).isTrue();
    assertThat(claimsSet.hasClaim("https://studycrm.com/roles")).isFalse();
    assertThat((String) claimsSet.getClaim("roles")).isEqualTo("ROLE_USER ROLE_ADMIN");
  }

  @Test
  void testTokenCustomizer_customizesIdToken() {
    // Arrange
    JwtEncodingContext context = getJwtEncodingContext(new OAuth2TokenType("id_token"));

    // Act
    tokenCustomizer.customize(context);

    // Assert
    var claimsSet = context.getClaims().build();
    assertThat(claimsSet.hasClaim("roles")).isFalse();
    assertThat(claimsSet.hasClaim("https://studycrm.com/roles")).isFalse();
  }

  private JwtEncodingContext getJwtEncodingContext(OAuth2TokenType tokenType) {
    var userInfo =
        OidcUserInfo.builder()
            .subject("testUser")
            .claim("https://studycrm.com/roles", "ROLE_USER ROLE_ADMIN")
            .build();
    when(userInfoService.loadUser("testUser")).thenReturn(userInfo);
    var claims = JwtClaimsSet.builder().subject("testUser").issuedAt(Instant.now());
    var principal = mock(Authentication.class);
    when(principal.getName()).thenReturn("testUser");
    var context = mock(JwtEncodingContext.class);
    when(context.getPrincipal()).thenReturn(principal);
    when(context.getClaims()).thenReturn(claims);
    when(context.getTokenType()).thenReturn(tokenType);
    return context;
  }
}

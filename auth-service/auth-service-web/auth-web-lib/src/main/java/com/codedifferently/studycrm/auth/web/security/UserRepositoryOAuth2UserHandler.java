package com.codedifferently.studycrm.auth.web.security;

import com.codedifferently.studycrm.auth.domain.User;
import com.codedifferently.studycrm.auth.domain.UserRepository;
import java.util.function.Consumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public final class UserRepositoryOAuth2UserHandler implements Consumer<OAuth2User> {

  @Autowired private UserRepository userRepository;

  @Override
  public void accept(OAuth2User user) {
    // Capture user in a local data store on first authentication
    if (this.userRepository.findByUsername(user.getName()) == null) {
      var userToSave =
          User.builder()
              .username(user.getName())
              .email(user.getAttribute("email"))
              .password("{noop}" + user.getName())
              .firstName(user.getAttribute("given_name"))
              .lastName(user.getAttribute("family_name"))
              .build();
      this.userRepository.save(userToSave);
    }
  }
}

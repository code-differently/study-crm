package com.codedifferently.studycrm.auth.web.security;

import com.codedifferently.studycrm.auth.domain.User;
import com.codedifferently.studycrm.auth.domain.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.stereotype.Service;

@Service
public class OidcUserInfoService {

    @Autowired
    private UserRepository userRepository;

    public OidcUserInfo loadUser(String username) {
        User user = this.userRepository.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        var roles = String.join(" ", user.getAuthorities()
                .stream()
                .map(a -> a.getAuthority())
                .toList());
        return OidcUserInfo.builder()
                .subject(username)
                .name(String.format("%s %s", user.getFirstName(), user.getLastName()))
                .givenName(user.getFirstName())
                .familyName(user.getLastName())
                .email(user.getEmail())
                .claim("https://studycrm.com/roles", roles)
                .build();
    }

}
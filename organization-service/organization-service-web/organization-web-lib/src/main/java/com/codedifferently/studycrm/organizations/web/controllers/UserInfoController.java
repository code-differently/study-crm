package com.codedifferently.studycrm.organizations.web.controllers;

import com.codedifferently.studycrm.organizations.api.web.UserInfoResponse;
import com.codedifferently.studycrm.organizations.domain.OrganizationService;
import com.codedifferently.studycrm.organizations.domain.User;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserInfoController {

  @Autowired private OrganizationService organizationService;

  @GetMapping("/user")
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<UserInfoResponse> getUserInfo(Authentication authentication) {
    User user = organizationService.findUserByUsername(authentication.getName());
    return ResponseEntity.ok(
        UserInfoResponse.builder()
            .username(user.getUsername())
            .email(user.getEmail())
            .firstName(user.getFirstName())
            .lastName(user.getLastName())
            .defaultOrganizationId(user.getDefaultOrganizationId().toString())
            .organizationIds(Collections.emptyList())
            .build());
  }
}

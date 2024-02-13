package com.codedifferently.studycrm.organizations.api.web;

import java.util.List;
import java.util.UUID;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserInfoResponse {

  private String username;

  private String email;

  private String firstName;

  private String lastName;

  private String defaultOrganizationId;

  private List<UUID> organizationIds;
}

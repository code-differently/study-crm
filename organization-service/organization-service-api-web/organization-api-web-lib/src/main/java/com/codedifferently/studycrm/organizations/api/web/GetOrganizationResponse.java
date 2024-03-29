package com.codedifferently.studycrm.organizations.api.web;

import java.util.UUID;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Generated
public class GetOrganizationResponse {

  private UUID organizationId;

  private String name;
}

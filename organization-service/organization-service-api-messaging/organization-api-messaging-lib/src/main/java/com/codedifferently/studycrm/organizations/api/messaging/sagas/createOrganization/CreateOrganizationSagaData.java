package com.codedifferently.studycrm.organizations.api.messaging.sagas.createOrganization;

import com.codedifferently.studycrm.organizations.api.web.UserDetails;
import com.codedifferently.studycrm.organizations.domain.Organization;
import java.util.UUID;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateOrganizationSagaData {

  private Organization organization;

  private UserDetails userDetails;

  private UUID userId;

  private UUID organizationId;
}

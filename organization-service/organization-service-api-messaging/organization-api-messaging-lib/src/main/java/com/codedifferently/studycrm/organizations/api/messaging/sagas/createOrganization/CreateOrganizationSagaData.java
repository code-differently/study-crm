package com.codedifferently.studycrm.organizations.api.messaging.sagas.createOrganization;

import com.codedifferently.studycrm.organizations.domain.Organization;
import com.codedifferently.studycrm.organizations.api.web.UserDetails;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateOrganizationSagaData {

    private Organization organization;

    private UserDetails userDetails;

    private String userId;

    private String organizationId;

}

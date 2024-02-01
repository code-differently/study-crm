package com.codedifferently.studycrm.organizations.api.web;

import java.util.UUID;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrganizationResponse {

    private UUID organizationId;

}
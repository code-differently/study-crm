package com.codedifferently.studycrm.organizations.api.web;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetOrganizationResponse {

    private String organizationId;

    private String name;

}

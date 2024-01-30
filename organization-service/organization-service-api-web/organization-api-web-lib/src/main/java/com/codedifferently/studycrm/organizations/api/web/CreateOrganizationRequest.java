package com.codedifferently.studycrm.organizations.api.web;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrganizationRequest {

    @Getter
    private String organizationName;

    @Getter
    private UserDetails userDetails;

}
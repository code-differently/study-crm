package com.codedifferently.studycrm.organizations.domain;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateOrganizationAndUserResult {

    private Organization organization;

    private User user;

}

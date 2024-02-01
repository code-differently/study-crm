package com.codedifferently.studycrm.organizations.api.web;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrganizationRequest {

    @Getter
    @NotBlank
    private String organizationName;

    @Getter
    private UserDetails userDetails;

}
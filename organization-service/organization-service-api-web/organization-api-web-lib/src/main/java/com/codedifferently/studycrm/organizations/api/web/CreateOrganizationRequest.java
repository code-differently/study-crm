package com.codedifferently.studycrm.organizations.api.web;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateOrganizationRequest {

    @Getter
    @NotBlank(message = "Organization name is required")
    private String organizationName;

    @Getter
    @Valid
    private UserDetails userDetails;

}
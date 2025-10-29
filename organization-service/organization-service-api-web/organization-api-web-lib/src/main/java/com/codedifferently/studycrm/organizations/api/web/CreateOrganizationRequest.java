package com.codedifferently.studycrm.organizations.api.web;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateOrganizationRequest {

  @Getter
  @NotBlank(message = "Organization name is required") private String organizationName;

  @Getter
  @Valid @NotNull(message = "User details are required") private UserDetails userDetails;
}

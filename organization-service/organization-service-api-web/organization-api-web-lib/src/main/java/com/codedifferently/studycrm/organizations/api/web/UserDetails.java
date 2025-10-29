package com.codedifferently.studycrm.organizations.api.web;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDetails {

  @Getter
  @NotBlank(message = "Username is required") private String username;

  @Getter
  @NotBlank(message = "Email is required") private String email;

  @Getter private String password;

  @Getter private String firstName;

  @Getter private String lastName;
}

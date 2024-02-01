package com.codedifferently.studycrm.auth.api.web;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CreateUserRequest {

  private String username;

  private String password;

  private String firstName;

  private String lastName;

  private String email;
}

package com.codedifferently.studycrm.organizations.api.web;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDetails {

    @Getter
    @NotBlank
    private String username;

    @Getter
    @NotBlank
    private String email;

    @Getter
    private String password;

    @Getter
    private String firstName;

    @Getter
    private String lastName;

}

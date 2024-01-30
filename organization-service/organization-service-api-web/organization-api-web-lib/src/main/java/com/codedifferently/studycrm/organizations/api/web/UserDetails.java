package com.codedifferently.studycrm.organizations.api.web;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDetails {

    @Getter
    private String username;

    @Getter
    private String email;

    @Getter
    private String password;

    @Getter
    private String firstName;

    @Getter
    private String lastName;

}

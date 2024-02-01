package com.codedifferently.studycrm.contacts.api.web;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateContactRequest {

    private String firstName;

    private String lastName;

}
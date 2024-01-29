package com.codedifferently.studycrm.contacts.api.web;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetContactResponse {

    private String contactId;

    private String firstName;

    private String lastName;

}

package com.codedifferently.studycrm.contacts.api.web;

import java.util.UUID;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateContactResponse {

    private UUID contactId;

}
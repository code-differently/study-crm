package com.codedifferently.studycrm.contacts.api.web;

import java.util.UUID;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Generated
public class GetContactResponse {

  private UUID contactId;

  private String firstName;

  private String lastName;
}

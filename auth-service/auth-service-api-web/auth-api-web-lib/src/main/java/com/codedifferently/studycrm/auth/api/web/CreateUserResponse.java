package com.codedifferently.studycrm.auth.api.web;

import java.util.UUID;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Generated
public class CreateUserResponse {

  @Getter private UUID userId;
}

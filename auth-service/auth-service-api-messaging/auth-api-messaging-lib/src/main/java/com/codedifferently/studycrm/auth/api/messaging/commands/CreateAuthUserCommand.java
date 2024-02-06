package com.codedifferently.studycrm.auth.api.messaging.commands;

import io.eventuate.tram.commands.common.Command;
import java.util.List;
import java.util.UUID;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Generated
public class CreateAuthUserCommand implements Command {

  private UUID userId;

  private String username;

  private String email;

  private String password;

  private String firstName;

  private String lastName;

  @Singular private List<String> grantedAuthorities;
}

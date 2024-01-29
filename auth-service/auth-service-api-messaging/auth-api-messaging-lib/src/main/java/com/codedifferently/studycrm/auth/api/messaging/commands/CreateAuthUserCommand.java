package com.codedifferently.studycrm.auth.api.messaging.commands;

import java.util.List;
import io.eventuate.tram.commands.common.Command;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateAuthUserCommand implements Command {

    private String userId;

    private String username;

    private String email;

    private String password;

    private String firstName;

    private String lastName;

    private List<String> grantedAuthorities;

}

package com.codedifferently.studycrm.auth.api.web;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CreateUserResponse {

    @Getter
    private String userId;

}

package com.codedifferently.studycrm.auth.web.controllers;

import com.codedifferently.studycrm.auth.api.web.CreateUserRequest;
import com.codedifferently.studycrm.auth.api.web.CreateUserResponse;
import com.codedifferently.studycrm.auth.domain.User;
import com.codedifferently.studycrm.auth.domain.UserRespository;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class UsersController {

    private final UserRespository userRepository;

    public UsersController(UserRespository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/users")
    public CreateUserResponse createUser(@RequestBody CreateUserRequest createUserRequest) {
        var user = User.builder()
                .username(createUserRequest.getUsername())
                .password(createUserRequest.getPassword())
                .firstName(createUserRequest.getFirstName())
                .lastName(createUserRequest.getLastName())
                .email(createUserRequest.getEmail())
                .build();
        User newUser = userRepository.save(user);
        var response = CreateUserResponse.builder()
                .userId(newUser.getUuid())
                .build();
        return response;
    }

}
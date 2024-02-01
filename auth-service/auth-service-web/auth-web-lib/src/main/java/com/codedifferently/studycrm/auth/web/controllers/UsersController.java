package com.codedifferently.studycrm.auth.web.controllers;

import com.codedifferently.studycrm.auth.api.web.CreateUserRequest;
import com.codedifferently.studycrm.auth.api.web.CreateUserResponse;
import com.codedifferently.studycrm.auth.domain.User;
import com.codedifferently.studycrm.auth.domain.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsersController {

    private final UserRepository userRepository;

    @Autowired
    public UsersController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping(value = "/users")
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
                .userId(newUser.getId())
                .build();
        return response;
    }

}
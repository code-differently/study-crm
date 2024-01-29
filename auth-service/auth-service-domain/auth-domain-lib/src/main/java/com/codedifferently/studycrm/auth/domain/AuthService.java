package com.codedifferently.studycrm.auth.domain;

import jakarta.transaction.Transactional;

public class AuthService {

    private UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public User saveUser(User user) {
        return userRepository.save(user);
    }
}

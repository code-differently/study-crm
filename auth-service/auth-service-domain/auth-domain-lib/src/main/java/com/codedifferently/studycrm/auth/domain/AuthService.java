package com.codedifferently.studycrm.auth.domain;

import java.util.stream.Collectors;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserAuthorityRepository userAuthorityRepository;

    @Transactional
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public boolean userExists(String username) {
        return userRepository.existsByUsername(username);
    }

    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Transactional
    public void addUserAuthorities(User user, List<String> authoritiesToAdd) {
        List<UserAuthority> userAuthorities = user.getAuthorities();
        var authoritiesSet = new HashSet<String>(
                userAuthorities.stream().map(UserAuthority::getAuthority).collect(Collectors.toList()));
        var newAuthorities = new HashSet<String>();
        for (String authority : authoritiesToAdd) {
            if (!authoritiesSet.contains(authority)) {
                newAuthorities.add(authority);
            }
        }
        userAuthorityRepository.saveAll(newAuthorities.stream()
                .map(authority -> UserAuthority.builder().authority(authority).user(user).build())
                .collect(Collectors.toList()));
    }
}

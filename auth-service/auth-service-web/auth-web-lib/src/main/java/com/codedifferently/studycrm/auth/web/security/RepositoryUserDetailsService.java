package com.codedifferently.studycrm.auth.web.security;

import com.codedifferently.studycrm.auth.domain.UserRepository;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class RepositoryUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        var user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        Collection<SimpleGrantedAuthority> authorities = user.getAuthorities()
                .stream()
                .map(a -> new SimpleGrantedAuthority(a.getAuthority()))
                .toList();
        return new User(user.getUsername(), user.getPassword(), authorities);
    }
}

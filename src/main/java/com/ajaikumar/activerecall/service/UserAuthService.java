package com.ajaikumar.activerecall.service;

import com.ajaikumar.activerecall.entity.UserEntity;
import com.ajaikumar.activerecall.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserAuthService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userFromDB = userRepository.findUserForAuthentication(username);
        if(userFromDB != null){
            return User.builder()
                    .username(userFromDB.getUsername())
                    .password(userFromDB.getPassword())
                    .build();
        }
        throw new UsernameNotFoundException("User name not found " + username);
    }

}

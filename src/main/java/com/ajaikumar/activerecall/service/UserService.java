package com.ajaikumar.activerecall.service;

import com.ajaikumar.activerecall.entity.UserEntity;
import com.ajaikumar.activerecall.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    private ArrayList<UserEntity> allUsers = new ArrayList<>();

    public ResponseEntity<UserEntity> createUser(UserEntity userEntity){
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        UserEntity savedUser = userRepository.save(userEntity);
        return new ResponseEntity<>(savedUser, HttpStatus.OK);
    }

    public ResponseEntity<List<UserEntity>> getAllUsers(){
        List<UserEntity> allAvailableUsers = userRepository.findAll();
        return new ResponseEntity<>(allAvailableUsers, HttpStatus.OK);
    }

    public boolean updateUser(UserEntity userEntity){
        userRepository.save(userEntity);
        return true;
    }

    public UserEntity findByUsername(String username){
        return userRepository.findByUsername(username);
    }

/*    public ResponseEntity<String> signIn(UserEntity userEntity) {
        UserEntity userFromDb = this.findByUsername(userEntity.getUsername());
        if(userFromDb == null) {
            return new ResponseEntity<>("User name not found !!!", HttpStatus.ACCEPTED);
        }
        if(userFromDb.getPassword().equals(userEntity.getPassword())){
            return new ResponseEntity<>("User signed in !!!", HttpStatus.ACCEPTED);
        }else{
            return new ResponseEntity<>("Wrong password entered !!!", HttpStatus.UNAUTHORIZED);
        }
    }*/

    public ResponseEntity<String> signin(UserEntity userEntity) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userEntity.getUsername(), userEntity.getPassword())
        );
        if(authentication.isAuthenticated()){
            System.out.println("Login done");
            return new ResponseEntity<>("User successfully signed in !!!", HttpStatus.ACCEPTED);
        }else{
            System.out.println("Login failed");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }
}

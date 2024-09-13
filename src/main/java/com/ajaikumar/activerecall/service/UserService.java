package com.ajaikumar.activerecall.service;

import com.ajaikumar.activerecall.entity.UserEntity;
import com.ajaikumar.activerecall.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private ArrayList<UserEntity> allUsers = new ArrayList<>();

    public ResponseEntity<UserEntity> createUser(UserEntity userEntity){
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
}

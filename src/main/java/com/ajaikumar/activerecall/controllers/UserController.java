package com.ajaikumar.activerecall.controllers;

import com.ajaikumar.activerecall.entity.UserEntity;
import com.ajaikumar.activerecall.service.UserAuthService;
import com.ajaikumar.activerecall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserAuthService userAuthService;

    @GetMapping
    public ResponseEntity<?> getUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserEntity>> getAllUsers(){
        return userService.getAllUsers();
    }

    @PostMapping
    public  ResponseEntity<?> createUsers(@RequestBody UserEntity userEntity){
        return userService.createUser(userEntity);
    }

    @PostMapping("/signin")
    public ResponseEntity<String> signInUser(@RequestBody UserEntity userEntity){
       // return userAuthService.signin(userEntity);
        return userService.signin(userEntity);
    }
}

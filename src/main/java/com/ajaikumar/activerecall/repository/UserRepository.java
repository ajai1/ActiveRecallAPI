package com.ajaikumar.activerecall.repository;

import com.ajaikumar.activerecall.entity.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface UserRepository extends MongoRepository<UserEntity, String> {
    @Query(value = "{ 'username': ?0 }", fields = "{ }")
    UserEntity findByUsername(String username);

    @Query(value = "{ 'username': ?0 }")
    UserEntity findUserForAuthentication(String username);

}

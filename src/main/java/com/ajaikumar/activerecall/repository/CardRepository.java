package com.ajaikumar.activerecall.repository;

import com.ajaikumar.activerecall.entity.CardEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CardRepository extends MongoRepository<CardEntity, String> {

    //List<CardEntity> findByDeckid(String deckid);
}

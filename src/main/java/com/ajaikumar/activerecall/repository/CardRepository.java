package com.ajaikumar.activerecall.repository;

import com.ajaikumar.activerecall.entity.CardEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CardRepository extends MongoRepository<CardEntity, String> {

    List<CardEntity> findByDeckId(String deckId);

    // Custom method to delete all cards by deckId
    void deleteByDeckId(String deckId);
}

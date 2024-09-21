package com.ajaikumar.activerecall.repository;

import com.ajaikumar.activerecall.entity.DeckEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DeckRepository extends MongoRepository<DeckEntity, String> {
   // @Query(value = "{ 'deckname': ?0 }", fields = "{ 'cards' : 0 }")
    DeckEntity findOneByDeckname(String deckname);

    List<DeckEntity> findByUserId(String userId);

    // Find a deck by userId and deckname
    Optional<DeckEntity> findByUserIdAndDeckname(String userId, String deckname);

}

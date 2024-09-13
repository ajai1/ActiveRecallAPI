package com.ajaikumar.activerecall.repository;

import com.ajaikumar.activerecall.entity.DeckEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface DeckRepository extends MongoRepository<DeckEntity, String> {

    DeckEntity findOneByDeckname(String deckname);
}

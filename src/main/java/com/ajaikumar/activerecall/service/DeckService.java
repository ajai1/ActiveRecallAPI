package com.ajaikumar.activerecall.service;

import com.ajaikumar.activerecall.entity.DeckEntity;
import com.ajaikumar.activerecall.entity.UserEntity;
import com.ajaikumar.activerecall.repository.DeckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeckService {

    @Autowired
    DeckRepository deckRepository;

    @Autowired
    UserService userService;


    public ResponseEntity<DeckEntity> createDeck(DeckEntity deck, String username){
        UserEntity user = userService.findByUsername(username);
        if(user == null) return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        DeckEntity savedDeck = deckRepository.save(deck);
        user.getDecks().add(savedDeck);
        userService.updateUser(user);
        return new ResponseEntity<>(savedDeck, HttpStatus.CREATED);
    }

    public ResponseEntity<List<DeckEntity>> getAllDecks(String username){
        UserEntity user = userService.findByUsername(username);
        if(user == null) return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        List<DeckEntity> allDecksForUser = user.getDecks();
        return new ResponseEntity<List<DeckEntity>>(allDecksForUser, HttpStatus.FOUND);
    }

    public boolean updateDecks(DeckEntity deckEntity){
        deckRepository.save(deckEntity);
        return true;
    }

    public DeckEntity findDeckByName(String username, String deckname){
        UserEntity user = userService.findByUsername(username);
        if(user == null) return null;
        for(DeckEntity deck : user.getDecks()){
            if(deck.getDeckname().equals(deckname)){
                return deck;
            }
        }
        return null;
    }
}

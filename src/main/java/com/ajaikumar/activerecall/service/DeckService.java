package com.ajaikumar.activerecall.service;

import com.ajaikumar.activerecall.entity.CardEntity;
import com.ajaikumar.activerecall.entity.DeckEntity;
import com.ajaikumar.activerecall.entity.UserEntity;
import com.ajaikumar.activerecall.repository.CardRepository;
import com.ajaikumar.activerecall.repository.DeckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DeckService {

    @Autowired
    DeckRepository deckRepository;

    @Autowired
    UserService userService;

    @Autowired
    CardRepository cardRepository;


    public ResponseEntity<DeckEntity> createDeck(DeckEntity deck){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!authentication.isAuthenticated()) return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);

        UserEntity user = userService.findByUsername(authentication.getName());
        if(user == null) return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        DeckEntity savedDeck = deckRepository.save(deck);
        user.getDecks().add(savedDeck);
        userService.updateUser(user);
        return new ResponseEntity<>(savedDeck, HttpStatus.CREATED);
    }

    public ResponseEntity<List<DeckEntity>> getAllDecks(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!authentication.isAuthenticated()) return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);

        UserEntity user = userService.findByUsername(authentication.getName());
        if(user == null) return new ResponseEntity<>(new ArrayList<>(), HttpStatus.NOT_FOUND);
        List<DeckEntity> allDecksForUser = user.getDecks();
        return new ResponseEntity<List<DeckEntity>>(allDecksForUser, HttpStatus.FOUND);
    }

    public boolean updateDecks(DeckEntity deckEntity){
        deckRepository.save(deckEntity);
        return true;
    }

    public ResponseEntity<DeckEntity> findDeckByName(String deckname){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!authentication.isAuthenticated()) return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        UserEntity user = userService.findByUsername(authentication.getName());
        if(user == null) return null;
        if(user.getDecks().size() == 0) return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        for(DeckEntity deck : user.getDecks()){
            if(deck !=null && deck.getDeckname().equals(deckname)){
                return new ResponseEntity<>(deck, HttpStatus.FOUND);
            }
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<Boolean> resetCardsIntervalAndRepetitions(String deckname){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!authentication.isAuthenticated()) return new ResponseEntity<>(false, HttpStatus.UNAUTHORIZED);
        DeckEntity deck = findDeckByName(deckname).getBody();
        if(deck == null) return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        for(CardEntity cardEntity : deck.getCards()){
            cardEntity.setRepetition(0);
            cardEntity.setInterval(1);
            cardRepository.save(cardEntity);
        }
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    public ResponseEntity<DeckEntity> updateDeck(DeckEntity deck){
        Optional<DeckEntity> deckOptionalDB = deckRepository.findById(deck.getId());
        if(deckOptionalDB.isPresent()){
            DeckEntity deckFromDB = deckOptionalDB.get();
            deckFromDB.setDeckname(deck.getDeckname());
            deckRepository.save(deckFromDB);
            return new ResponseEntity<>(deckFromDB, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<String> deleteDeck(DeckEntity deck) {
        Optional<DeckEntity> deckFromDB = deckRepository.findById(deck.getId());
        if(deckFromDB.isPresent()){
            for(CardEntity card : deckFromDB.get().getCards()){
                cardRepository.deleteById(card.getId());
            }
            deckRepository.deleteById(deck.getId());
            return new ResponseEntity<>("Deck deleted", HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Deck Id not found", HttpStatus.NOT_FOUND);
        }
    }
}

package com.ajaikumar.activerecall.service;

import com.ajaikumar.activerecall.entity.CardEntity;
import com.ajaikumar.activerecall.entity.DeckEntity;
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
import java.util.stream.Collectors;

@Service
public class CardService {

    @Autowired
    CardRepository cardRepository;

    @Autowired
    DeckRepository deckRepository;

    @Autowired
    DeckService deckService;

    public ResponseEntity<CardEntity> createCard (CardEntity cardEntity, String deckname){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!authentication.isAuthenticated()) return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        DeckEntity deckEntity = deckService.findDeckByName(deckname).getBody();
        if(deckEntity != null){
            CardEntity savedCard = cardRepository.save(cardEntity);
            deckEntity.getCards().add(savedCard);
            deckService.updateDecks(deckEntity);
            return new ResponseEntity<>(savedCard, HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    public CardEntity updateCard (CardEntity cardEntity){
        Optional<CardEntity> fromDb = cardRepository.findById(cardEntity.getId());
        if(fromDb.isPresent()){
            CardEntity card = fromDb.get();
            card.setHeader(cardEntity.getHeader());
            card.setBriefstatement(cardEntity.getBriefstatement());
            card.setText(cardEntity.getText());
            card.setCanvas(cardEntity.getCanvas());
            card.setRecall(cardEntity.getRecall());
            card.setEaseFactor(cardEntity.getEaseFactor());
            card.setInterval(cardEntity.getInterval());
            card.setRepetition(cardEntity.getRepetition());
            return cardRepository.save(card);
        }
        return null;
    }

    public List<CardEntity> getAllCardsFromDeck (String deckname){
        DeckEntity deckEntity = deckService.findDeckByName(deckname).getBody();
        if(deckEntity != null){
            return  deckEntity.getCards();
        }else{
            return new ArrayList<>();
        }
    }

    public boolean deleteCard(CardEntity cardEntity, String deckname){
        DeckEntity deckEntity = deckService.findDeckByName(deckname).getBody();
        if(deckEntity != null){
            List<CardEntity> remainingCards = deckEntity.getCards().stream().filter(card -> !card.getId().equals(cardEntity.getId())).collect(Collectors.toList());
            deckEntity.setCards(remainingCards);
            deckRepository.save(deckEntity);
            cardRepository.deleteById(cardEntity.getId());
            return true;
        }else{
            return false;
        }
    }
}

package com.ajaikumar.activerecall.service;

import com.ajaikumar.activerecall.entity.CardEntity;
import com.ajaikumar.activerecall.entity.DeckEntity;
import com.ajaikumar.activerecall.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CardService {

    @Autowired
    CardRepository cardRepository;

    @Autowired
    DeckService deckService;

    public CardEntity createCard (CardEntity cardEntity, String username, String deckname){
        DeckEntity deckEntity = deckService.findDeckByName(username, deckname);
        if(deckEntity != null){
            CardEntity savedCard = cardRepository.save(cardEntity);
            deckEntity.getCards().add(savedCard);
            deckService.updateDecks(deckEntity);
            return savedCard;
        }else{
            return null;
        }
    }

    public List<CardEntity> getAllCardsFromDeck (String username, String deckname){
        DeckEntity deckEntity = deckService.findDeckByName(username, deckname);
        if(deckEntity != null){
            return  deckEntity.getCards();
        }else{
            return new ArrayList<>();
        }
    }
}

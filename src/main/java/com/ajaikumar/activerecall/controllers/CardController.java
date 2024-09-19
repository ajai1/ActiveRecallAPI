package com.ajaikumar.activerecall.controllers;

import com.ajaikumar.activerecall.entity.CardEntity;
import com.ajaikumar.activerecall.service.CardService;
import com.ajaikumar.activerecall.service.DeckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cards")
public class CardController {

    @Autowired
    CardService cardService;

    @GetMapping("/{deckname}")
    public List<CardEntity> getAllCardsFromDeck(@PathVariable String deckname){
        return cardService.getAllCardsFromDeck(deckname);
    }

    @PostMapping("/{deckname}")
    public ResponseEntity<CardEntity> createCard(@RequestBody CardEntity cardEntity, @PathVariable String deckname){
        return cardService.createCard(cardEntity,deckname);
    }

    @PutMapping("/{deckname}")
    public CardEntity updateCard(@RequestBody CardEntity cardEntity, @PathVariable String deckname){
        return cardService.updateCard(cardEntity);
    }

    @DeleteMapping("/{deckname}")
    public boolean deleteCard(@RequestBody CardEntity cardEntity, @PathVariable String deckname){
        return cardService.deleteCard(cardEntity, deckname);
    }
}

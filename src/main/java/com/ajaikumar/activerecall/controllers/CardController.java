package com.ajaikumar.activerecall.controllers;

import com.ajaikumar.activerecall.entity.CardEntity;
import com.ajaikumar.activerecall.service.CardService;
import com.ajaikumar.activerecall.service.DeckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cards")
public class CardController {

    @Autowired
    CardService cardService;

    @GetMapping("/{username}/{deckname}")
    public List<CardEntity> getAllCardsFromDeck(@PathVariable String username, @PathVariable String deckname){
        return cardService.getAllCardsFromDeck(username, deckname);
    }

    @PostMapping("/{username}/{deckname}")
    public CardEntity createCard(@RequestBody CardEntity cardEntity, @PathVariable String username, @PathVariable String deckname){
        return cardService.createCard(cardEntity, username, deckname);
    }
}

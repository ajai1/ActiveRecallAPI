package com.ajaikumar.activerecall.controllers;

import com.ajaikumar.activerecall.entity.DeckEntity;
import com.ajaikumar.activerecall.entity.UserEntity;
import com.ajaikumar.activerecall.service.DeckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/decks")
public class DeckController {

    @Autowired
    DeckService deckService;

    @GetMapping("/{username}")
    public ResponseEntity<List<DeckEntity>> getDecks(@PathVariable String username) {
        return deckService.getAllDecks(username);
    }

    @GetMapping("/{username}/{deckname}")
    public DeckEntity getDeckByName(@PathVariable String username, @PathVariable String deckname){
        return deckService.findDeckByName(username, deckname);
    }

    @PostMapping("/{username}")
    public ResponseEntity<DeckEntity> createDeck(@RequestBody DeckEntity deck, @PathVariable String username){
        return deckService.createDeck(deck, username);
    }

}

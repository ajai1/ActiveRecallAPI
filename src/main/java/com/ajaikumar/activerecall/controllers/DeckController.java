package com.ajaikumar.activerecall.controllers;

import com.ajaikumar.activerecall.entity.DeckEntity;
import com.ajaikumar.activerecall.entity.DeckResponseEntity;
import com.ajaikumar.activerecall.entity.UserEntity;
import com.ajaikumar.activerecall.service.DeckService;
import com.ajaikumar.activerecall.service.SchedulerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/decks")
public class DeckController {

    @Autowired
    DeckService deckService;

    @Autowired
    SchedulerService schedulerService;

    @GetMapping
    public ResponseEntity<List<DeckEntity>> getDecks() {
        return deckService.getAllDecks();
    }

    @GetMapping("/{deckname}")
    public ResponseEntity<DeckResponseEntity> getDeckByName(@PathVariable String deckname){
        DeckEntity dbDeck = deckService.findDeckByName(deckname).getBody();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!authentication.isAuthenticated()) return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        boolean isDeckScheduled = schedulerService.isSchedulerRunningForDeck(deckname);
        DeckResponseEntity deckResponse = new DeckResponseEntity(dbDeck, isDeckScheduled);
        return new ResponseEntity<>(deckResponse, HttpStatus.FOUND);
    }

    @PostMapping
    public ResponseEntity<DeckEntity> createDeck(@RequestBody DeckEntity deck){
        return deckService.createDeck(deck);
    }

    @PutMapping
    public ResponseEntity<DeckEntity> updateDeck(@RequestBody DeckEntity deck){
        return deckService.updateDeck(deck);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteDeck(@RequestBody DeckEntity deck){
        return deckService.deleteDeck(deck);
    }

}

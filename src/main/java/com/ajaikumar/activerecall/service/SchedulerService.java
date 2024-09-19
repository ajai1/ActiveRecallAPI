package com.ajaikumar.activerecall.service;

import com.ajaikumar.activerecall.entity.CardEntity;
import com.ajaikumar.activerecall.entity.DeckEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class SchedulerService {
    private final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
    private final Map<String, Boolean> userDeckResetJobMap = new ConcurrentHashMap<>();

    @Autowired
    DeckService deckService;

    @Autowired
    CardService cardService;

    public ResponseEntity<Boolean> scheduleTask(String deckname){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!authentication.isAuthenticated()) return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);

        String jobKey = createJobKey(authentication.getName(), deckname);
        if(userDeckResetJobMap.getOrDefault(jobKey, false)){
            return new ResponseEntity<>(false, HttpStatus.OK);
        }
        userDeckResetJobMap.put(jobKey, true);
        DeckEntity deck = deckService.findDeckByName(deckname).getBody();
        if(deck == null) {
            System.out.println("No such Deck Found !!!");
            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        }
        executorService.schedule(() -> runResettingIntervals(authentication.getName(), deckname, deck), 10, TimeUnit.SECONDS);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    private void runResettingIntervals(String username, String deckname, DeckEntity deck){
        String jobKey = createJobKey(username, deckname);
        try {
            // Simulate the scheduled job running for the userName + deckName
            System.out.println("Job for user: " + username + ", deck: " + deckname + " started...");
            for(CardEntity cardEntity : deck.getCards()){
                cardEntity.setRepetition(0);
                cardEntity.setInterval(1);
                cardService.updateCard(cardEntity);
                System.out.println("Card Updated !!!");
            }
            System.out.println("Job for user: " + username + ", deck: " + deckname + " completed.");

        } finally {
            // Mark the job for this userName + deckName as completed
            userDeckResetJobMap.remove(jobKey);
        }
    }

    private String createJobKey(String username, String deckname) {
        return username + "_" + deckname;
    }

    public boolean isSchedulerRunningForDeck(String deckname){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!authentication.isAuthenticated()) return false;
        String jobKey = createJobKey(authentication.getName(), deckname);
        return userDeckResetJobMap.getOrDefault(jobKey, false);
    }
}

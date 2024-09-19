package com.ajaikumar.activerecall.controllers;

import com.ajaikumar.activerecall.entity.ScheduleEntity;
import com.ajaikumar.activerecall.service.SchedulerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    private final SchedulerService schedulerService;

    @Autowired
    public ScheduleController(SchedulerService schedulerService) {
        this.schedulerService = schedulerService;
    }

    @GetMapping("/{deckname}")
    public boolean isSchedulerRunning(@PathVariable String deckname){
        return schedulerService.isSchedulerRunningForDeck(deckname);
    }

    @PostMapping
    public ResponseEntity<String> scheduleThisJob(@RequestBody ScheduleEntity scheduleEntity){
        boolean isScheduled = schedulerService.scheduleTask(scheduleEntity.getDeckname()).getBody();
        if (isScheduled) {
            return ResponseEntity.ok("Job scheduled successfully for user: deck: " + scheduleEntity.getDeckname());
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Job for user: deck: " + scheduleEntity.getDeckname() + " is already running. Please wait.");
        }
    }

}

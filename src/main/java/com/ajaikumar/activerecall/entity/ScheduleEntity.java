package com.ajaikumar.activerecall.entity;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class ScheduleEntity {
    private String username;
    private String deckname;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDeckname() {
        return deckname;
    }

    public void setDeckname(String deckname) {
        this.deckname = deckname;
    }
}

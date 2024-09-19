package com.ajaikumar.activerecall.entity;

public class DeckResponseEntity {
    public DeckResponseEntity(DeckEntity deckEntity, boolean paused) {
        this.deckEntity = deckEntity;
        this.paused = paused;
    }

    private DeckEntity deckEntity;
    private boolean paused;

    public DeckEntity getDeckEntity() {
        return deckEntity;
    }

    public void setDeckEntity(DeckEntity deckEntity) {
        this.deckEntity = deckEntity;
    }

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }
}

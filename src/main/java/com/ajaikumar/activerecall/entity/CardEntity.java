package com.ajaikumar.activerecall.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "cards")
public class CardEntity {
    @Id
    private String id;
    private String header;
    private String briefstatement;
    private int recall;
    private String text;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getBriefstatement() {
        return briefstatement;
    }

    public void setBriefstatement(String briefstatement) {
        this.briefstatement = briefstatement;
    }

    public int getRecall() {
        return recall;
    }

    public void setRecall(int recall) {
        this.recall = recall;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

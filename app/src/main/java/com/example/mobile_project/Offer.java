package com.example.mobile_project;

public class Offer {
    private String title;
    private String description;
    private String name;
    private String offerType;

    public Offer() {} // Empty constructor for Firestore

    public Offer(String title, String description, String name, String offerType) {
        this.title = title;
        this.description = description;
        this.name = name;
        this.offerType = offerType;
    }

    // Getters and setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOfferType() {
        return offerType;
    }

    public void setOfferType(String offerType) {
        this.offerType = offerType;
    }
}


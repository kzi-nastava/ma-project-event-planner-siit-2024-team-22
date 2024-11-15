package com.example.siit_2024_team_22_ma.models;

import java.io.Serializable;

public class Event implements Serializable {
    private int image;
    private String title;
    private String description;
    private boolean isPrivate;


    public Event(int image, String title, String description, boolean isPrivate) {
        this.image = image;
        this.title = title;
        this.description = description;
        this.isPrivate = isPrivate;
    }

    public int getImage() { return image; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public boolean isPrivate() { return isPrivate; }
}
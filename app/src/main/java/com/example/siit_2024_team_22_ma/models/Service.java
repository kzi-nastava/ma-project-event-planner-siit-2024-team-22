package com.example.siit_2024_team_22_ma.models;

import java.io.Serializable;

public class Service implements Serializable {
    private String title;
    private String description;
    private String company;
    private String contact;
    private int imageResource;

    public Service(String title, String description, String company, String contact, int imageResource) {
        this.title = title;
        this.description = description;
        this.company = company;
        this.contact = contact;
        this.imageResource = imageResource;
    }

    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getCompany() { return company; }
    public String getContact() { return contact; }
    public int getImageResource() { return imageResource; }
}
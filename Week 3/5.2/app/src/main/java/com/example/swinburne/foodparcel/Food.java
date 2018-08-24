package com.example.swinburne.foodparcel;

import java.util.Date;

public class Food {
    private String name;
    private String imageURL;
    private String keywords;
    private Date date;
    private int rating;
    private String owners;

    public Food (String name, String imageURL, String keywords, Date date, int rating, String owners) {
        this.name = name;
        this.imageURL = imageURL;
        this.keywords = keywords;
        this.date = date;
        this.rating = rating;
        this.owners = owners;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getOwners() {
        return owners;
    }

    public void setOwners(String owners) {
        this.owners = owners;
    }
}

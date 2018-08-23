package com.example.swinburne.simplebooklist;

public class Book {

    private String title;
    private int imageRef;
    private int rating;

    public Book (String title, int imageRef, int rating) {
        this.title = title;
        this.imageRef = imageRef;
        this.rating = rating;
    }

    public String getTitle() {
        return title;
    }

    public int getImageRef() {
        return imageRef;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImageRef(int imageRef) {
        this.imageRef = imageRef;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}

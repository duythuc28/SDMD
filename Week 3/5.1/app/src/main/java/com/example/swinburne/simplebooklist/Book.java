package com.example.swinburne.simplebooklist;

public class Book {

    private String title;
    private int imageRef;

    public Book (String title, int imageRef) {
        this.title = title;
        this.imageRef = imageRef;
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
}

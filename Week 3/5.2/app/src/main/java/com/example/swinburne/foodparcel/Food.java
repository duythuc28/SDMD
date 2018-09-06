package com.example.swinburne.foodparcel;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Food implements Parcelable{
    private String name;
    private String imageURL;
    private String keywords;
    private Date date;
    private int rating;
    private String owners;
    private boolean shareable;

    public int getImageRef() {
        return imageRef;
    }

    public void setImageRef(int imageRef) {
        this.imageRef = imageRef;
    }

    private int imageRef;

    public boolean isShareable() {
        return shareable;
    }

    public void setShareable(boolean shareable) {
        this.shareable = shareable;
    }

    public Food (String name,String imageURL, int imageRef, String keywords, Date date, int rating, String owners) {
        this.name = name;
        this.imageURL = imageURL;
        this.imageRef = imageRef;
        this.keywords = keywords;
        this.date = date;
        this.rating = rating;
        this.owners = owners;
        this.shareable = false;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.imageURL);
        dest.writeString(this.keywords);
        dest.writeLong(this.date != null ? this.date.getTime() : -1);
        dest.writeInt(this.rating);
        dest.writeString(this.owners);
        dest.writeByte(this.shareable ? (byte) 1 : (byte) 0);
        dest.writeInt(this.imageRef);
    }

    protected Food(Parcel in) {
        this.name = in.readString();
        this.imageURL = in.readString();
        this.keywords = in.readString();
        long tmpDate = in.readLong();
        this.date = tmpDate == -1 ? null : new Date(tmpDate);
        this.rating = in.readInt();
        this.owners = in.readString();
        this.shareable = in.readByte() != 0;
        this.imageRef = in.readInt();
    }

    public static final Creator<Food> CREATOR = new Creator<Food>() {
        @Override
        public Food createFromParcel(Parcel source) {
            return new Food(source);
        }

        @Override
        public Food[] newArray(int size) {
            return new Food[size];
        }
    };
}

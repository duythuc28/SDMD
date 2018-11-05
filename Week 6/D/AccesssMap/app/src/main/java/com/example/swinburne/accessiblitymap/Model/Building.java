package com.example.swinburne.accessiblitymap.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Building implements Parcelable {
    @SerializedName("street_address")
    private String address;
    @SerializedName("lower_building_name")
    private String name;
    @SerializedName("block_id")
    private String blockId;
    @SerializedName("x_coordinate")
    private Double longitude;
    @SerializedName("y_coordinate")
    private Double latitude;
    @SerializedName("suburb")
    private String suburb;
    @SerializedName("accessibility_rating")
    private int rating;
    @SerializedName("accessibility_type")
    private String type;
    @SerializedName("accessibility_type_description")
    private String accessibilityDes;

    public Building(String address, String name, String blockId,
                    Double latitude, Double longitude,
                    String suburb, int rating, String type, String accessibilityDes) {
        this.address = address;
        this.name = name;
        this.blockId = blockId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.suburb = suburb;
        this.rating = rating;
        this.type = type;
        this.accessibilityDes = accessibilityDes;
    }

    public Building (BuildingDA buildingDA) {
        this.address = buildingDA.address;
        this.name = buildingDA.name;
        this.blockId = String.valueOf(buildingDA.blockId);
        this.latitude = buildingDA.latitude;
        this.longitude = buildingDA.longitude;
        this.suburb = buildingDA.suburb;
        this.rating = buildingDA.rating;
        this.type = buildingDA.type;
        this.accessibilityDes = buildingDA.accessibilityDes;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBlockId() {
        return blockId;
    }

    public void setBlockId(String blockId) {
        this.blockId = blockId;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getSuburb() {
        return suburb;
    }

    public void setSuburb(String suburb) {
        this.suburb = suburb;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAccessibilityDes() {
        return accessibilityDes;
    }

    public void setAccessibilityDes(String accessibilityDes) {
        this.accessibilityDes = accessibilityDes;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.address);
        dest.writeString(this.name);
        dest.writeString(this.blockId);
        dest.writeValue(this.longitude);
        dest.writeValue(this.latitude);
        dest.writeString(this.suburb);
        dest.writeInt(this.rating);
        dest.writeString(this.type);
        dest.writeString(this.accessibilityDes);
    }

    protected Building(Parcel in) {
        this.address = in.readString();
        this.name = in.readString();
        this.blockId = in.readString();
        this.longitude = (Double) in.readValue(Double.class.getClassLoader());
        this.latitude = (Double) in.readValue(Double.class.getClassLoader());
        this.suburb = in.readString();
        this.rating = in.readInt();
        this.type = in.readString();
        this.accessibilityDes = in.readString();
    }

    public static final Parcelable.Creator<Building> CREATOR = new Parcelable.Creator<Building>() {
        @Override
        public Building createFromParcel(Parcel source) {
            return new Building(source);
        }

        @Override
        public Building[] newArray(int size) {
            return new Building[size];
        }
    };
}

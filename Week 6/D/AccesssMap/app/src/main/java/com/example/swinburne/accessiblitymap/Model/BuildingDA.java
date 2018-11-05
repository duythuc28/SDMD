package com.example.swinburne.accessiblitymap.Model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
//import com.google.gson.annotations.SerializedName;

@Table(name = "BuildingModel")
public class BuildingDA extends Model {
    @Column(name = "address")
    public String address;

    @Column(name = "name" , unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    public String name;

    @Column(name = "blockID")
    public int blockId;

    @Column(name = "longitude")
    public Double longitude;

    @Column(name = "latitude")
    public Double latitude;

    @Column(name = "suburb")
    public String suburb;

    @Column(name = "rating")
    public int rating;

    @Column(name = "type")
    public String type;

    @Column(name = "accessibilityDes")
    public String accessibilityDes;

    @Column(name = "imageURL")
    public String imageURL;

    // Make sure to have a default constructor for every ActiveAndroid model
    public BuildingDA(){
        super();
    }

    public BuildingDA(int blockID, String name, String address,
                    String accessibilityDes, String suburb, int rating, float latitude, float longitude, String imageURL, String type){
        super();
        this.blockId = blockID;
        this.name = name;
        this.address = address;
        this.accessibilityDes = accessibilityDes;
        this.suburb = suburb;
        this.rating = rating;
        this.latitude = (double) latitude;
        this.longitude = (double) longitude;
        this.imageURL = imageURL;
        this.type = type;
    }

}

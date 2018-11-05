package com.example.swinburne.accessiblitymap.Shared;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.example.swinburne.accessiblitymap.Model.Building;
import com.example.swinburne.accessiblitymap.Model.BuildingDA;

import java.util.List;

public class DatabaseManager {

    public static void addBuilding(Building building) {
        BuildingDA buildingDA = new BuildingDA();
        buildingDA.blockId = Integer.valueOf(building.getBlockId());
        buildingDA.type = building.getType();
        buildingDA.longitude = building.getLongitude();
        buildingDA.latitude = building.getLatitude();
        buildingDA.suburb = building.getSuburb();
        buildingDA.rating = building.getRating();
        buildingDA.accessibilityDes = building.getAccessibilityDes();
        buildingDA.name = building.getName();
        buildingDA.address = building.getAddress();
        buildingDA.save();
    }

    public static void removeBuilding(Building building) {
        new Delete().from(BuildingDA.class).where("name = ?", building.getName()).execute();
    }

    public static BuildingDA getBuildingByName (String name) {
        return new Select().from(BuildingDA.class).where("name = ?", name).executeSingle();
    }

    public static List<BuildingDA> getListBuilding() {
        return new Select()
                .from(BuildingDA.class)
                .execute();
    }


}

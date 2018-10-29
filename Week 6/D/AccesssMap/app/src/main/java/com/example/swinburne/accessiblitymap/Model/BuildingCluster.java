package com.example.swinburne.accessiblitymap.Model;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

public class BuildingCluster implements ClusterItem {

    private final LatLng mPosition;
    private String mTitle;
    private String mSnippet;
    private BitmapDescriptor markerLogo;



    private Building mBuilding;

    public BuildingCluster(double lat, double lng, BitmapDescriptor icon, Building building) {
        mPosition = new LatLng(lat, lng);
        mBuilding = building;
        markerLogo = icon;
    }

    public BuildingCluster(double lat, double lng, String title, String snippet, BitmapDescriptor icon) {
        mPosition = new LatLng(lat, lng);
        mTitle = title;
        mSnippet = snippet;
        markerLogo = icon;
    }

    @Override
    public LatLng getPosition() {
        return mPosition;
    }

    @Override
    public String getTitle() {
        return mTitle;
    }

    @Override
    public String getSnippet() {
        return mSnippet;
    }

    public BitmapDescriptor getMarkerLogo() {
        return markerLogo;
    }

    public void setMarkerLogo(BitmapDescriptor markerLogo) {
        this.markerLogo = markerLogo;
    }

    public Building getmBuilding() {
        return mBuilding;
    }

    public void setmBuilding(Building mBuilding) {
        this.mBuilding = mBuilding;
    }
}

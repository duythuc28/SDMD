package com.example.swinburne.accessiblitymap.View;

import android.app.Fragment;
import android.content.Context;
import android.media.Rating;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.swinburne.accessiblitymap.Model.Building;
import com.example.swinburne.accessiblitymap.R;


public class MapInfoWindowView extends RelativeLayout {

    public interface MapInfoWindowCallBack {

    }

//    private Building building;
    private ImageView logo;
    private TextView txtDistance;
    private TextView txtTitle;
    private RatingBar ratingBar;
    private TextView txtSuburb;
    private TextView txtAddress;

    public MapInfoWindowView(Context context) {
        super(context);
        inflate(context, R.layout.map_info_window_view, this);
        initializeView();
    }

    public MapInfoWindowView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.map_info_window_view, this);
        initializeView();
    }

    public MapInfoWindowView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.map_info_window_view, this);
        initializeView();
    }

    private void initializeView() {
        logo = findViewById(R.id.imgView_locationImage);
        txtDistance = findViewById(R.id.txtView_distance);
        txtTitle = findViewById(R.id.txtView_name);
        txtAddress = findViewById(R.id.txtView_address);
        txtSuburb = findViewById(R.id.txtView_suburb);
        ratingBar = findViewById(R.id.rating_accessType);
    }

    public void setOnClickListener() {

    }

    public void directionClick(View view) {

    }

    public void setDataByBuilding(Building building) {
        // TODO: Get distance
//        txtDistance.setText(building.get);
//        initializeView();
        txtTitle.setText(building.getName());
        txtAddress.setText(building.getAddress());
        txtSuburb.setText(building.getSuburb());
        ratingBar.setRating(building.getRating());
    }
}

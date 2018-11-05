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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.swinburne.accessiblitymap.Model.Building;
import com.example.swinburne.accessiblitymap.R;
import com.example.swinburne.accessiblitymap.Shared.Utils;


public class MapInfoWindowView extends RelativeLayout {

    public interface MapInfoWindowCallBack {
         void onClickWindow(Building building);
         void onClickDirection(Building building);
    }

    private Building building;
    private ImageView logo;
    private TextView txtDistance;
    private TextView txtTitle;
    private RatingBar ratingBar;
    private TextView txtSuburb;
    private TextView txtAddress;
    private LinearLayout infoView;
    private TextView txtAccessDes;
    private MapInfoWindowCallBack listener;
    private Button btnDirection;

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
        infoView = findViewById(R.id.infoWindow_view);
        txtAccessDes = findViewById(R.id.txtView_accessDescription);
        infoView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClickWindow(building);
            }
        });
        btnDirection = findViewById(R.id.btn_Direction);
        btnDirection.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClickDirection(building);
            }
        });
    }

    public void setOnClickListener(MapInfoWindowCallBack listener) {
        this.listener = listener;
    }

    public void setDataByBuilding(Building building) {
        // TODO: Get distance
//        txtDistance.setText(building.get);
//        initializeView();
        this.building = building;
        txtTitle.setText(Utils.capitalize(building.getName()));
        txtAddress.setText(building.getAddress());
        txtSuburb.setText(building.getSuburb());
        txtAccessDes.setText(building.getAccessibilityDes());
        ratingBar.setRating(building.getRating());
    }

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }
}

package com.example.swinburne.accessiblitymap;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.swinburne.accessiblitymap.Model.Building;
import com.example.swinburne.accessiblitymap.Model.BuildingDA;
import com.example.swinburne.accessiblitymap.Shared.DatabaseManager;
import com.example.swinburne.accessiblitymap.Shared.Utils;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class DetailActivity extends AppCompatActivity implements OnMapReadyCallback  {

    private Building building;

    private GoogleMap googleMap;
    private ImageView imgViewIcon;
    private TextView txtName;
    private TextView txtAddress;
    private TextView txtAccessDes;
    private RatingBar ratingBar;
    private ImageButton btnBookmark;
    private Button btnDirection;
    private boolean isBookmark;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.detail_map);
        mapFragment.getMapAsync(this);
        initializeUI();
        building = getIntent().getExtras().getParcelable("building_data");
        setData(building);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        if (checkBookmark()) {
            btnBookmark.setImageResource(R.mipmap.bookmark_filled);
        } else {
            btnBookmark.setImageResource(R.mipmap.bookmark_unfill);
        }
        setTitle(Utils.capitalize(building.getName()));
    }

    private boolean checkBookmark() {
        BuildingDA buildingDA = DatabaseManager.getBuildingByName(this.building.getName());
        return buildingDA != null;
    }

    private void initializeUI () {
        imgViewIcon = findViewById(R.id.imgView_detailImage);
        txtName = findViewById(R.id.txtView_detailName);
        txtAddress = findViewById(R.id.txtView_detailAddress);
        txtAccessDes = findViewById(R.id.txtView_detailAccessDes);
        ratingBar = findViewById(R.id.detail_rating);
        btnBookmark = findViewById(R.id.btn_detailBookMark);
    }

    private void setData(Building building) {
        txtName.setText(Utils.capitalize(building.getName()));
        txtAddress.setText(building.getAddress());
        txtAccessDes.setText(building.getAccessibilityDes());
        ratingBar.setRating(building.getRating());
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
       this.googleMap = googleMap;

       LatLng accessPlace =  new LatLng(building.getLatitude(), building.getLongitude());
        BitmapDrawable bitmapdraw;
        if (building.getRating() == 3) {
            bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.access_high_rating);
        } else if (building.getRating() == 2) {
            bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.access_medium_rating);
        } else {
            bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.access_low_rating);
        }
        Bitmap b = bitmapdraw.getBitmap();
        Bitmap smallMarker = Bitmap.createScaledBitmap(b, 140, 140, false);
        Marker marker = this.googleMap.addMarker(new MarkerOptions().position(accessPlace)
                .title(Utils.capitalize(building.getName())).icon(BitmapDescriptorFactory.fromBitmap(smallMarker)));
       this.googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(accessPlace, 18));
        marker.showInfoWindow();
    }

    public void directionButtonClick(View view) {
        SharedPreferences sharedPreferences = getSharedPreferences("SharePref",MODE_PRIVATE);
        float latitude = sharedPreferences.getFloat("app_current_latitude",-1);
        float longitude = sharedPreferences.getFloat("app_current_longitude", -1);

        String uri = "http://maps.google.com/maps?saddr=" + latitude + "," + longitude + "&daddr=" + building.getLatitude() + "," + building.getLongitude();
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        startActivity(intent);
    }

    public void bookMarkButtonClick(View view) {
        if (isBookmark) {
            btnBookmark.setImageResource(R.mipmap.bookmark_unfill);
            isBookmark = false;
            DatabaseManager.removeBuilding(this.building);
        } else {
            btnBookmark.setImageResource(R.mipmap.bookmark_filled);
            isBookmark = true;
            DatabaseManager.addBuilding(this.building);
        }
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

}

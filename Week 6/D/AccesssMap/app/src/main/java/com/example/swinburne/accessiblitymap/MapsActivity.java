package com.example.swinburne.accessiblitymap;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.example.swinburne.accessiblitymap.Manager.RequestAPI.RequestAPIManager;
import com.example.swinburne.accessiblitymap.Manager.RequestAPI.RequestHandler;
import com.example.swinburne.accessiblitymap.Model.Building;
import com.example.swinburne.accessiblitymap.Model.BuildingCluster;
import com.example.swinburne.accessiblitymap.View.MapInfoWindowView;
import com.example.swinburne.accessiblitymap.View.MapLegend;
import com.example.swinburne.accessiblitymap.View.MapMarker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;

import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,ClusterManager.OnClusterItemClickListener<BuildingCluster> {

    private GoogleMap mMap;
    private List<Building> mBuildings;
    // Acquire a reference to the system Location Manager
    LocationManager locationManager;
    private LatLng curentLocation;
    private ClusterManager<BuildingCluster> mClusterManager;
    private ProgressBar progressBar;
    private MapLegend mapLegend;
    private MapInfoWindowView mapInfoWindowView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        // Register the listener with the Location Manager to receive location updates
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 8);
        } else {
            requestLocationService();
        }
        progressBar = findViewById(R.id.progressBar);
        mapLegend = findViewById(R.id.view_legend);
        mapInfoWindowView = findViewById(R.id.view_infoWinDow);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        restoreState(savedInstanceState);
    }

    private void restoreState(Bundle state) {
        if (state == null) return;
        Double lat = state.getDouble("CURRENT_LATITUDE");
        Double lng = state.getDouble("CURRENT_LONGITUDE");
       curentLocation = new LatLng(lat,lng);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putDouble("CURRENT_LATITUDE", curentLocation.latitude);
        outState.putDouble("CURRENT_LONGITUDE", curentLocation.longitude);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            requestLocationService();
            mMap.setMyLocationEnabled(true);
        } else {
            Toast.makeText(this, "Please allow location service", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadData(LatLng location) {
        // TODO: Get radius and current location


        RequestAPIManager.getBuilding(1000, location.latitude, location.longitude, null, new RequestHandler<List<Building>>() {
            @Override
            public void onResponse(List<Building> buildings) {
                // TODO: Display building in map
                mBuildings = buildings;
                Log.d("Number of building", String.valueOf(buildings.size()));
                progressBar.setVisibility(View.GONE);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                setupCluster();
            }

            @Override
            public void onFailure(String error) {
                // TODO: Display alert view fo map
                progressBar.setVisibility(View.GONE);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            }
        });
    }

    private void setupCluster() {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(curentLocation, 13));
        mClusterManager = new ClusterManager<BuildingCluster>(this, mMap);
        mClusterManager.setRenderer(new MapMarker(this, mMap, mClusterManager));

        mClusterManager.setOnClusterItemClickListener(this);
//

        mMap.setOnCameraIdleListener(mClusterManager);
        mMap.setOnMarkerClickListener(mClusterManager);
        mMap.setOnInfoWindowClickListener(mClusterManager);

        addBuildingMarker();
    }

    private void addBuildingMarker() {
        for (Building building : mBuildings) {
            BitmapDrawable bitmapdraw;
            if (building.getRating() == 3) {
                bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.access_high_rating);
            } else if (building.getRating() == 2) {
                bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.access_medium_rating);
            } else {
                bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.access_low_rating);
            }
            Bitmap b = bitmapdraw.getBitmap();
            Bitmap smallMarker = Bitmap.createScaledBitmap(b, 100, 100, false);
            BuildingCluster buildingCluster = new BuildingCluster(building.getLatitude(), building.getLongitude(), BitmapDescriptorFactory.fromBitmap(smallMarker), building);
            mClusterManager.addItem(buildingCluster);
        }

    }

    private void requestLocationService() {
        try {
            locationManager = (LocationManager) this.getSystemService(this.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        } catch (SecurityException ex) {
            Log.e("No permission", ex.toString());
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (curentLocation != null) {
            loadData(curentLocation);
        }

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                // TODO: Close the info window if appear and display the legend
                mapInfoWindowView.setVisibility(View.INVISIBLE);
                mapLegend.setVisibility(View.VISIBLE);
            }
        });
    }

    public void searchBarClick(View view) {
        Intent intent = new Intent(this, SearchActivity.class);
//        intent.putExtra("food", food1 );
        startActivityForResult(intent, 1);
    }

    public void filterButtonClick(View view) {
        Intent intent = new Intent(this, FilterActivity.class);
//        intent.putExtra("food", food1 );
        startActivityForResult(intent, 1);
    }

    // Define a listener that responds to location updates
    LocationListener locationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            // TODO: Update map
//            curentLocation = location;
            curentLocation = new LatLng(location.getLatitude(), location.getLongitude());
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(curentLocation, 13));
            loadData(curentLocation);
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onProviderDisabled(String provider) {
        }
    };

    @Override
    public boolean onClusterItemClick(BuildingCluster item) {

        // TODO: Display a custom view here and zoom to this specific location and hide legend
        mapInfoWindowView.setVisibility(View.VISIBLE);
        mapLegend.setVisibility(View.INVISIBLE);
        mapInfoWindowView.setDataByBuilding(item.getmBuilding());
        return true;
    }

}
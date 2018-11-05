package com.example.swinburne.accessiblitymap;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.preference.Preference;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.activeandroid.ActiveAndroid;
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

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,ClusterManager.OnClusterItemClickListener<BuildingCluster>, MapInfoWindowView.MapInfoWindowCallBack {

    private GoogleMap mMap;
    private List<Building> mBuildings = new ArrayList<>();
    // Acquire a reference to the system Location Manager
    LocationManager locationManager;
    private LatLng curentLocation;
    private ClusterManager<BuildingCluster> mClusterManager;
    private ProgressBar progressBar;
    private MapLegend mapLegend;
    private MapInfoWindowView mapInfoWindowView;
    private int radius;
    private String accessType;
    private FloatingActionButton btnBookmark;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        ActiveAndroid.initialize(this);
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
        btnBookmark = findViewById(R.id.floatBtn_bookmark);
        mapInfoWindowView.setOnClickListener(this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        restoreState(savedInstanceState);

        SharedPreferences preferences = getSharedPreferences("SharePref", MODE_PRIVATE);
        radius = preferences.getInt("radius", -1);
        if (radius == -1) {
            radius = 1000;
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("radius", radius);
            editor.apply();
        }
        accessType = preferences.getString("accessType", null);
        if (accessType == null) {
            accessType = "all";
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("accessType", "all");
            editor.apply();
        }
    }

    private void restoreState(Bundle state) {
        if (state == null) return;
        Double lat = state.getDouble("CURRENT_LATITUDE");
        Double lng = state.getDouble("CURRENT_LONGITUDE");
       curentLocation = new LatLng(lat,lng);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (curentLocation != null) {
            outState.putDouble("CURRENT_LATITUDE", curentLocation.latitude);
            outState.putDouble("CURRENT_LONGITUDE", curentLocation.longitude);
        }
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
        RequestAPIManager.getBuilding(radius, location.latitude, location.longitude, accessType, new RequestHandler<List<Building>>() {
            @Override
            public void onResponse(List<Building> buildings) {
                // TODO: Display building in map
                mBuildings.addAll(buildings);
                Log.d("Number of building", String.valueOf(buildings.size()));
                progressBar.setVisibility(View.INVISIBLE);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                setupCluster();
            }

            @Override
            public void onFailure(String error) {
                // TODO: Display alert view fo map
                progressBar.setVisibility(View.INVISIBLE);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            }
        });
    }

    private void reloadData() {
        progressBar.setVisibility(View.VISIBLE);
        RequestAPIManager.getBuilding(radius, curentLocation.latitude, curentLocation.longitude, accessType, new RequestHandler<List<Building>>() {
            @Override
            public void onResponse(List<Building> buildings) {
                // TODO: Display building in map
                mBuildings.clear();
                mBuildings.addAll(buildings);
                Log.d("Number of building", String.valueOf(buildings.size()));
                progressBar.setVisibility(View.INVISIBLE);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(curentLocation, 13));
//                setupCluster();
                addBuildingMarker();
            }

            @Override
            public void onFailure(String error) {
                // TODO: Display alert view fo map
                progressBar.setVisibility(View.INVISIBLE);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            }
        });
    }

    private void setupCluster() {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(curentLocation, 13));
        mClusterManager = new ClusterManager<BuildingCluster>(this, mMap);
        mClusterManager.setRenderer(new MapMarker(this, mMap, mClusterManager));
        mClusterManager.setOnClusterItemClickListener(this);
        mMap.setOnCameraIdleListener(mClusterManager);
        mMap.setOnMarkerClickListener(mClusterManager);
        mMap.setOnInfoWindowClickListener(mClusterManager);
        addBuildingMarker();
    }

    private void addBuildingMarker() {
        if (mClusterManager!= null) {
            mClusterManager.clearItems();
        }
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
            mClusterManager.cluster();
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
                slideDown(mapInfoWindowView);
                mapLegend.setVisibility(View.VISIBLE);
                btnBookmark.setVisibility(View.VISIBLE);
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
        startActivityForResult(intent, 2);
    }


    // Define a listener that responds to location updates
    LocationListener locationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            curentLocation = new LatLng(location.getLatitude(), location.getLongitude());
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(curentLocation, 13));
            loadData(curentLocation);
            SharedPreferences sharedPref = getSharedPreferences("SharePref",MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putFloat("app_current_latitude", (float) curentLocation.latitude);
            editor.putFloat("app_current_longitude", (float) curentLocation.longitude);
            editor.apply();
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onProviderDisabled(String provider) {
        }
    };

    // slide the view from below itself to the current position
    public void slideUp(View view){
        view.setVisibility(View.VISIBLE);
        view.setAlpha(0.0f);
// Start the animation
        view.animate()
                .translationY(0)
                .alpha(1.0f)
                .setListener(null);
    }

    // slide the view from its current position to below itself
    public void slideDown(View view){
        view.animate()
                .translationY(view.getHeight())
                .alpha(0.0f)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        mapInfoWindowView.setVisibility(View.GONE);
                    }
                });
    }

    @Override
    public boolean onClusterItemClick(BuildingCluster item) {

        // TODO: Display a custom view here and zoom to this specific location and hide legend
        slideUp(mapInfoWindowView);
        mapLegend.setVisibility(View.INVISIBLE);
        btnBookmark.setVisibility(View.INVISIBLE);
        mapInfoWindowView.setDataByBuilding(item.getmBuilding());
        LatLng focus = new LatLng(item.getmBuilding().getLatitude(), item.getmBuilding().getLongitude());
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(focus, 18));
        return true;
    }

    @Override
    public void onClickWindow(Building building) {
        Log.d("test", building.getName());
        // Go detail
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("building_data", building);
        startActivity(intent);
    }

    @Override
    public void onClickDirection(Building building) {
        String uri = "http://maps.google.com/maps?saddr=" + curentLocation.latitude + "," + curentLocation.longitude + "&daddr=" + building.getLatitude() + "," + building.getLongitude();
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        startActivity(intent);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {
            if(resultCode == RESULT_OK) {
                SharedPreferences preferences = getSharedPreferences("SharePref", MODE_PRIVATE);
                radius = preferences.getInt("radius", 0);
                accessType = preferences.getString("accessType", null);
                reloadData();
            }
        }
    }

    public void onClickBookMark(View view) {
        Intent intent = new Intent(this, BookMarkActivity.class);
//        intent.putExtra("food", food1 );
        startActivityForResult(intent, 3);
    }
}
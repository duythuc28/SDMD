package au.edu.swin.sdmd.suncalculatorjava;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import au.edu.swin.sdmd.suncalculatorjava.Adapter.GeolocationAdapter;
import au.edu.swin.sdmd.suncalculatorjava.calc.GeoLocation;

public class AddLocationActivity extends AppCompatActivity {

    MaterialSearchView searchView;
    List<GeoLocation> geoLocations = new ArrayList<>();
    RecyclerView recyclerView;
    GeolocationAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_location);
        initializeUI();
        try {
            mapDataToObject(readDataFromFile());
        } catch (IOException ex) {
            Log.e("error", ex.toString());
        }

    }

    private void initializeUI() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Search Locations");
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        searchView = findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText != null && !newText.isEmpty()) {
                   ArrayList<GeoLocation> searchLocation = findLocationByName(newText);
                    mAdapter = new GeolocationAdapter(searchLocation, AddLocationActivity.this);
                    mAdapter.notifyDataSetChanged();
                    recyclerView.setAdapter(mAdapter);
                } else {

                    mAdapter = new GeolocationAdapter(geoLocations, AddLocationActivity.this);
                    mAdapter.notifyDataSetChanged();
                    recyclerView.setAdapter(mAdapter);
                }
                return true;
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.listView);
        mAdapter = new GeolocationAdapter(geoLocations, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);

    }

    private ArrayList<GeoLocation> findLocationByName(String name) {
        ArrayList<GeoLocation> tLocation = new ArrayList<>();
        for (GeoLocation location: geoLocations) {
            if (location.getLocationName().toLowerCase().contains(name.toLowerCase())) {
                tLocation.add(location);
            }
        }
        return tLocation;
    }
    private ArrayList<String> readDataFromFile() throws IOException {

        ArrayList<String> records = new ArrayList<String>();
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(getAssets().open("au_locations.txt")));
        String line;
        while ((line = reader.readLine()) != null)
        {
            // Skip the comment notation
            if (!line.contains("//")) {
                records.add(line);
            }
        }
        reader.close();
        return records;
    }

    private void mapDataToObject(ArrayList<String> string) {
        for (String line: string) {
            String[] row = line.split(",");
            GeoLocation geoLocation = new GeoLocation(row[0], Double.valueOf(row[1]), Double.valueOf(row[2]), TimeZone.getDefault());
            this.geoLocations.add(geoLocation);
        }
        mAdapter.notifyDataSetChanged();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        return true;
    }
}

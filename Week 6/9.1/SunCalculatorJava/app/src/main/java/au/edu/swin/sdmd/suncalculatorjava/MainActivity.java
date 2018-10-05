package au.edu.swin.sdmd.suncalculatorjava;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import au.edu.swin.sdmd.suncalculatorjava.calc.AstronomicalCalendar;
import au.edu.swin.sdmd.suncalculatorjava.calc.GeoLocation;

public class MainActivity extends AppCompatActivity {

    FragmentAdapter adapterViewPager;
    private List<GeoLocation> australiaLocations = new ArrayList<>();
    FloatingActionButton btnAdd;
    ViewPager vpPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        storeDataToInternalStorage();
        btnAdd = findViewById(R.id.btnAddLocation);

        vpPager = (ViewPager) findViewById(R.id.pager);
        adapterViewPager = new FragmentAdapter(getSupportFragmentManager(), australiaLocations);
        vpPager.setAdapter(adapterViewPager);
        vpPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            // This method will be invoked when a new page becomes selected.
            @Override
            public void onPageSelected(int position) {

            }

            // This method will be invoked when the current page is scrolled
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // Code goes here
            }

            // Called when the scroll state changes:
            // SCROLL_STATE_IDLE, SCROLL_STATE_DRAGGING, SCROLL_STATE_SETTLING
            @Override
            public void onPageScrollStateChanged(int state) {
                // Code goes here
            }
        });
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        if (australiaLocations.size() > 4) {
            tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        } else {
            tabLayout.setTabMode(TabLayout.MODE_FIXED);
        }
        tabLayout.setupWithViewPager(vpPager);
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
            GeoLocation geoLocation = new GeoLocation(row[0], Double.valueOf(row[1]), Double.valueOf(row[2]), TimeZone.getTimeZone(row[3]));
            LocationFile.appendInput(getApplicationContext(), geoLocation);
        }
    }
    private void storeDataToInternalStorage() {
        try {
//            LocationFile.deleteFile(this);
            mapDataToObject(readDataFromFile());
        } catch (IOException ex) {
            Log.e("tag", "I/O Exception", ex);
        }
    }

    public void addLocation(GeoLocation location) {
        this.australiaLocations.add(location);
    }

    private void initData() {
        TimeZone tz = TimeZone.getDefault();
        addLocation(new GeoLocation("Melbourne", -37.813629, 144.963058,tz));
    }

    public void floatButtonClick(View view) {
        Intent intent = new Intent(this, AddLocationActivity.class);
        startActivityForResult(intent, 1);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
               GeoLocation location = data.getExtras().getParcelable("geoLocation");
               if (this.australiaLocations.size() > 4) {
                   this.australiaLocations.remove(this.australiaLocations.size()-1);
               }

                this.australiaLocations.add(0,location);
                adapterViewPager = new FragmentAdapter(getSupportFragmentManager(), australiaLocations);
                vpPager.setAdapter(adapterViewPager);
            }
        }
    }
}

package au.edu.swin.sdmd.suncalculatorjava;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        ViewPager vpPager = (ViewPager) findViewById(R.id.pager);
        adapterViewPager = new FragmentAdapter(getSupportFragmentManager(), australiaLocations);
        vpPager.setAdapter(adapterViewPager);
        vpPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            // This method will be invoked when a new page becomes selected.
            @Override
            public void onPageSelected(int position) {
//                Toast.makeText(MainActivity.this,
//                        "Selected page position: " + position, Toast.LENGTH_SHORT).show();
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

    public void addLocation(GeoLocation location) {
        this.australiaLocations.add(location);
    }

    private void initData() {
        TimeZone tz = TimeZone.getDefault();
        addLocation(new GeoLocation("Melbourne", -37.813629, 144.963058,tz));
        addLocation(new GeoLocation("Sydney", -33.868820, 151.209290,tz));
        addLocation(new GeoLocation("Canberra", -35.280937, 149.130005,tz));
        addLocation(new GeoLocation("Perth", -31.950527, 115.860458,tz));
    }

}

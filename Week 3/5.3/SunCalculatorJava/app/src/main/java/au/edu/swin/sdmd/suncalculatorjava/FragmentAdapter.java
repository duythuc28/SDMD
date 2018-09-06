package au.edu.swin.sdmd.suncalculatorjava;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

import au.edu.swin.sdmd.suncalculatorjava.calc.GeoLocation;

public class FragmentAdapter extends FragmentStatePagerAdapter {
    List<GeoLocation> australiaLocations;

    public FragmentAdapter(FragmentManager fm, List<GeoLocation> australiaLocations) {
        super(fm);
        this.australiaLocations = australiaLocations;
    }

    @Override
    public Fragment getItem(int position) {
        return SunsetFragment.newInstance(australiaLocations.get(position));
    }

    @Override
    public int getCount() {
        return australiaLocations.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return australiaLocations.get(position).getLocationName();
    }

}

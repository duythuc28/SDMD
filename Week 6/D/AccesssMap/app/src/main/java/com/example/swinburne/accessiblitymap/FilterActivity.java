package com.example.swinburne.accessiblitymap;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SimpleAdapter;

import com.example.swinburne.accessiblitymap.Adapter.FilterAdapter;
import com.example.swinburne.accessiblitymap.Adapter.SimpleSectionedRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class FilterActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    FilterAdapter mAdapter;
    String [] filterData = {"0.5km", "1km" ,"2km" ,"5km" ,"All", "High Rating", "Medium Rating", "Low Rating"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        mRecyclerView = (RecyclerView) findViewById(R.id.filter_listView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        SharedPreferences preferences = getSharedPreferences("SharePref", MODE_PRIVATE);
        int radius = preferences.getInt("radius", -1);
        String accessType = preferences.getString("accessType", null);
        //Your RecyclerView.Adapter
        mAdapter = new FilterAdapter(this, filterData, radius, accessType);

        //This is the code to provide a sectioned list
        List<SimpleSectionedRecyclerViewAdapter.Section> sections =
                new ArrayList<SimpleSectionedRecyclerViewAdapter.Section>();

        //Sections
        sections.add(new SimpleSectionedRecyclerViewAdapter.Section(0,"DISTANCE"));
        sections.add(new SimpleSectionedRecyclerViewAdapter.Section(4,"FILTER"));

        //Add your adapter to the sectionAdapter
        SimpleSectionedRecyclerViewAdapter.Section[] dummy = new SimpleSectionedRecyclerViewAdapter.Section[sections.size()];
        SimpleSectionedRecyclerViewAdapter mSectionedAdapter = new
                SimpleSectionedRecyclerViewAdapter(this,R.layout.section,R.id.section_text,mAdapter);
        mSectionedAdapter.setSections(sections.toArray(dummy));
        //Apply this adapter to the RecyclerView
        mRecyclerView.setAdapter(mSectionedAdapter);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_filter, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.filter_btn_search) {
            searchButtonClick();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void searchButtonClick() {
        SharedPreferences sharedPreferences = getSharedPreferences("SharePref", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("radius", mAdapter.getRadius());
        editor.putString("accessType", mAdapter.getAccessType());
        editor.apply();
        setResult(RESULT_OK);
        this.finish();
    }
}

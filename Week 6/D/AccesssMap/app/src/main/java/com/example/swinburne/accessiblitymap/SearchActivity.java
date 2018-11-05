package com.example.swinburne.accessiblitymap;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.swinburne.accessiblitymap.Adapter.BuildingAdapter;
import com.example.swinburne.accessiblitymap.Manager.RequestAPI.RequestAPIManager;
import com.example.swinburne.accessiblitymap.Manager.RequestAPI.RequestHandler;
import com.example.swinburne.accessiblitymap.Model.Building;
import com.example.swinburne.accessiblitymap.Model.PaginationRequest;
import com.example.swinburne.accessiblitymap.Shared.EndlessRecyclerViewScrollListener;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {


    MaterialSearchView searchView;
    List<Building> mBuildings = new ArrayList<>();
    RecyclerView recyclerView;
    BuildingAdapter mAdapter;
    PaginationRequest currentPage = new PaginationRequest();
    String currentQuery;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initializeUI();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search_bar, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        return true;
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
                    searchData(newText, new PaginationRequest(), true);
                    currentQuery = newText;
                } else {
                    loadData(true, new PaginationRequest());
                    currentQuery = null;
                }
                return true;
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.listView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
//                currentPage.nextPageRequest();
                if (currentQuery == null || currentQuery == "") {
                    loadData(false, new PaginationRequest(10, page));
                } else {
                    searchData(currentQuery, new PaginationRequest(10, page), false);
                }
            }
        });

        mAdapter = new BuildingAdapter(mBuildings, this);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter.setListener(new BuildingAdapter.BuildingApdaterCallBack() {
            @Override
            public void rowOnClick(Building building) {
                Intent intent = new Intent(SearchActivity.this, DetailActivity.class);
                intent.putExtra("building_data", building);
                startActivity(intent);
            }
        });
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);

        loadData(true, new PaginationRequest());
    }

    private void searchData(String name, PaginationRequest page, final boolean isReset) {
        SharedPreferences preferences = getSharedPreferences("SharePref", MODE_PRIVATE);
        String filterData = preferences.getString("accessType", null);
        RequestAPIManager.getBuildingByName(name, page, filterData, new RequestHandler<List<Building>>() {
            @Override
            public void onResponse(List<Building> buildings) {
                if (isReset) {
                    mBuildings.clear();
                }
                mBuildings.addAll(buildings);
                mAdapter.notifyDataSetChanged();
                recyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(String error) {
                // TODO: Handle error
            }
        });
    }

    private void loadData(final boolean isReset, PaginationRequest page) {
        SharedPreferences preferences = getSharedPreferences("SharePref", MODE_PRIVATE);
        String filterData = preferences.getString("accessType", null);
        RequestAPIManager.getBuildingByName(null, page, filterData, new RequestHandler<List<Building>>() {
            @Override
            public void onResponse(List<Building> buildings) {
                if (isReset) {
                    mBuildings.clear();
                }
                mBuildings.addAll(buildings);
                mAdapter.notifyDataSetChanged();
                recyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(String error) {
                // TODO: handle error here
            }
        });
    }
}

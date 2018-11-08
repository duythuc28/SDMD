package com.example.swinburne.accessiblitymap;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ethanhua.skeleton.Skeleton;
import com.ethanhua.skeleton.SkeletonScreen;
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
    EndlessRecyclerViewScrollListener scrollListener;
    SwipeRefreshLayout pullToRefresh;
    ProgressBar searchProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initializeUI();
        onRefresh();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search_bar, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        return true;
    }

    private void initializeUI() {
        pullToRefresh = findViewById(R.id.swipeContainer);
        searchProgress = findViewById(R.id.search_progressBar);

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
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, OrientationHelper.VERTICAL, false);

        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new BuildingAdapter(mBuildings, this);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter.setListener(new BuildingAdapter.BuildingApdaterCallBack() {
            @Override
            public void rowOnClick(Building building) {
                Intent intent = new Intent(SearchActivity.this, DetailActivity.class);
                intent.putExtra("building_data", building);
                startActivity(intent);
            }

            @Override
            public void rowOnLongTouch(Building building) {

            }

        });
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);

        loadData(true, new PaginationRequest());

        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                int offset = totalItemsCount;

                Log.d("offset: ", String.valueOf(offset));
                Log.d("page: ", String.valueOf(page));

                if (currentQuery == null || currentQuery == "") {
                    loadData(false, new PaginationRequest(10, offset));
                } else {
                    searchData(currentQuery, new PaginationRequest(10, offset), false);
                }
            }
        };

        recyclerView.addOnScrollListener(scrollListener);

    }

    private void onRefresh() {
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (currentQuery == null || currentQuery == "") {
                    loadData(true, new PaginationRequest(10, 0));
                } else {
                    searchData(currentQuery, new PaginationRequest(10, 0), true);
                }
                pullToRefresh.setRefreshing(false);
            }

        });
        // Configure the refreshing colors
        pullToRefresh.setColorSchemeResources(R.color.appColor,
                android.R.color.holo_blue_dark);

    }


    private void searchData(String name, PaginationRequest page, final boolean isReset) {
        searchProgress.setVisibility(View.VISIBLE);
        SharedPreferences preferences = getSharedPreferences("SharePref", MODE_PRIVATE);
        String filterData = preferences.getString("accessType", null);
        RequestAPIManager.getBuildingByName(name, page, filterData, new RequestHandler<List<Building>>() {
            @Override
            public void onResponse(List<Building> buildings) {
                searchProgress.setVisibility(View.INVISIBLE);
                if (isReset) {
                    mBuildings.clear();
                    scrollListener.resetState();
                }
                mBuildings.addAll(buildings);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(String error) {
                // TODO: Handle error
                searchProgress.setVisibility(View.INVISIBLE);
                Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadData(final boolean isReset, PaginationRequest page) {
        searchProgress.setVisibility(View.VISIBLE);
        SharedPreferences preferences = getSharedPreferences("SharePref", MODE_PRIVATE);
        String filterData = preferences.getString("accessType", null);
        RequestAPIManager.getBuildingByName(null, page, filterData, new RequestHandler<List<Building>>() {
            @Override
            public void onResponse(List<Building> buildings) {
                searchProgress.setVisibility(View.INVISIBLE);
                if (isReset) {
                    mBuildings.clear();
                    scrollListener.resetState();
                }
                mBuildings.addAll(buildings);
//                mAdapter.notifyItemRangeInserted(mAdapter.getItemCount(), mBuildings.size() - 1);
                mAdapter.notifyDataSetChanged();
            }


            @Override
            public void onFailure(String error) {
                searchProgress.setVisibility(View.INVISIBLE);
                // TODO: handle error here
                Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
            }
        });
    }
}

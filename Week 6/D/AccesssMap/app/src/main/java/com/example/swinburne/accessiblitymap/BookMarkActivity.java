package com.example.swinburne.accessiblitymap;

import android.content.Intent;
import android.graphics.Canvas;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.LinearLayout;

import com.example.swinburne.accessiblitymap.Adapter.BuildingAdapter;
import com.example.swinburne.accessiblitymap.Model.Building;
import com.example.swinburne.accessiblitymap.Model.BuildingDA;
import com.example.swinburne.accessiblitymap.Model.PaginationRequest;
import com.example.swinburne.accessiblitymap.Shared.DatabaseManager;
import com.example.swinburne.accessiblitymap.Shared.EndlessRecyclerViewScrollListener;
import com.example.swinburne.accessiblitymap.Shared.SwipeController;
import com.example.swinburne.accessiblitymap.Shared.SwipeControllerActions;

import java.util.ArrayList;
import java.util.List;

public class BookMarkActivity extends AppCompatActivity {

    List<Building> mBuildings = new ArrayList<>();
    RecyclerView recyclerView;
    BuildingAdapter mAdapter;
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_mark);

        linearLayout = findViewById(R.id.bookmark_layout);


        recyclerView = (RecyclerView) findViewById(R.id.bookmark_recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        mAdapter = new BuildingAdapter(mBuildings, this);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter.setListener(new BuildingAdapter.BuildingApdaterCallBack() {
            @Override
            public void rowOnClick(Building building) {
                Intent intent = new Intent(BookMarkActivity.this, DetailActivity.class);
                intent.putExtra("building_data", building);
                startActivity(intent);
            }

            @Override
            public void rowOnLongTouch(final Building building) {
                Snackbar.make(linearLayout, "Do you want to remove this location", Snackbar.LENGTH_LONG)
                        .setAction("Delete", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                DatabaseManager.removeBuilding(building);
                                mBuildings.remove(building);
                                mAdapter.notifyDataSetChanged();
                            }
                        })
                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                        .show();
            }
        });

        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);

        loadData();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void loadData() {
       List<BuildingDA> list =  DatabaseManager.getListBuilding();
       for (BuildingDA data: list) {
           Building building = new Building(data);
           mBuildings.add(building);
       }
       mAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

}

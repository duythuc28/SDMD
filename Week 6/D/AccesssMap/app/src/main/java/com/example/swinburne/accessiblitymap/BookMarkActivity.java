package com.example.swinburne.accessiblitymap;

import android.content.Intent;
import android.graphics.Canvas;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_mark);

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
        });
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                DatabaseManager.removeBuilding(mBuildings.get(viewHolder.getAdapterPosition()));
                mAdapter.removeBuilding(viewHolder.getAdapterPosition());
                mAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                mAdapter.notifyItemRangeChanged(viewHolder.getAdapterPosition(), mAdapter.getItemCount());
            }
        }).attachToRecyclerView(recyclerView);

//        final SwipeController swipeController = new SwipeController(new SwipeControllerActions() {
//            @Override
//            public void onRightClicked(int position) {
////                mAdapter.players.remove(position);
//                DatabaseManager.removeBuilding(mBuildings.get(position));
//                mAdapter.notifyItemRemoved(position);
//                mAdapter.notifyItemRangeChanged(position, mAdapter.getItemCount());
//            }
//        });
//
//        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeController);
//        itemTouchhelper.attachToRecyclerView(recyclerView);
//
//        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
//            @Override
//            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
//                swipeController.onDraw(c);
//            }
//        });


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

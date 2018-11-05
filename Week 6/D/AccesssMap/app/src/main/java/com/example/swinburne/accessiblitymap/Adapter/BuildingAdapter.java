package com.example.swinburne.accessiblitymap.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.Rating;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.swinburne.accessiblitymap.Model.Building;
import com.example.swinburne.accessiblitymap.R;
import com.example.swinburne.accessiblitymap.Shared.Utils;

import java.util.List;

import static android.app.Activity.RESULT_OK;

public class BuildingAdapter extends RecyclerView.Adapter<BuildingAdapter.BuildingAdapterViewHolder> {
    private List<Building> locations;
    private int selectedBuildingIndex = -1;
    private Context mContext;
    private BuildingApdaterCallBack listener;

    public interface BuildingApdaterCallBack {
        void rowOnClick(Building building);
    }

    public BuildingAdapter(List<Building> locations, Context mContext) {
        this.locations = locations;
        this.mContext = mContext;
    }

    public void setListener(BuildingApdaterCallBack listener) {
        this.listener = listener;
    }

    public class BuildingAdapterViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgViewIcon;
        public TextView txtViewTitle;
        public TextView txtViewAddress;
        public TextView txtViewAccessDescription;
        public RatingBar ratingBar;
        public View holderView;
        public BuildingAdapterViewHolder(View view) {
            super(view);
            imgViewIcon = view.findViewById(R.id.imgView_searchLocationImage);
            txtViewTitle = view.findViewById(R.id.txtView_searchName);
            txtViewAccessDescription = view.findViewById(R.id.txtView_searchAccessDes);
            txtViewAddress = view.findViewById(R.id.txtView_searchAddress);
            ratingBar = view.findViewById(R.id.rating_searchRating);
            holderView = view;
        }
    }

    public BuildingAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_row_layout, parent, false);

        return new BuildingAdapterViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BuildingAdapterViewHolder holder, int position) {
        final Building location = locations.get(position);
        holder.ratingBar.setRating(location.getRating());
        holder.txtViewTitle.setText(Utils.capitalize(location.getName()));
        holder.txtViewAddress.setText(location.getAddress());
        holder.txtViewAccessDescription.setText(location.getAccessibilityDes());
        final int index = position;
        holder.holderView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.rowOnClick(locations.get(index));
            }
        });
    }

    @Override
    public int getItemCount() {
        return locations.size();
    }
}

package com.example.swinburne.accessiblitymap.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.swinburne.accessiblitymap.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FilterAdapter extends RecyclerView.Adapter<FilterAdapter.SimpleViewHolder> {

    private final Context mContext;
    private List<String> mData;
    private int selectedRadius;
    private int selectedType;
    private int radius;
    private String accessType;

    public void add(String s,int position) {
        position = position == -1 ? getItemCount()  : position;
        mData.add(position,s);
        notifyItemInserted(position);
    }

    public void remove(int position){
        if (position < getItemCount()  ) {
            mData.remove(position);
            notifyItemRemoved(position);
        }
    }

    public static class SimpleViewHolder extends RecyclerView.ViewHolder {
        public final TextView title;
        public final CheckBox checkBox;

        public SimpleViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.txtView_filter);
            checkBox = view.findViewById(R.id.checkBox_Filter);
        }
    }

    public FilterAdapter(Context context, String[] data, int radius, String accessType) {
        mContext = context;
        if (data != null)
            mData = new ArrayList<String>(Arrays.asList(data));
        else mData = new ArrayList<String>();
        this.radius = radius;
        this.accessType = accessType;
        selectedRadius = getSelectedRadius(radius);
        selectedType = getSelectedType(accessType);
    }

    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.filter_row_layout, parent, false);
        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SimpleViewHolder holder, final int position) {
        holder.title.setText(mData.get(position));
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext,"Position ="+position, Toast.LENGTH_SHORT).show();
            }
        });

        if (position < 4) {
            holder.checkBox.setChecked(position == selectedRadius);
        } else {
            holder.checkBox.setChecked(position == selectedType);
        }
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position < 4) {
                    selectedRadius = position;
                } else {
                    selectedType = position;
                }
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public String getAccessType() {
        switch (selectedType) {
            case 4:
                return "all";
            case 5:
                return "accessibility_rating = 3";
            case 6:
                return "accessibility_rating = 2";
            case 7:
                return "accessibility_rating <= 1";
        }
        return null;
    }

    public int getRadius() {
        switch (selectedRadius) {
            case 0:
                return 500;
            case 1:
                return 1000;
            case 2:
                return 2000;
            case 3:
                return 5000;
        }
        return -1;
    }


    private int getSelectedRadius(int radius) {
        switch (radius) {
            case 500:
                return 0;
            case 1000:
                return 1;
            case 2000:
                return 2;
            case 5000:
                return 3;
        }
        return 0;
    }

    private int getSelectedType (String type) {
        switch (type) {
            case "all":
                return 4;
            case "accessibility_rating = 3":
                return 5;
            case "accessibility_rating = 2":
                return 6;
            case "accessibility_rating <= 1":
                return 7;
        }
        return 0;
    }
}

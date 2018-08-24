package com.example.swinburne.foodparcel.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.swinburne.foodparcel.Food;
import com.example.swinburne.foodparcel.R;

import java.util.List;

public class FoodAdapter  extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {

    private List<Food> bookList;

    public class FoodViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView logo;
        public RatingBar ratingBar;
        public FoodViewHolder(View view) {
            super(view);
//            title = (TextView) view.findViewById(R.id.txtTitle);
//            logo = view.findViewById(R.id.imgViewIcon);
//            ratingBar = view.findViewById(R.id.ratingStar);
        }
    }

    public FoodAdapter(List<Food> moviesList) {
        this.bookList = moviesList;
    }

    public FoodViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.food_row, parent, false);

        return new FoodViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(FoodViewHolder holder, int position) {
//        Food book =  bookList.get(position);
//        holder.title.setText(book.getTitle());
//        holder.logo.setImageResource(book.getImageRef());
//        holder.ratingBar.setRating(book.getRating());
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }
}

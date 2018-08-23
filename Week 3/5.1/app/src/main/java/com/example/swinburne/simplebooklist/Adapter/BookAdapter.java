package com.example.swinburne.simplebooklist.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import com.example.swinburne.simplebooklist.Book;
import com.example.swinburne.simplebooklist.R;

import java.util.List;


public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {
    private List<Book> bookList;

    public class BookViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView logo;
        public RatingBar ratingBar;
        public BookViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.txtTitle);
            logo = view.findViewById(R.id.imgViewIcon);
            ratingBar = view.findViewById(R.id.ratingStar);
        }
    }

    public BookAdapter(List<Book> moviesList) {
        this.bookList = moviesList;
    }

    public BookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.book_row, parent, false);

        return new BookViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BookViewHolder holder, int position) {
        Book book =  bookList.get(position);
        holder.title.setText(book.getTitle());
        holder.logo.setImageResource(book.getImageRef());
        holder.ratingBar.setRating(book.getRating());
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

}

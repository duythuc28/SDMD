package au.edu.swin.sdmd.movieratingsjava.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;
import au.edu.swin.sdmd.movieratingsjava.Movie;
import au.edu.swin.sdmd.movieratingsjava.R;
import au.edu.swin.sdmd.movieratingsjava.View.RatingView;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private List<Movie> movies;
    private Context context;

    public class MovieViewHolder extends RecyclerView.ViewHolder {
        public RatingView icon;
        public TextView title;
        public TextView description;
        public MovieViewHolder(View view) {
            super(view);
            icon = view.findViewById(R.id.row_icon);
            title = view.findViewById(R.id.row_label);
            description = view.findViewById(R.id.row_subtext);
        }
    }

    public MovieAdapter(List<Movie> movies, Context context) {
        this.movies = movies;
        this.context= context;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listrow, parent,false);
        Log.w("MVMVMVMVMVMV", "Creating row view at position ");
        return new MovieViewHolder(itemView) ;
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = this.movies.get(position);
        holder.title.setText(movie.getName());
        holder.icon.setImageByName(movie.getName(), movie.getRating());
        holder.description.setText(movie.getVotes());
    }

    @Override
    public int getItemCount() {
        return this.movies.size();
    }

}

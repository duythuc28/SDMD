package au.edu.swin.sdmd.movieratingsjava;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import au.edu.swin.sdmd.movieratingsjava.Adapter.MovieAdapter;

public class RefactorActivity extends AppCompatActivity {

    private List<Movie> movieList = new ArrayList<>();
    private MovieAdapter mAdapter;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refactor);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progressBar_cyclic);
        mAdapter = new MovieAdapter(movieList, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        GetFileTask task = new GetFileTask();
        task.execute();
    }

    private void reloadData(List<Movie> movies) {
        movieList.clear();
        movieList.addAll(movies);
        mAdapter.notifyDataSetChanged();
    }

    class GetFileTask extends AsyncTask<Void, Void, List<Movie>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<Movie> doInBackground(Void... voids) {
            InputStream inputStream = getResources().openRawResource(R.raw.ratings);
            return Movie.loadFromFile(inputStream);
        }

        @Override
        protected void onPostExecute(List<Movie> movies) {
            super.onPostExecute(movies);
            reloadData(movies);
            progressBar.setVisibility(View.GONE);
            Toast.makeText(RefactorActivity.this, "Load data successfully", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

}

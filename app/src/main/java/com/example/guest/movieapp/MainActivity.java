package com.example.guest.movieapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import com.example.guest.movieapp.adapters.MovieListAdapter;
import com.example.guest.movieapp.models.Movie;
import com.example.guest.movieapp.services.MovieService;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    @Bind(R.id.recyclerView) RecyclerView mRecyclerView;
    @Bind(R.id.titleEditText) EditText mTitleEditText;
    @Bind(R.id.findMoviesButton) Button mFindMoviesButton;
    public String movieTitle;
    public ArrayList<Movie> mMovies = new ArrayList<>();
    final MovieService movieService = new MovieService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        getMovies();

        mFindMoviesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                movieTitle = mTitleEditText.getText().toString();
                mTitleEditText.getText().clear();
                movieService.findUserMovie(movieTitle, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        mMovies = movieService.processResults(response);
                        MainActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                displayMovies();
                            }
                        });
                    }

                });
            }
        });
    }


    private void getMovies() {


        movieService.findMovies(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                mMovies = movieService.processResults(response);

                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        displayMovies();
                    }
                });
            }
        });
    }

    private void displayMovies() {
        MovieListAdapter mAdapter = new MovieListAdapter(getApplicationContext(), mMovies);
        mRecyclerView.setAdapter(mAdapter);
        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(MainActivity.this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
    }
}

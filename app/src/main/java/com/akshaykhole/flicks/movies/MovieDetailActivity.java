package com.akshaykhole.flicks.movies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.akshaykhole.flicks.R;

public class MovieDetailActivity extends AppCompatActivity {
    TextView tvMovieTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        initComponents();
        Intent intent = getIntent();
        MovieModel movie = (MovieModel) intent.getSerializableExtra("movie");
        tvMovieTitle.setText(movie.getOriginalTitle());
    }

    public void initComponents() {
        this.tvMovieTitle = (TextView) findViewById(R.id.textViewMovieTitle);
    }
}

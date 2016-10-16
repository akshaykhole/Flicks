package com.akshaykhole.flicks.movies;

import android.content.Intent;
import android.media.Rating;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.akshaykhole.flicks.R;
import com.squareup.picasso.Picasso;

public class MovieDetailActivity extends AppCompatActivity {
    TextView tvMovieTitle;
    ImageView ivMoviePoster;
    TextView tvMovieOverview;
    RatingBar ratingBarMovieRating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        initComponents();
        Intent intent = getIntent();
        MovieModel movie = (MovieModel) intent.getSerializableExtra("movie");
        tvMovieTitle.setText(movie.getOriginalTitle());
        tvMovieOverview.setText(movie.getOverview());

        Picasso.with(getApplicationContext())
                .load(movie.getPosterPath())
                .placeholder(R.drawable.placeholder_movie_image)
                .error(R.drawable.placeholder_movie_image_error)
                .into(this.ivMoviePoster);

        ratingBarMovieRating.setRating(movie.voteAverage.floatValue());
    }

    public void initComponents() {
        this.tvMovieTitle = (TextView) findViewById(R.id.textViewMovieTitle);
        this.ivMoviePoster = (ImageView) findViewById(R.id.imageViewMoviePoster);
        this.tvMovieOverview = (TextView) findViewById(R.id.textViewMovieOverview);
        this.ratingBarMovieRating = (RatingBar) findViewById(R.id.ratingBarMovieRating);
    }
}

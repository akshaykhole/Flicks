package com.akshaykhole.flicks.movies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.akshaykhole.flicks.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import cz.msebera.android.httpclient.Header;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;


public class MovieDetailActivity extends AppCompatActivity {
    Integer IMAGE_RADIUS = 15;
    Integer IMAGE_MARGIN = 0;

    TextView tvMovieTitle;
    ImageView ivMoviePoster;
    TextView tvMovieOverview;
    RatingBar ratingBarMovieRating;
    TextView textViewReleaseDate;
    MovieModel movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        initComponents();
        Intent intent = getIntent();
        this.movie = (MovieModel) intent.getSerializableExtra("movie");
        tvMovieTitle.setText(movie.getOriginalTitle());
        tvMovieOverview.setText(movie.getOverview());

        Picasso.with(getApplicationContext())
                .load(movie.getPosterPath())
                .placeholder(R.drawable.placeholder_movie_image)
                .fit()
                .transform(new RoundedCornersTransformation(IMAGE_RADIUS, IMAGE_MARGIN))
                .error(R.drawable.placeholder_movie_image_error)
                .into(this.ivMoviePoster);

        ratingBarMovieRating.setRating((movie.voteAverage.floatValue() / 2));

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date releaseDate = dateFormat.parse(movie.getReleaseDate());
            dateFormat = new SimpleDateFormat("MMM d, yyyy");
            String formattedDate = dateFormat.format(releaseDate);
            textViewReleaseDate.setText(formattedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        ivMoviePoster.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                AsyncHttpClient videoTrailerClient = new AsyncHttpClient();

                String trailersUrl = "https://api.themoviedb.org/3/movie/" +
                        movie.getId() + "/videos?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";

                videoTrailerClient.get(trailersUrl, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode,
                                          Header[] headers,
                                          JSONObject response) {

                        super.onSuccess(statusCode, headers, response);

                        try {
                            JSONArray videoList = response.getJSONArray("results");
                            if(videoList.length() > 0) {

                                Intent playVideoIntent = new Intent(MovieDetailActivity.this,
                                        MovieVideoPlayerActivity.class);

                                String videoKey = videoList.getJSONObject(0).get("key").toString();

                                playVideoIntent.putExtra("videoKey", videoKey);

                                startActivity(playVideoIntent);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode,
                                          Header[] headers,
                                          String responseString,
                                          Throwable throwable) {

                        super.onFailure(statusCode, headers, responseString, throwable);

                        Log.d("DEBUG", responseString);
                    }
                });
            }
        });
    }

    public void initComponents() {
        this.tvMovieTitle = (TextView) findViewById(R.id.textViewMovieTitle);
        this.ivMoviePoster = (ImageView) findViewById(R.id.imageViewMoviePoster);
        this.tvMovieOverview = (TextView) findViewById(R.id.textViewMovieOverview);
        this.ratingBarMovieRating = (RatingBar) findViewById(R.id.ratingBarMovieRating);
        this.textViewReleaseDate = (TextView) findViewById(R.id.textViewReleaseDate);
    }
}

package com.akshaykhole.flicks.movies;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.akshaykhole.flicks.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import cz.msebera.android.httpclient.Header;

public class MoviesActivity extends AppCompatActivity {
    ArrayList<MovieModel> movies;
    MovieArrayAdapter movieArrayAdapter;
    ListView lvItems;
    private SwipeRefreshLayout swipeRefreshMoviesListContainer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_movies);
        fetchMoviesAsync();

        swipeRefreshMoviesListContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainerMoviesList);
        swipeRefreshMoviesListContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchMoviesAsync();
            }
        });

        swipeRefreshMoviesListContainer.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light
        );

        // Set handler for click event on item
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MovieModel movie = movies.get(i);

                if(movie.popularityOrdinal() == MovieModel.PopularityCategories.POPULAR.ordinal()) {
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

                                    Intent playVideoIntent = new Intent(MoviesActivity.this,
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

                } else {
                    Intent intent = new Intent(MoviesActivity.this, MovieDetailActivity.class);
                    intent.putExtra("movie", movie);
                    startActivity(intent);
                }
            }
        });
    }

    public void fetchMoviesAsync() {
        String url = getResources().getString(R.string.moviesDatabaseUrl);
        movies = new ArrayList<>();
        lvItems = (ListView) findViewById(R.id.lvMovieItems);
        movieArrayAdapter = new MovieArrayAdapter(this, movies);
        lvItems.setAdapter(movieArrayAdapter);

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode,
                                  Header[] headers,
                                  JSONObject response) {

                JSONArray movieJsonResults;

                try {
                    movieJsonResults = response.getJSONArray("results");
                    movies.addAll(MovieModel.fromJSONArray(movieJsonResults));
                    movieArrayAdapter.notifyDataSetChanged();
                    swipeRefreshMoviesListContainer.setRefreshing(false);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode,
                                  Header[] headers,
                                  Throwable throwable,
                                  JSONObject errorResponse) {

                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }
}
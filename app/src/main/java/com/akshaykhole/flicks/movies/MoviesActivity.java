package com.akshaykhole.flicks.movies;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
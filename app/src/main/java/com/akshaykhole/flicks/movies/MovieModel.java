package com.akshaykhole.flicks.movies;

import android.graphics.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by akshay on 10/15/16.
 */

// https://image.tmdb.org/t/p/w500/kqjL17yufvn9OVLyXYpvtyrFfak.jpg
public class MovieModel implements Serializable {
    public enum PopularityCategories {
        POPULAR,
        NOT_POPULAR
    }

    String posterPath;
    String originalTitle;
    String overview;
    String backdropPosterPath;
    String language;
    String releaseDate;
    Double voteAverage;
    Double popularity;

    Boolean isVideo;
    Boolean isAnAdultMovie;

    public String getReleaseDate() {
        return releaseDate;
    }

    public Double getPopularity() {
        return popularity;
    }

    public Boolean getVideo() {
        return isVideo;
    }

    public Boolean getAnAdultMovie() {
        return isAnAdultMovie;
    }

    public String getLanguage() {
        return language;
    }

    public String getBackdropPosterPath() {
        return String.format("https://image.tmdb.org/t/p/w500/%s", backdropPosterPath);
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getPosterPath() {
        return String.format("https://image.tmdb.org/t/p/w500/%s", posterPath);
    }

    public String getOverview() {
        return overview;
    }

    public MovieModel(JSONObject jsonObject) throws JSONException {
        this.posterPath = jsonObject.getString("poster_path");
        this.originalTitle = jsonObject.getString("original_title");
        this.overview = jsonObject.getString("overview");
        this.backdropPosterPath = jsonObject.getString("backdrop_path");
        this.voteAverage = jsonObject.getDouble("vote_average");
        this.isVideo = jsonObject.getBoolean("video");
        this.isAnAdultMovie = jsonObject.getBoolean("adult");
        this.releaseDate = jsonObject.getString("release_date");
        this.language = jsonObject.getString("original_language");
    }

    public static ArrayList<MovieModel> fromJSONArray(JSONArray array) {
        ArrayList<MovieModel> results = new ArrayList<>();

        for(int x = 0; x < array.length(); ++x) {

            try {
                results.add(new MovieModel(array.getJSONObject(x)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return results;
    }

    public Integer popularityOrdinal() {
        if (this.voteAverage > 5) {
            return PopularityCategories.POPULAR.ordinal();
        } else {
            return PopularityCategories.NOT_POPULAR.ordinal();
        }
    }
}
package com.akshaykhole.flicks.movies;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by akshay on 10/15/16.
 */

public class MovieArrayAdapter extends ArrayAdapter<MovieModel> {
    public MovieArrayAdapter(Context context, List<MovieModel> movies) {
        super(context, android.R.layout.simple_list_item_1, movies);
    }
}

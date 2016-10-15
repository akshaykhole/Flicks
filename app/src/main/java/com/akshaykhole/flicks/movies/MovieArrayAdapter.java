package com.akshaykhole.flicks.movies;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.akshaykhole.flicks.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by akshay on 10/15/16.
 */

public class MovieArrayAdapter extends ArrayAdapter<MovieModel> {
    public MovieArrayAdapter(Context context, List<MovieModel> movies) {
        super(context, android.R.layout.simple_list_item_1, movies);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MovieModel movie = getItem(position);

        if(convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.movies_list_item, parent, false);
        }

        ImageView ivImage = (ImageView) convertView.findViewById(R.id.imageViewMoviePoster);
        ivImage.setImageResource(0);

        TextView tvTitle = (TextView) convertView.findViewById(R.id.textViewMovieTitle);
        TextView tvOverView = (TextView) convertView.findViewById(R.id.textViewMovieOverview);

        tvTitle.setText(movie.getOriginalTitle());
        tvOverView.setText(movie.getOverview());

        Picasso.with(getContext()).load(movie.getPosterPath()).into(ivImage);
        return convertView;
    }
}

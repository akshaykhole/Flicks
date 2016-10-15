package com.akshaykhole.flicks.movies;

import android.content.Context;
import android.content.res.Configuration;
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

    private static class ViewHolder {
        TextView tvMovieTitle;
        TextView tvMovieOverview;
        ImageView ivMovieImage;
    }

    public MovieArrayAdapter(Context context, List<MovieModel> movies) {
        super(context, R.layout.movies_list_item, movies);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MovieModel movie = getItem(position);
        ViewHolder viewHolder;
        int orientation = getContext().getResources().getConfiguration().orientation;

        if(convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.movies_list_item, parent, false);

            // Start caching using ViewHolder
            viewHolder = new ViewHolder();
            viewHolder.tvMovieTitle = (TextView) convertView.findViewById(R.id.textViewMovieTitle);
            viewHolder.tvMovieOverview = (TextView) convertView.findViewById(R.id.textViewMovieOverview);


            viewHolder.ivMovieImage = (ImageView) convertView.findViewById(R.id.imageViewMoviePoster);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tvMovieTitle.setText(movie.getOriginalTitle());
        viewHolder.tvMovieOverview.setText(movie.getOverview());

        // TODO: Try to DRY this up
        if(orientation == Configuration.ORIENTATION_PORTRAIT) {
            Picasso.with(getContext())
                    .load(movie.getPosterPath())
                    .placeholder(R.drawable.placeholder_movie_image)
                    .error(R.drawable.placeholder_movie_image_error)
                    .into(viewHolder.ivMovieImage);
        } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Picasso.with(getContext())
                    .load(movie.getBackdropPosterPath())
                    .placeholder(R.drawable.placeholder_movie_image)
                    .error(R.drawable.placeholder_movie_image_error)
                    .into(viewHolder.ivMovieImage);
        }

        return convertView;
    }
}

package com.akshaykhole.flicks.movies;

import android.content.Context;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.util.Log;
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

    @Override
    public int getViewTypeCount() {
        return MovieModel.PopularityCategories.values().length;
    }

    @Override
    public int getItemViewType(int position) {
        return getItem(position).popularityOrdinal();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MovieModel movie = getItem(position);
        ViewHolder viewHolder;
        int orientation = getContext().getResources().getConfiguration().orientation;

        if(convertView == null) {
            int typeOfViewToInflate = getItemViewType(position);
            convertView = getInflatedLayoutForType(typeOfViewToInflate);

            // Start caching using ViewHolder
            viewHolder = new ViewHolder();
            viewHolder.tvMovieTitle = (TextView) convertView.findViewById(R.id.textViewMovieTitle);
            viewHolder.tvMovieOverview = (TextView) convertView.findViewById(R.id.textViewMovieOverview);

            viewHolder.ivMovieImage = (ImageView) convertView.findViewById(R.id.imageViewMoviePoster);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if(movie.popularityOrdinal() == MovieModel.PopularityCategories.NOT_POPULAR.ordinal()) {
            viewHolder.tvMovieTitle.setText(movie.getOriginalTitle());
            viewHolder.tvMovieOverview.setText(movie.getOverview());
        }


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

    private View getInflatedLayoutForType(int type) {
        if(type == MovieModel.PopularityCategories.POPULAR.ordinal()) {
            return LayoutInflater.from(getContext()).inflate(R.layout.popular_movie_list_item, null);
        } else if (type == MovieModel.PopularityCategories.NOT_POPULAR.ordinal()) {
            return LayoutInflater.from(getContext()).inflate(R.layout.movies_list_item, null);
        } else {
            return null;
        }
    }
}

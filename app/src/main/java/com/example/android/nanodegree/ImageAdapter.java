package com.example.android.nanodegree;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by spruisken on 12/12/15.
 */
public class ImageAdapter extends ArrayAdapter<Movie> {
    private static final String LOG_TAG = ImageAdapter.class.getSimpleName();

    public ImageAdapter(Activity context, List<Movie> movies) {
        super(context, 0, movies);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Log.v(LOG_TAG, "Get View at Position: " + Integer.toString(position));

        Movie movie = getItem(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.grid_item_movie, parent, false);
        }

        ImageView thumbnail = (ImageView) convertView.findViewById(R.id.movie_thumbnail);
        Picasso.with(getContext()).load(movie.getThumbnailPath()).into(thumbnail);

        return convertView;
    }
}

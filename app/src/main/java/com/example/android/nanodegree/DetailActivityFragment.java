package com.example.android.nanodegree;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by spruisken on 12/14/15.
 */
public class DetailActivityFragment extends Fragment {

    private final String LOG_TAG = DetailActivity.class.getSimpleName();
    Movie movie;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getActivity().getIntent();
        if(intent != null) {
            this.movie = (Movie) intent.getSerializableExtra("EXTRA_MOVIE_INFO");

        }

        getActivity().setTitle(this.movie.title);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

        ImageView thumbnail = (ImageView) rootView.findViewById(R.id.detail_view_thumbnail);
        TextView synopsisView = (TextView) rootView.findViewById(R.id.detail_view_synopsis);
        TextView ratingView = (TextView) rootView.findViewById(R.id.detail_view_rating);
        TextView releaseDateView = (TextView) rootView.findViewById(R.id.detail_view_release_date);

        Picasso.with(getContext()).load(this.movie.getThumbnailPath()).into(thumbnail);
        synopsisView.setText("Synopsis: " + this.movie.plot_synopsis);
        ratingView.setText("Rating: " + this.movie.user_rating);
        releaseDateView.setText("Release Date: " + this.movie.release_date);

        return rootView;

    }
}

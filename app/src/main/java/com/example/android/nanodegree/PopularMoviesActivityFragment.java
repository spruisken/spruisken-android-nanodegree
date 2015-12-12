package com.example.android.nanodegree;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by spruisken on 12/12/15.
 */
public class PopularMoviesActivityFragment extends Fragment {

    

    public PopularMoviesActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_popular_movies, container, false);
        return rootView;
    }
}
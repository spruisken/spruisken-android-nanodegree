package com.example.android.nanodegree;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by spruisken on 12/14/15.
 */
public class DetailActivityFragment extends Fragment {

    Movie movie;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

        Intent intent = getActivity().getIntent();
        if(intent != null) {
            this.movie = (Movie) intent.getSerializableExtra("EXTRA_MOVIE_INFO");

        }

        return rootView;

    }
}

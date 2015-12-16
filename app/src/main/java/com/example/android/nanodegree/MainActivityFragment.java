package com.example.android.nanodegree;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class MainActivityFragment extends Fragment {

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        Button popularMoviesButton = (Button) rootView.findViewById(R.id.button_popular_movies);

        popularMoviesButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //launch Popular Movies Project.
                Intent intent = new Intent(getActivity(), PopularMoviesActivity.class);
                startActivity(intent);
            }
        });

        return rootView;
    }

}

package com.example.android.nanodegree;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by spruisken on 12/12/15.
 */
public class PopularMoviesActivityFragment extends Fragment {

    private ImageAdapter mMoviesAdapter;

    public PopularMoviesActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        List<Movie> movies = new ArrayList<>();

        mMoviesAdapter = new ImageAdapter(
                getActivity(),
                movies
        );

        View rootView = inflater.inflate(R.layout.fragment_popular_movies, container, false);
        GridView gridView = (GridView) rootView.findViewById(R.id.movies_grid);
        gridView.setAdapter(mMoviesAdapter);
        FetchMoviesTask moviesTask = new FetchMoviesTask(getActivity(),mMoviesAdapter);
        moviesTask.execute("discover","movie","popularity","w185");

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //launch Movie Detail Activity
                Movie movie  = mMoviesAdapter.getItem(position);
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra("EXTRA_MOVIE_INFO",movie);
                startActivity(intent);
            }
        });

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.moviesfragment,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        FetchMoviesTask moviesTask = new FetchMoviesTask(getActivity(),mMoviesAdapter);

        if(id == R.id.action_sort_by_popularity) {
            moviesTask.execute("discover","movie","popularity","w185");
        } else if(id == R.id.action_sort_by_rating) {
            moviesTask.execute("discover","movie","rating","w185");
        }

        return super.onOptionsItemSelected(item);
    }

}
package com.example.android.nanodegree;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
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

        List<Movie> movies = new ArrayList<Movie>();

        mMoviesAdapter = new ImageAdapter(
                getActivity(),
                movies
        );

        View rootView = inflater.inflate(R.layout.fragment_popular_movies, container, false);
        GridView gridView = (GridView) rootView.findViewById(R.id.movies_grid);
        gridView.setAdapter(mMoviesAdapter);
        FetchMoviesTask moviesTask = new FetchMoviesTask();
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
        FetchMoviesTask moviesTask = new FetchMoviesTask();

        if(id == R.id.action_sort_by_popularity) {
            moviesTask.execute("discover","movie","popularity","w185");
        } else if(id == R.id.action_sort_by_rating) {
            moviesTask.execute("discover","movie","rating","w185");
        }

        return super.onOptionsItemSelected(item);
    }

    public class FetchMoviesTask extends AsyncTask<String, Void, List<Movie>> {

        private final String LOG_TAG = FetchMoviesTask.class.getSimpleName();

        private List<Movie> getMovieDataFromJson(String popularMovies, String size) throws JSONException {

            final String M_RESULTS = "results";
            final String M_TITLE = "title";
            final String M_THUMBNAIL_PATH = "poster_path";
            final String M_PLOT_SYNOPSIS = "overview";
            final String M_RATING = "vote_average";
            final String M_RELEASE_DATE = "release_date";

            JSONObject moviesJson = new JSONObject(popularMovies);
            JSONArray moviesArray = moviesJson.getJSONArray(M_RESULTS);

            List<Movie> movies =  new ArrayList <Movie>();

            for(int i=0; i< moviesArray.length(); i++) {

                JSONObject movieJson = moviesArray.getJSONObject(i);
                String title = movieJson.getString(M_TITLE);
                String plot_synopsis = movieJson.getString(M_PLOT_SYNOPSIS);
                String user_rating = movieJson.getString(M_RATING);
                String release_date = movieJson.getString(M_RELEASE_DATE);

                String thumbnail_path = movieJson.getString(M_THUMBNAIL_PATH);

                String base_url = "http://image.tmdb.org/t/p/";

                Uri builtUri = Uri.parse(base_url).buildUpon()
                        .appendEncodedPath(size)
                        .appendEncodedPath(thumbnail_path)
                        .build();

                String thumbnail_full_path = builtUri.toString();

                Log.v(LOG_TAG, "Movie Title " + title);
                Log.v(LOG_TAG, "Built URI " + thumbnail_full_path);
                Log.v(LOG_TAG, "Plot Synopsis " + plot_synopsis);
                Log.v(LOG_TAG, "User Rating " + user_rating);
                Log.v(LOG_TAG, "Release Date " + release_date);

                Movie movie = new Movie(title,thumbnail_full_path,plot_synopsis,user_rating,release_date);
                movies.add(movie);

            }

            return movies;

        }

        @Override
        protected List<Movie> doInBackground(String... params) {

            if(params.length == 0) {
                //must specify order: Top-Rated or Most Popular
                return null;
            }

            // These two need to be declared outside the try/catch
            // so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String moviesJsonStr = null;

            try {
                final String MOVIES_BASE_URL = "http://api.themoviedb.org/3";
                final String SORT_PARAM = "sort_by";
                final String API_KEY = "api_key";

                Uri builtUri = Uri.parse(MOVIES_BASE_URL).buildUpon()
                        .appendEncodedPath(params[0])
                        .appendEncodedPath(params[1])
                        .appendQueryParameter(SORT_PARAM, params[2])
                        .appendQueryParameter(API_KEY,getResources().getString(R.string.movies_api_key))
                        .build();

                URL url = new URL(builtUri.toString());

                Log.v(LOG_TAG, "Built URI " + builtUri.toString());

                // Create the request to OpenWeatherMap, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }

                moviesJsonStr = buffer.toString();

            } catch (IOException e) {

                Log.e(LOG_TAG, "Error ", e);
                Log.e(LOG_TAG, Log.getStackTraceString(e));
                return null;

            } finally {
                if(urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }

            try {
                return getMovieDataFromJson(moviesJsonStr,params[3]);
            } catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(List<Movie> result) {
            if(result != null) {
                mMoviesAdapter.clear();
                for(Movie movie : result) {
                    mMoviesAdapter.add(movie);
                }
            }

        }
    }
}
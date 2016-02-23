package com.example.android.nanodegree.app.data;

import android.provider.BaseColumns;

/**
 * Created by spruisken on 2/22/16.
 */

public class MovieContract {

    public static final class MovieEntry implements BaseColumns {

        public static final String TABLE_NAME = "movie";

        public static final String TITLE = "title";
        public static final String THUMBNAIL_PATH = "thumbnail_path";
        public static final String PLOT_SYNOPSIS = "plot_synopsis";
        public static final String USER_RATING = "user_rating";
        public static final String RELEASE_DATE = "release_date";
    }
}

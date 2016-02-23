package com.example.android.nanodegree.app.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.nanodegree.app.data.MovieContract.MovieEntry;

/**
 * Created by spruisken on 2/22/16.
 */
public class MovieDbHelper extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;
    static final String DATABASE_NAME = "movie.db";

    public MovieDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_MOVIE_TABLE = "CREATE TABLE " + MovieEntry.TABLE_NAME + " (" +

                // the ID of the location entry associated with this weather data
                MovieEntry.TITLE + " TEXT NOT NULL, " +
                MovieEntry.THUMBNAIL_PATH + " TEXT NOT NULL, " +
                MovieEntry.PLOT_SYNOPSIS + " TEXT NOT NULL, " +
                MovieEntry.USER_RATING + " TEXT NOT NULL, " +

                MovieEntry.RELEASE_DATE + " REAL NOT NULL, " +

                // To assure the application have just one movie entry per title
                // per release date, it's created a UNIQUE constraint with REPLACE strategy
                " UNIQUE (" + MovieEntry.TITLE + ", " +
                MovieEntry.RELEASE_DATE + ") ON CONFLICT REPLACE);";

        sqLiteDatabase.execSQL(SQL_CREATE_MOVIE_TABLE);
    }

    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MovieEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

}

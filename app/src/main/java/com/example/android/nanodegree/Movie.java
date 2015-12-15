package com.example.android.nanodegree;

import android.widget.ImageView;

import java.io.Serializable;

/**
 * Created by spruisken on 12/12/15.
 */
public class Movie implements Serializable {
    String title;
    String thumbnail_path;
    String plot_synopsis;
    String user_rating;
    String release_date;
    ImageView thumbnail;

    public Movie(String title, String thumbnail_path, String plot_synopsis, String user_rating, String release_date) {
        this.title = title;
        this.thumbnail_path = thumbnail_path;
        this.plot_synopsis = plot_synopsis;
        this.user_rating = user_rating;
        this.release_date = release_date;
    }

    public String getThumbnailPath() {
        return this.thumbnail_path;
    }

    public void setThumbnail(ImageView thumbnail) {
        this.thumbnail = thumbnail;
    }

    public ImageView getThumbnail() {
        return this.thumbnail;
    }

}

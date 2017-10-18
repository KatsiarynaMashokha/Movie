package com.example.guest.movieapp.models;

import java.util.ArrayList;

/**
 * Created by Guest on 10/18/17.
 */

public class Movie {
    private String title;
    private int rating;
    private String description;
    private String release;
    private String image_url;

    public Movie(String title, int rating, String description, String release, String image_url) {
        this.title = title;
        this.rating = rating;
        this.description = description;
        this.release = release;
        this.image_url = getImagePath(image_url);
    }

    public String getTitle() {
        return title;
    }


    public int getRating() {
        return rating;
    }

    public String getDescription() {
        return description;
    }


    public String getRelease() {
        return release;
    }

    public String getImage_url() {
        return image_url;
    }

    public String getImagePath(String url) {
        return "http://image.tmdb.org/t/p/w185/" + url;
    }
}

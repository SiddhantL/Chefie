package com.snltech.siddhantlad.chefiecompile.DatabaseSource;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class TrackRecipe {
    private String id;
    private String trackName;
    private int rating;

    public TrackRecipe() {

    }

    public TrackRecipe(String id, String trackName, int rating) {
        this.trackName = trackName;
        this.rating = rating;
        this.id = id;
    }

    public String getTrackName() {
        return trackName;
    }

    public int getRating() {
        return rating;
    }
}
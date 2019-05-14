package com.example.siddhantlad.chefiecompile;
import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Rate {
    private Double rating;

    public Rate(){
        //this constructor is required
    }

    public Rate(Double rating) {
        this.rating = rating;
    }

    public Double getRating() {
        return rating;
    }
}

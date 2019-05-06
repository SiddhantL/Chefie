package com.example.siddhantlad.chefiecompile;
import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Rate {
    private Float rating;

    public Rate(){
        //this constructor is required
    }

    public Rate(Float rating) {
        this.rating = rating;
    }

    public Float getRating() {
        return rating;
    }
}

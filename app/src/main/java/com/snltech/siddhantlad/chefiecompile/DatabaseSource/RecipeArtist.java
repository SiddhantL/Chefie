package com.snltech.siddhantlad.chefiecompile.DatabaseSource;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class RecipeArtist {
    private String artistId;
    private String artistName;
    private String artistGenre;
    private String Ingredient0;
    public String ingredientCheck;
    public RecipeArtist(){
        //this constructor is required

    }

    public RecipeArtist(String artistId, String artistName, String artistGenre) {
        this.artistId = artistId;
        this.artistName = artistName;
        this.artistGenre = artistGenre;
    }

    public String getArtistId() {
        return artistId;
    }

    public String getArtistName() {
        return artistName;
    }

    public String getArtistGenre() {
        return artistGenre;
    }

    public String getIngredient0() { return ingredientCheck; }
}

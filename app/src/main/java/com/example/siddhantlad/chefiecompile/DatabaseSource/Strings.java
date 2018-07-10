package com.example.siddhantlad.chefiecompile.DatabaseSource;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Strings {
    private String RecipeId;
    private String RecipeName;
    private String RecipeType;

    public Strings(){

    }

    public Strings(String RecipeId, String RecipeName, String RecipeType) {
        this.RecipeId = RecipeId;
        this.RecipeName = RecipeName;
        this.RecipeType = RecipeType;
    }

    public String getRecipeId() {
        return RecipeId;
    }

    public String getRecipeName() {
        return RecipeName;
    }

    public String getRecipeType() {
        return RecipeType;
    }
}
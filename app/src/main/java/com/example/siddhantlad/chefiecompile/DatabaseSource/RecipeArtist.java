package com.example.siddhantlad.chefiecompile.DatabaseSource;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class RecipeArtist {
    private String artistId;
    private String artistName;
    private String artistGenre;
    private String Ingredient0;
    public String ingredientCheck;
  /*  private String Ingredient1;
    private String Ingredient2;
    private String Ingredient3;
    private String Ingredient4;
    private String Ingredient5;
    private String Ingredient6;
    private String Ingredient7;
    private String Ingredient8;
    private String Ingredient9;
    private String Ingredient10;
    private String Ingredient11;
    private String Ingredient12;
    private String Ingredient13;
    private String Ingredient14;
    private String Ingredient15;
    private String Ingredient16;
    private String Ingredient17;
    private String Ingredient18;
    private String Ingredient19;
    private String Ingredient20;
    private String Ingredient21;
    private String Ingredient22;
    private String Ingredient23;
    private String Ingredient24;
    private String Ingredient25;*/

    public RecipeArtist(){
        //this constructor is required

    }

    public RecipeArtist(String artistId, String artistName, String artistGenre) {
        this.artistId = artistId;
        this.artistName = artistName;
        this.artistGenre = artistGenre;
        this.Ingredient0= Ingredient0;
       /* this.Ingredient1= Ingredient1;
        this.Ingredient2= Ingredient2;
        this.Ingredient3= Ingredient3;
        this.Ingredient4= Ingredient4;
        this.Ingredient5= Ingredient5;
        this.Ingredient6= Ingredient6;
        this.Ingredient7= Ingredient7;
        this.Ingredient8= Ingredient8;
        this.Ingredient9= Ingredient9;
        this.Ingredient10= Ingredient10;
        this.Ingredient11= Ingredient11;
        this.Ingredient12= Ingredient12;
        this.Ingredient13= Ingredient13;
        this.Ingredient14= Ingredient14;
        this.Ingredient15= Ingredient15;
        this.Ingredient16= Ingredient16;
        this.Ingredient17= Ingredient17;
        this.Ingredient18= Ingredient18;
        this.Ingredient19= Ingredient19;
        this.Ingredient20= Ingredient20;
        this.Ingredient21= Ingredient21;
        this.Ingredient22= Ingredient22;
        this.Ingredient23= Ingredient23;
        this.Ingredient24= Ingredient24;
        this.Ingredient25= Ingredient25;*/

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

    /*public String getIngredient1() { return Ingredient1; }

    public String getIngredient2() { return Ingredient2; }

    public String getIngredient3() { return Ingredient3; }

    public String getIngredient4() { return Ingredient4; }

    public String getIngredient5() { return Ingredient5; }

    public String getIngredient6() { return Ingredient6; }

    public String getIngredient7() { return Ingredient7; }

    public String getIngredient8() { return Ingredient8; }

    public String getIngredient9() { return Ingredient9; }

    public String getIngredient10() { return Ingredient10; }

    public String getIngredient11() { return Ingredient11; }

    public String getIngredient12() { return Ingredient12; }

    public String getIngredient13() { return Ingredient13; }

    public String getIngredient14() { return Ingredient14; }

    public String getIngredient15() { return Ingredient15; }

    public String getIngredient16() { return Ingredient16; }

    public String getIngredient17() { return Ingredient17; }

    public String getIngredient18() { return Ingredient18; }

    public String getIngredient19() { return Ingredient19; }

    public String getIngredient20() { return Ingredient20; }

    public String getIngredient21() { return Ingredient21; }

    public String getIngredient22() { return Ingredient22; }

    public String getIngredient23() { return Ingredient23; }

    public String getIngredient24() { return Ingredient24; }

    public String getIngredient25() { return Ingredient25; }*/
}

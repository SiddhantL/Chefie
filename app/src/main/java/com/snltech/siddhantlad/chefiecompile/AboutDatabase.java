package com.snltech.siddhantlad.chefiecompile;
import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class AboutDatabase {
    private String aboutme;
    private String aboutchefie;
    private String aboutsnltech;
    public AboutDatabase(){
        //this constructor is required
    }

    public AboutDatabase(String aboutme,String aboutchefie,String aboutsnltech) {
        this.aboutme = aboutme;
        this.aboutme = aboutchefie;
        this.aboutme = aboutsnltech;
    }

    public String getAboutme() {
        return aboutme;
    }
    public String getAboutchefie() {
        return aboutchefie;
    }
    public String getAboutsnltech() {
        return aboutsnltech;
    }
}

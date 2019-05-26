package com.snltech.siddhantlad.chefiecompile;
import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class users {
    private String Username;
    public users(){
        //this constructor is required
    }

    public users(String Username) {
        this.Username = Username;
    }

    public String getUserNameFromUUID() {
        return Username;
    }
}

package com.example.siddhantlad.chefiecompile;
import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class UserInfo {
    private String Phone;
    private String Username;
    private String Email;
    public UserInfo(){
        //this constructor is required
    }

    public UserInfo(String Phone, String Username, String Email) {
        this.Phone = Phone;
        this.Username=Username;
        this.Email=Email;
    }

    public String getPhone() {
        return Phone;
    }
    public String getUsername() {
        return Username;
    }
    public String getEmail() {
        return Email;
    }

}

package com.snltech.siddhantlad.chefiecompile;
import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class UserDetails {
    private String Message;
    private String Username;
    private String Email;
    public UserDetails(){
        //this constructor is required
    }

    public UserDetails(String Message, String Username, String Email) {
        this.Message = Message;
        this.Username=Username;
        this.Email=Email;
    }

    public String getMessage() {
        return Message;
    }
    public String getUsername() {
        return Username;
    }
    public String getEmail() {
        return Email;
    }

}

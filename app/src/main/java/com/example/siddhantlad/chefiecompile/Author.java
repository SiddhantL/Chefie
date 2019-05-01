package com.example.siddhantlad.chefiecompile;
import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Author {
    private String author;
    private String Username;
    private String Email;
    public Author(){
        //this constructor is required
    }

    public Author(String author,String Username,String Email) {
        this.author = author;
        this.Username=Username;
        this.Email=Email;
        }

    public String getAuthor() {
        return author;
    }
    public String getUsername() {
        return Username;
    }
    public String getEmail() {
        return Email;
    }

}

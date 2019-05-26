package com.snltech.siddhantlad.chefiecompile;
import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Author {
    private String author;
    private String Username;
    private String Email;
    private String YouTube;
    public Author(){
        //this constructor is required
    }

    public Author(String author,String Username,String Email, String YouTube) {
        this.author = author;
        this.Username=Username;
        this.Email=Email;
        this.YouTube=YouTube;
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

    public String getYouTube() {
        return YouTube;
    }
}

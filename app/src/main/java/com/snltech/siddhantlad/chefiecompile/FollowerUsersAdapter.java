/*
package com.example.siddhantlad.chefiecompile;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.siddhantlad.chefiecompile.Author;
import com.example.siddhantlad.chefiecompile.DisplayRecipeInfo;
import com.example.siddhantlad.chefiecompile.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;


public class FollowerUsersAdapter extends ArrayAdapter<String> {
    private Activity context;
    List<String> artists;
    TextView followerID;
    public FollowerUsersAdapter(Activity context, List<String> artists) {
        super(context, R.layout.followusers, artists);
        this.context = context;
        this.artists = artists;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.followusers, null, true);
        followerID= (TextView)listViewItem.findViewById(R.id.textFollow);
        for (int i=0;i<artists.size();i++) {
            followerID.setText(artists.get(i));
        }
           return listViewItem;
    }
}*/

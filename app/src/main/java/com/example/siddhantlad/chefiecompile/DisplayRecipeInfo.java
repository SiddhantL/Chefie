package com.example.siddhantlad.chefiecompile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.siddhantlad.chefiecompile.DatabaseSource.Artist;
import com.example.siddhantlad.chefiecompile.DatabaseSource.ArtistList;
import com.example.siddhantlad.chefiecompile.DatabaseSource.MainActivity2;
import com.example.siddhantlad.chefiecompile.DatabaseSource.RecipeArtist;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class DisplayRecipeInfo extends AppCompatActivity {
DatabaseReference mDatabase;
List<Artist> artists;
ListView listview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_recipe_info);
        Intent intent = getIntent();
        String RecipeName = intent.getExtras().getString("RecipeName");
        artists = new ArrayList<>();
        listview=(ListView)findViewById(R.id.listViewStep);
        mDatabase = FirebaseDatabase.getInstance().getReference("steps/"+RecipeName);
        TextView nameDisplayTV=(TextView)findViewById(R.id.nameDisplay);
        nameDisplayTV.setText(RecipeName);
        Toast.makeText(this, RecipeName, Toast.LENGTH_SHORT).show();
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("images/"+RecipeName+".jpg");

// ImageView in your Activity
        ImageView imageView = (ImageView)findViewById(R.id.imageDisplay);

        Glide.with(this /* context */)
                .load(storageReference)
                .into(imageView);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //clearing the previous artist list
                artists.clear();

                //iterating through all the nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //getting artist
                    Artist artist = postSnapshot.getValue(Artist.class);
                    //adding artist to the list
                    artists.add(artist);
                }

                //creating adapter
                ArtistList artistAdapter = new ArtistList(DisplayRecipeInfo.this, artists);
                //attaching adapter to the listview
                listview.setAdapter(artistAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
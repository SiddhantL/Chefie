package com.example.siddhantlad.chefiecompile;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.siddhantlad.chefiecompile.DatabaseSource.RecipeActivity;
import com.example.siddhantlad.chefiecompile.DatabaseSource.RecipeArtist;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RecipeListProfile extends AppCompatActivity {
    ListView listViewProfile;
    DatabaseReference mRecipeByName;
    ArrayList<String> recipeNames;
    int Count;
    ArrayAdapter<String> recipeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list_profile);
        listViewProfile = (ListView) findViewById(R.id.listViewProfile);
        Intent intent = getIntent();
        String uuid = intent.getStringExtra("uuid");
        mRecipeByName = FirebaseDatabase.getInstance().getReference("RecipeByName/"+uuid);
        recipeNames = new ArrayList<String>();
        recipeAdapter = new ArrayAdapter<String>(this, R.layout.layout_artist_list, recipeNames);
        Count=0;
        mRecipeByName.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                for (final DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Count++;
                    String name = postSnapshot.getKey().toString().trim();
                    Toast.makeText(RecipeListProfile.this, Integer.toString(Count), Toast.LENGTH_SHORT).show();
                    Toast.makeText(RecipeListProfile.this, name, Toast.LENGTH_LONG).show();
               //name contains all Recipe names, use the names and create another Datatree of same recipes but no - or use artistname method to take information of all recipes
                    //Or just copy Display Recipe info onClick for opening the Recipe and to show display picture using name and name is already saved as name
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}


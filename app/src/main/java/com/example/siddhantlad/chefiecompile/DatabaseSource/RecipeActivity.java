package com.example.siddhantlad.chefiecompile.DatabaseSource;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.siddhantlad.chefiecompile.DatabaseSource.ArtistActivity;
import com.example.siddhantlad.chefiecompile.DatabaseSource.RecipeArtist;
import com.example.siddhantlad.chefiecompile.DatabaseSource.RecipeArtistList;
import com.example.siddhantlad.chefiecompile.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RecipeActivity extends AppCompatActivity {
    private DatabaseReference mDatabase,userWritesRef;
    EditText editTextRecipeName;
    Spinner spinnerType;
    Context context;
    RecipeArtistList artistAdapter;
    ListView listViewRecipes;
    List<RecipeArtist> recipes;
    ArrayList<String> my_array_of_selected_ingredients;
    int Count;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        Intent intent = getIntent();
        my_array_of_selected_ingredients = intent.getStringArrayListExtra("my_array_of_selected_ingredients");
        mDatabase = FirebaseDatabase.getInstance().getReference("recipes");
        context=this;
        spinnerType = (Spinner) findViewById(R.id.spinnerGenres);
        listViewRecipes = (ListView) findViewById(R.id.listViewArtists);
        recipes = new ArrayList<>();
        listViewRecipes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), ArtistActivity.class);
                startActivity(intent);
            }
        });

    }

    private void addArtist() {
        String name = editTextRecipeName.getText().toString().trim();
        String type = spinnerType.getSelectedItem().toString();

        if (!TextUtils.isEmpty(name)) {
 String id = mDatabase.push().getKey();
            RecipeArtist artist = new RecipeArtist(id, name, type);

            mDatabase.child(id).setValue(artist);
            editTextRecipeName.setText("");
            Toast.makeText(this, "Artist added", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Please enter a name", Toast.LENGTH_LONG).show();
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
Count=0;
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                recipes.clear();

                for (final DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    RecipeArtist recipeartist = postSnapshot.getValue(RecipeArtist.class);
                    recipes.add(recipeartist);
                    final String name =postSnapshot.getKey().toString().trim();
                    final String nameFinal=name.substring(1);
                    final DatabaseReference dataref= FirebaseDatabase.getInstance().getReference().child("recipes").child(name);
                    dataref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot1) {
                            Count=Count+1;
   //                         Toast.makeText(context, Count, Toast.LENGTH_SHORT).show();
                            for (DataSnapshot postSnapshot1 : dataSnapshot1.getChildren()) {
                                final int CountSave=Count;
                                final String names = postSnapshot1.getKey().toString().trim();
                    dataref.child(names).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot2) {
                                        Boolean removeCheck=false;
                                        if (dataSnapshot2.getValue() != null) {
                                            if (!names.equals("artistName")){
                                            String ingredientName=dataSnapshot2.getValue().toString().trim();
                                            String recipeName=nameFinal;
                                            Toast.makeText(RecipeActivity.this, recipeName+": "+ingredientName, Toast.LENGTH_SHORT).show();
                                      if (!my_array_of_selected_ingredients.isEmpty()){
                                        if(my_array_of_selected_ingredients.contains(ingredientName)){
                                            Toast.makeText(context, recipeName+": Yes", Toast.LENGTH_SHORT).show();
                                        }else{
                                            Toast.makeText(context, recipeName+": No"+" "+CountSave, Toast.LENGTH_SHORT).show();
                                            /*try {
                                                recipes.remove(recipes.get(CountSave));
                                                listViewRecipes.invalidate();
                                              //  ((BaseAdapter) listViewRecipes.getAdapter()).notifyDataSetChanged();
                                                Toast.makeText(context, "Crash", Toast.LENGTH_SHORT).show();
                                                //  recipes.remove(listViewRecipes.getItemAtPosition(CountSave));
                                            }catch (Exception e){}*/
                                            }}
                                            } else {
                                           //---@@   Toast.makeText(context, "null value", Toast.LENGTH_SHORT).show();
                                          }
                                        }
                                    }
                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
                artistAdapter = new RecipeArtistList(RecipeActivity.this,recipes);
                listViewRecipes.setAdapter(artistAdapter);
                for (int Counting=0;Counting<=listViewRecipes.getAdapter().getCount()-1;Counting++){
                   /* RecipeArtist artist = recipes.get(Counting);
                    try {
                        Intent intent=new Intent(RecipeActivity.this,RecipeArtist.class);
                        Toast.makeText(context, artist.getArtistName(), Toast.LENGTH_SHORT).show();
                    }catch (Exception E){
                        Toast.makeText(context, "CRASH", Toast.LENGTH_SHORT).show();
                    }*/
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
     }
}

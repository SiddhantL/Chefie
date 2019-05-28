package com.snltech.siddhantlad.chefiecompile.DatabaseSource;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.snltech.siddhantlad.chefiecompile.AddRecipeFab;
import com.snltech.siddhantlad.chefiecompile.DisplayRecipeInfo;
import com.snltech.siddhantlad.chefiecompile.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RecipeActivity extends AppCompatActivity {
    private DatabaseReference mDatabase,userWritesRef;
    EditText editTextRecipeName;
    Spinner spinnerType;
    Context context;
    RecipeArtistList artistAdapter;
    String nameSave;
    TextView empty,emptyAdd;
    Button addButton;
    ListView listViewRecipes;
    List<RecipeArtist> recipes,filter;
    ArrayList<String> my_array_of_selected_ingredients,lowercase_my_array_of_selected_ingredients;
    ImageView noResult;
    ArrayList<String> adapterListName;
    int Count,CountSave,removeCount,rangeFilter;
    Boolean remove;
    List<Integer> removeList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        try {
            Intent intent = getIntent();
            addButton = (Button) findViewById(R.id.addButt);
            addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(RecipeActivity.this, MainActivity2.class));
                }
            });
            lowercase_my_array_of_selected_ingredients = new ArrayList<String>();
            my_array_of_selected_ingredients = intent.getStringArrayListExtra("my_array_of_selected_ingredients");
            rangeFilter = intent.getIntExtra("rangeFilter", 0);
            for (int z = 0; z < my_array_of_selected_ingredients.size(); z++) {
                lowercase_my_array_of_selected_ingredients.add(my_array_of_selected_ingredients.get(z).toLowerCase());
            }
            mDatabase = FirebaseDatabase.getInstance().getReference("recipes");
            context = this;
            empty = (TextView) findViewById(R.id.textView23);
            emptyAdd = (TextView) findViewById(R.id.textView24);
            noResult = (ImageView) findViewById(R.id.imageView5);
            removeList = new ArrayList<>();
            adapterListName = new ArrayList<String>();
            spinnerType = (Spinner) findViewById(R.id.spinnerGenres);
            listViewRecipes = (ListView) findViewById(R.id.listViewArtists);
            recipes = new ArrayList<>();
        }catch (Exception E){
            Toast.makeText(context, "Something Went Wrong", Toast.LENGTH_SHORT).show();
        }
    }

        private void addArtist () {
            String name = editTextRecipeName.getText().toString().trim();
            String type = spinnerType.getSelectedItem().toString();

            if (!TextUtils.isEmpty(name)) {
                String id = mDatabase.push().getKey();
                RecipeArtist artist = new RecipeArtist(id, name, type);

                mDatabase.child(id).setValue(artist);
                editTextRecipeName.setText("");
                Toast.makeText(this, "Artist added", Toast.LENGTH_LONG).show();
            } else {
                //   Toast.makeText(this, "Please enter a name", Toast.LENGTH_LONG).show();
            }
        }
        @Override
        protected void onStart () {
            super.onStart();
            try {
                Count = 0;
                mDatabase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(final DataSnapshot dataSnapshot) {
                        recipes.clear();
                        for (final DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            final RecipeArtist recipeartist = postSnapshot.getValue(RecipeArtist.class);
                            recipes.add(recipeartist);
                            remove = true;
                            final String name = postSnapshot.getKey().toString().trim();
                            final String nameFinal = name.substring(1);
                            adapterListName.add(nameFinal);
                            nameSave = adapterListName.get(0);
                            final DatabaseReference dataref = FirebaseDatabase.getInstance().getReference().child("recipes").child(name);
                            dataref.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot1) {
                                    Count = Count + 1;
                                    for (DataSnapshot postSnapshot1 : dataSnapshot1.getChildren()) {
                                        final int CountSave = Count;
                                        final String names = postSnapshot1.getKey().toString().trim();
                                        dataref.child(names).addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot2) {
                                                Boolean removeCheck = false;
                                                remove = true;
                                                if (dataSnapshot2.getValue() != null) {
                                                    if (!names.equals("artistName")) {
                                                        String ingredientName = dataSnapshot2.getValue().toString().trim();
                                                        String recipeName = nameFinal;
                                                        try {
                                                            if (!my_array_of_selected_ingredients.isEmpty()) {
                                                                if (lowercase_my_array_of_selected_ingredients.contains(ingredientName.toLowerCase())) {
                                                                } else {
                                                                    if (remove) {
                                                                        //   recipes.remove(recipeartist);
                                                                        if (!nameSave.equals(name)) {
                                                                            removeCount = 0;
                                                                            nameSave = name;
                                                                        }
                                                                        removeCount = removeCount + 1;
                                                              //          Toast.makeText(context, name + Integer.toString(removeCount), Toast.LENGTH_SHORT).show();
                                                                        if (removeCount > rangeFilter) {
                                                                            recipes.remove(recipeartist);
                                                                            adapterListName.remove(recipeName);
                                                                        }
                                                                        if (recipes.isEmpty()) {
                                                                            noResult.setVisibility(View.VISIBLE);
                                                                            empty.setVisibility(View.VISIBLE);
                                                                            emptyAdd.setVisibility(View.VISIBLE);
                                                                            addButton.setVisibility(View.VISIBLE);
                                                                        }
                                                                        // adapterListName.remove(recipeName);
                                                                        listViewRecipes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                                            @Override
                                                                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                                                                Intent intent = new Intent(getApplicationContext(), DisplayRecipeInfo.class);
                                                                                intent.putExtra("RecipeName", adapterListName.get(i).toString().trim()/*"CheckImage"*/);
                                                                                //Toast.makeText(RecipeActivity.this, adapterListName.get(i), Toast.LENGTH_SHORT).show();
                                                                                startActivity(intent);
                                                                            }
                                                                        });
                                                                        artistAdapter.notifyDataSetChanged();
                                                                        remove = false;
                                                                    } else {
                                                                    }
                                                                }
                                                            } else {
                                                            }
                                                        } catch (Exception E) {
                                                        }
                                                    } else {
                                                        String recName = dataSnapshot2.getValue().toString().trim();
                                                        if (recName.equals("Extra")) {
                                                            recipes.remove(recipeartist);
                                                            adapterListName.remove(recName);
                                                        }
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
                        artistAdapter = new RecipeArtistList(RecipeActivity.this, recipes);
                        listViewRecipes.setAdapter(artistAdapter);
                        for (int Counting = 0; Counting <= listViewRecipes.getAdapter().getCount() - 1; Counting++) {
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }catch (Exception E){
                Toast.makeText(context, "Something Went Wrong", Toast.LENGTH_SHORT).show();
            }
        }
}

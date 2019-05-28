package com.snltech.siddhantlad.chefiecompile;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.snltech.siddhantlad.chefiecompile.DatabaseSource.MainActivity2;

import java.util.ArrayList;
import java.util.List;

public class RecipeListProfile extends AppCompatActivity {
    ListView listViewProfile;
    DatabaseReference mRecipeByName;
    List<String> recipeNames;
    List<String> all_profile_recipes;
    TextView empty,emptyAdd;
    Button addRec;
    ImageView noResult;
    int Count;
    ArrayAdapter<String> recipeAdapter;
    RecipeProfileList adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list_profile);
        listViewProfile = (ListView) findViewById(R.id.listViewProfile);
        Intent intent = getIntent();
        addRec=(Button)findViewById(R.id.addButt);
        addRec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RecipeListProfile.this, MainActivity2.class));
            }
        });
        String uuid = intent.getStringExtra("uuid");
        all_profile_recipes=new ArrayList<String>();
        mRecipeByName = FirebaseDatabase.getInstance().getReference("RecipeByName/"+uuid);
        recipeNames = new ArrayList<String>();
        recipeAdapter = new ArrayAdapter<String>(this, R.layout.layout_artist_list, recipeNames);
        Count=0;
        empty=(TextView)findViewById(R.id.textView23);
        emptyAdd=(TextView)findViewById(R.id.textView24);
        noResult=(ImageView)findViewById(R.id.imageView5);

        mRecipeByName.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                all_profile_recipes.clear();
                for (final DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Count++;
                    String name = postSnapshot.getKey().toString().trim();
                 //   Toast.makeText(RecipeListProfile.this, Integer.toString(Count), Toast.LENGTH_SHORT).show();
                  //  Toast.makeText(RecipeListProfile.this, name, Toast.LENGTH_LONG).show();
                    String subName=name.substring(1);
                    all_profile_recipes.add(subName);
                    adapter=new RecipeProfileList(RecipeListProfile.this,all_profile_recipes);
                    listViewProfile.setAdapter(adapter);

               //name contains all Recipe names, use the names and create another Datatree of same recipes but no - or use artistname method to take information of all recipes
                    //Or just copy Display Recipe info onClick for opening the Recipe and to show display picture using name and name is already saved as name
                }
                if (all_profile_recipes.isEmpty()){
                    noResult.setVisibility(View.VISIBLE);
                    empty.setVisibility(View.VISIBLE);
                    emptyAdd.setVisibility(View.VISIBLE);
                    addRec.setVisibility(View.VISIBLE);
                }else{
                    noResult.setVisibility(View.GONE);
                    addRec.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        listViewProfile.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) { Intent intent = new Intent(getApplicationContext(), DisplayRecipeInfo.class);
            String filterIntent=adapter.getItem(i);
            intent.putExtra("RecipeName",filterIntent.toString().trim());/*"CheckImage"*/
           //     Toast.makeText(RecipeListProfile.this,filterIntent.toString(), Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });

    }
}


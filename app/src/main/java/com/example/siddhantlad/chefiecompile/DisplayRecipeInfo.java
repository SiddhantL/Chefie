package com.example.siddhantlad.chefiecompile;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.siddhantlad.chefiecompile.DatabaseSource.Artist;
import com.example.siddhantlad.chefiecompile.DatabaseSource.ArtistList;
import com.example.siddhantlad.chefiecompile.DatabaseSource.MainActivity2;
import com.example.siddhantlad.chefiecompile.DatabaseSource.RecipeArtist;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
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
DatabaseReference mDatabase,creditDatabase;
List<Artist> artists;
ListView listview;
FirebaseAuth mAuth;
private ArrayAdapter<String>adapter;
TextView authortv;
private ListView listView;
private ArrayList<String> arrayList=new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_recipe_info);
        Intent intent = getIntent();
        String RecipeName = intent.getExtras().getString("RecipeName");
        artists = new ArrayList<>();
        listview=(ListView)findViewById(R.id.listViewStep);
        authortv=(TextView)findViewById(R.id.textView12);
        mDatabase = FirebaseDatabase.getInstance().getReference("steps/"+RecipeName);
        creditDatabase = FirebaseDatabase.getInstance().getReference("credits/"+RecipeName);
        TextView nameDisplayTV=(TextView)findViewById(R.id.nameDisplay);
        nameDisplayTV.setText(RecipeName);
        authortv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                creditDatabase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Author author = dataSnapshot.getValue(Author.class);
                        Intent profile=new Intent(DisplayRecipeInfo.this,ProfileActivity.class);
                        profile.putExtra("uuid",author.getAuthor().toString());
                        startActivity(profile);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
        creditDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                       Author author = dataSnapshot.getValue(Author.class);
                       authortv.setText(author.getUsername().toString());
                        }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        Toast.makeText(this, RecipeName, Toast.LENGTH_SHORT).show();
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("images/"+RecipeName+".jpg");
        StorageReference storageReferencePlaceholder = FirebaseStorage.getInstance().getReference().child("images/Empty.jpg");
adapter=new ArrayAdapter<String>(this, R.layout.simple_list_item_green,arrayList);
// ImageView in your Activity
        ImageView imageView = (ImageView)findViewById(R.id.imageDisplay);
      /*  Glide.with(this *//* context *//*)
                .load(storageReference)
                .into(imageView);*/
        Glide.with(this)
                .load(storageReference).apply(new RequestOptions().placeholder(R.drawable.lunchpic)).into(imageView);
        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
String string= dataSnapshot.getValue(String.class);
arrayList.add(string);
adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        listview.setAdapter(adapter);
    }
}
package com.snltech.siddhantlad.chefiecompile;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.snltech.siddhantlad.chefiecompile.DatabaseSource.Artist;
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
DatabaseReference mDatabase,creditDatabase,rateDatabase;
List<Artist> artists;
ListView listview;
RatingBar rateBar;
Button youTubeClick;
FirebaseAuth mAuth;
private ArrayAdapter<String>adapter;
TextView authortv;
Double CountMean,Summation,NoValue,Average;
private ListView listView;
private ArrayList<String> arrayList=new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_recipe_info);
        Intent intent = getIntent();
        final String RecipeName = intent.getExtras().getString("RecipeName");
        artists = new ArrayList<>();
        listview=(ListView)findViewById(R.id.listViewStep);
        mAuth= FirebaseAuth.getInstance();
        youTubeClick=(Button)findViewById(R.id.button3);
        authortv=(TextView)findViewById(R.id.textView12);
        mDatabase = FirebaseDatabase.getInstance().getReference("steps/"+RecipeName);
        creditDatabase = FirebaseDatabase.getInstance().getReference("credits/"+RecipeName);
        rateBar=(RatingBar)findViewById(R.id.ratingBar3);
        rateDatabase = FirebaseDatabase.getInstance().getReference("rate/"+RecipeName);
        TextView nameDisplayTV=(TextView)findViewById(R.id.nameDisplay);
        nameDisplayTV.setText(RecipeName);
        //   CountMean=0;
        rateDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                for (final DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    String rates = postSnapshot.getKey().toString().trim();
                    Summation=0.0;
                    rateDatabase.child(rates).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot2) {
                            for(final DataSnapshot postSnapshot2 : dataSnapshot2.getChildren()){
                            String Value=String.valueOf(postSnapshot2.getValue());
                               //Toast.makeText(DisplayRecipeInfo.this, Value, Toast.LENGTH_SHORT).show();
                                Summation=Double.parseDouble(Value)+Summation;
                                NoValue=Double.parseDouble(Long.toString(dataSnapshot.getChildrenCount()));
                               Average=Double.parseDouble(Summation.toString())/Double.parseDouble(NoValue.toString());
             //                   Toast.makeText(DisplayRecipeInfo.this, Double.toString(Average), Toast.LENGTH_SHORT).show();
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
        rateBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {
      //          Toast.makeText(DisplayRecipeInfo.this, Float.toString(rating), Toast.LENGTH_SHORT).show();
                String id=mAuth.getCurrentUser().getUid();
                rateDatabase.child(id).child("rating").setValue(rating);
            }
        });
        authortv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                creditDatabase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Author author = dataSnapshot.getValue(Author.class);
                        Intent profile=new Intent(DisplayRecipeInfo.this,ProfileActivity.class);
                        profile.putExtra("uuid",author.getAuthor().toString());
                        profile.putExtra("nameProfile",author.getUsername().toString());
                        profile.putExtra("emailProfile",author.getEmail().toString());
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
      //  Toast.makeText(this, RecipeName, Toast.LENGTH_SHORT).show();
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("images/"+RecipeName+"/"+RecipeName+".jpg");
        StorageReference storageReferencePlaceholder = FirebaseStorage.getInstance().getReference().child("images/Empty.jpg");
adapter=new ArrayAdapter<String>(this, R.layout.simple_list_item_green,arrayList);
// ImageView in your Activity
        ImageView imageView = (ImageView)findViewById(R.id.imageDisplay);
      /*  Glide.with(this *//* context *//*)
                .load(storageReference)
                .into(imageView);*/
        Glide.with(this)
                .load(storageReference).apply(new RequestOptions().placeholder(R.drawable.wrap_lunchpic)).into(imageView);
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
        youTubeClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                creditDatabase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        final Author author = dataSnapshot.getValue(Author.class);
                        if (author.getYouTube() != null) {
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setData(Uri.parse(author.getYouTube()));
                            Toast.makeText(DisplayRecipeInfo.this, author.getYouTube(), Toast.LENGTH_SHORT).show();
                            intent.setPackage("com.google.android.youtube");
                            startActivity(intent);
                        } else {
                            Toast.makeText(DisplayRecipeInfo.this, "No Video Available", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

    }
}
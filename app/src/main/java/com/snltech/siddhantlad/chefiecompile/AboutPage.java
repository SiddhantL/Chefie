package com.snltech.siddhantlad.chefiecompile;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class AboutPage extends AppCompatActivity {
DatabaseReference aboutMe;
TextView AboutMeTextSet,AboutChefieTextSet,AboutSNLTextSet;
ListView list;
ArrayList<String> arrays;
ArrayAdapter<String> arrayAdapt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_page);
        try {
            AboutMeTextSet = (TextView) findViewById(R.id.textView32);
            AboutChefieTextSet = (TextView) findViewById(R.id.textView34);
            AboutSNLTextSet = (TextView) findViewById(R.id.textView36);
            list = (ListView) findViewById(R.id.listView);
            StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("ceoImage/" + "ceoImage.jpg");
            StorageReference storageReferencePlaceholder = FirebaseStorage.getInstance().getReference().child("images/Empty.jpg");
            aboutMe = FirebaseDatabase.getInstance().getReference("AboutMe");
            arrays = new ArrayList<String>();
            arrays.add("Version 1.1.1");
            arrayAdapt = new ArrayAdapter<String>(this, R.layout.simple_list_item_white, arrays);
            list.setAdapter(arrayAdapt);
            aboutMe.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    try {
                        AboutDatabase aboutText = dataSnapshot.getValue(AboutDatabase.class);
                        AboutMeTextSet.setText(aboutText.getAboutme().toString());
                        AboutChefieTextSet.setText(aboutText.getAboutchefie().toString());
                        AboutSNLTextSet.setText(aboutText.getAboutsnltech().toString());
                    } catch (Exception E) {
                        Toast.makeText(AboutPage.this, "Error Occurred", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            ImageView imageView = (ImageView) findViewById(R.id.imageView6);
            /*  Glide.with(this *//* context *//*)
                .load(storageReference)
                .into(imageView);*/
            Glide.with(this)
                    .load(storageReference).apply(new RequestOptions().placeholder(R.drawable.coderimage)).into(imageView);
        }catch (Exception E){
            Toast.makeText(this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
        }
    }
}

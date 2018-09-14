package com.example.siddhantlad.chefiecompile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class DisplayRecipeInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_recipe_info);
        Intent intent = getIntent();
        String RecipeName = intent.getExtras().getString("RecipeName");
        TextView nameDisplayTV=(TextView)findViewById(R.id.nameDisplay);
        nameDisplayTV.setText(RecipeName);
        Toast.makeText(this, RecipeName, Toast.LENGTH_SHORT).show();
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("images/"+RecipeName+".jpg");

// ImageView in your Activity
        ImageView imageView = (ImageView)findViewById(R.id.imageDisplay);

        Glide.with(this /* context */)
                .load(storageReference)
                .into(imageView);
    }
}
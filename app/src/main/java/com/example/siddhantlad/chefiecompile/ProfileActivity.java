package com.example.siddhantlad.chefiecompile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class ProfileActivity extends AppCompatActivity {
    TextView nameTV,emailTV;
    DatabaseReference usersRecipeDatabase;
    FirebaseAuth mAuth;
    Button profilenext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        final Intent intent = getIntent();
        final String uuid = intent.getStringExtra("uuid");
        String email = intent.getStringExtra("emailProfile");
        String name = intent.getStringExtra("nameProfile");
        profilenext=(Button)findViewById(R.id.myRecipes);
        nameTV=(TextView)findViewById(R.id.textView8);
        emailTV=(TextView)findViewById(R.id.textView9);
        Toast.makeText(this, uuid, Toast.LENGTH_SHORT).show();
        nameTV.setText(name);
        emailTV.setText(email);
        profilenext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent uuidSend=new Intent(ProfileActivity.this,RecipeListProfile.class);
                uuidSend.putExtra("uuid",uuid);
                startActivity(uuidSend);
            }
        });
    }
}

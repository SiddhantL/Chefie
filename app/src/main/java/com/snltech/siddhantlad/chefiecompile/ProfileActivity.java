package com.snltech.siddhantlad.chefiecompile;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.KeyListener;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {
    TextView follower,follows;
    EditText nameTV,emailTV,messageTV;
    DatabaseReference usersRecipeDatabase,mDatabase;
    FirebaseAuth mAuth;
    Button profilenext,Follow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        final Intent intent = getIntent();
        mDatabase = FirebaseDatabase.getInstance().getReference("users");
        final String uuid = intent.getStringExtra("uuid");
        final String name = intent.getStringExtra("nameProfile");
        final String email = intent.getStringExtra("emailProfile");
        nameTV=(EditText) findViewById(R.id.textView8);
        emailTV=(EditText) findViewById(R.id.textView9);
        messageTV=(EditText) findViewById(R.id.textView10);
        follower=(TextView)findViewById(R.id.followers);
        follows=(TextView)findViewById(R.id.following);
        /*nameTV.setKeyListener(null);
        messageTV.setKeyListener(null);
        emailTV.setKeyListener(null);*/
        nameTV.setKeyListener(null);
        nameTV.setTag(nameTV.getKeyListener());
        nameTV.setKeyListener((KeyListener) nameTV.getTag());
        messageTV.setKeyListener(null);
        messageTV.setTag(messageTV.getKeyListener());
        messageTV.setKeyListener((KeyListener) messageTV.getTag());
        emailTV.setKeyListener(null);
        emailTV.setTag(emailTV.getKeyListener());
        emailTV.setKeyListener((KeyListener) emailTV.getTag());
        mAuth=FirebaseAuth.getInstance();
        mDatabase.child(uuid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserDetails ud=dataSnapshot.getValue(UserDetails.class);
                messageTV.setText(ud.getMessage().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        mDatabase.child(uuid+"/Followers").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                follower.setText(Long.toString(dataSnapshot.getChildrenCount()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        mDatabase.child(uuid+"/Following").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                follows.setText(Long.toString(dataSnapshot.getChildrenCount()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        follower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent follower=new Intent(ProfileActivity.this,FollowerUsersProfile.class);
                follower.putExtra("uuid",uuid);
                follower.putExtra("name",name);
                follower.putExtra("email",email);
                startActivity(follower);
            }
        });
        follows.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent follows=new Intent(ProfileActivity.this,FollowingUserProfile.class);
                follows.putExtra("uuid",uuid);
                follows.putExtra("name",name);
                follows.putExtra("email",email);
                startActivity(follows);
            }
        });
        profilenext=(Button)findViewById(R.id.myRecipes);
        Follow=(Button)findViewById(R.id.followBTN);
        Follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabase.child(uuid).child("Followers").child(mAuth.getCurrentUser().getUid()).setValue(mAuth.getCurrentUser().getUid());
                mDatabase.child(mAuth.getCurrentUser().getUid()).child("Following").child(uuid).setValue(uuid);
            }
        });
 // Toast.makeText(this, uuid, Toast.LENGTH_SHORT).show();
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

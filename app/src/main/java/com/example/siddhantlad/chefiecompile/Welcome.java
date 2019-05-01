package com.example.siddhantlad.chefiecompile;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.siddhantlad.chefiecompile.DatabaseSource.Artist;
import com.example.siddhantlad.chefiecompile.DatabaseSource.ArtistList;
import com.example.siddhantlad.chefiecompile.DatabaseSource.MainActivity;
import com.example.siddhantlad.chefiecompile.DatabaseSource.MainActivity2;
import com.example.siddhantlad.chefiecompile.DatabaseSource.UserInfo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import jp.wasabeef.blurry.Blurry;

public class Welcome extends AppCompatActivity {
Button logOutBtn,breakfastActivity;
    //FireBase Authentication Field
    FirebaseAuth mAuth;
    Button addRecipeFab;
    FirebaseUser user;
    FirebaseAuth.AuthStateListener mAuthListener;
    DatabaseReference mDatabase;
    EditText username, mail,contact;
    com.getbase.floatingactionbutton.FloatingActionButton fabSearch,fabLog,fabAdd;
    //TextView display;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        fabSearch=(com.getbase.floatingactionbutton.FloatingActionButton)findViewById(R.id.fabAction1);
        fabAdd=(com.getbase.floatingactionbutton.FloatingActionButton)findViewById(R.id.fabAction2);
        fabLog=(com.getbase.floatingactionbutton.FloatingActionButton)findViewById(R.id.fabAction3);
        user = FirebaseAuth.getInstance().getCurrentUser();
        username=(EditText)findViewById(R.id.textView8);
        mail=(EditText)findViewById(R.id.textView9);
        contact=(EditText)findViewById(R.id.textView10);
        username.setKeyListener(null);
        mail.setKeyListener(null);
        contact.setKeyListener(null);
        mail.setText(user.getEmail().toString());
        if (user.getDisplayName()!=null) {
            username.setText(user.getDisplayName().toString());

        }else{

        }
        //nameDispl.setText(user.getEmail().toString());
        mDatabase = FirebaseDatabase.getInstance().getReference();
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addRecipeFabIntent=new Intent(Welcome.this,MainActivity2.class);
                startActivity(addRecipeFabIntent);

            }
        });
     //   display=(TextView)findViewById(R.id.textView);

    //OnClick Listener
        fabLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
mAuth.signOut();
finish();
startActivity(new Intent(Welcome.this,LoginActivity.class));
            }
        });

        fabSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent MainAct=new Intent(Welcome.this,MainActivity.class);
                startActivity(MainAct);
            }
        });

        //Assign Instances
        mAuth= FirebaseAuth.getInstance();
        mAuthListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user= firebaseAuth.getCurrentUser();
                if(user!=null){

                }else{

                }
            }
        };

    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot name : dataSnapshot.getChildren()) {

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

      //  display.setText(userEmailString);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(mAuthListener);
    }

}

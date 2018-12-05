package com.example.siddhantlad.chefiecompile;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.siddhantlad.chefiecompile.DatabaseSource.MainActivity;
import com.example.siddhantlad.chefiecompile.DatabaseSource.MainActivity2;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import jp.wasabeef.blurry.Blurry;

public class Welcome extends AppCompatActivity {
Button logOutBtn;
TextView breakfastActivity;
    //FireBase Authentication Field
    FirebaseAuth mAuth;
    FloatingActionButton addRecipeFab;
    FirebaseAuth.AuthStateListener mAuthListener;
    //TextView display;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        breakfastActivity=(TextView)findViewById(R.id.breakfastOpt);
        addRecipeFab=(FloatingActionButton)findViewById(R.id.addRecipeFab);
        addRecipeFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addRecipeFabIntent=new Intent(Welcome.this,MainActivity2.class);
                startActivity(addRecipeFabIntent);
               /* Blurry.with(Welcome.this)
                        .radius(10)
                        .sampling(8)
                        .color(Color.argb(66, 255, 255, 0))
                        .async()
                        .onto(rootView);*/

            }
        });
    logOutBtn=(Button)findViewById(R.id.logoutbtn);
     //   display=(TextView)findViewById(R.id.textView);

    //OnClick Listener
        logOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
mAuth.signOut();
finish();
startActivity(new Intent(Welcome.this,SignUpActivity.class));
            }
        });

        breakfastActivity.setOnClickListener(new View.OnClickListener() {
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
      //  display.setText(userEmailString);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(mAuthListener);
    }

}

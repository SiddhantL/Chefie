package com.snltech.siddhantlad.chefiecompile;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {
Button createUser;
TextView MoveToLogin;
EditText userEmailEdit,userPasswordEdit,userName;
    DatabaseReference mDatabase;
    FirebaseUser user;
//FireBase Authentication Field
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
    //Assign ID's
        createUser=(Button)findViewById(R.id.CreateBtn);
        MoveToLogin=(TextView)findViewById(R.id.textView2);
        userEmailEdit=(EditText)findViewById(R.id.EmailEdittext);
        userPasswordEdit=(EditText)findViewById(R.id.PasswordeditText);
        userName=(EditText)findViewById(R.id.NameEditText);
        mDatabase = FirebaseDatabase.getInstance().getReference("users");
                    createUser.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final String userNameS=userName.getText().toString().toString().trim();
                            String userEmailString=userEmailEdit.getText().toString().trim();
                            String userPasswordString=userPasswordEdit.getText().toString().trim();
                            if (!TextUtils.isEmpty(userEmailString) && (!TextUtils.isEmpty(userPasswordString)) && (!TextUtils.isEmpty(userNameS))){
                                mAuth.createUserWithEmailAndPassword(userEmailString,userPasswordString).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()){
                                      //      Toast.makeText(SignUpActivity.this, "Account Created", Toast.LENGTH_LONG).show();
                                            mDatabase.child(user.getUid()).child("Name").setValue(userNameS);
                                            mDatabase.child(user.getUid()).child("Email").setValue(user.getEmail());
                                            startActivity(new Intent(SignUpActivity.this,DisplayName.class));
                                        }else {
                                            Toast.makeText(SignUpActivity.this, "Account could not be created", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                        }
                    });
        //Assign Instances
        mAuth= FirebaseAuth.getInstance();
        mAuthListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user= firebaseAuth.getCurrentUser();
                if(user!=null){
                    startActivity(new Intent(SignUpActivity.this,DisplayName.class));
                }else{
                    createUser.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final String userNameS=userName.getText().toString().toString().trim();
                            String userEmailString=userEmailEdit.getText().toString().trim();
                            String userPasswordString=userPasswordEdit.getText().toString().trim();
                            if (!TextUtils.isEmpty(userEmailString) && (!TextUtils.isEmpty(userPasswordString)) && (!TextUtils.isEmpty(userNameS))){
                                mAuth.createUserWithEmailAndPassword(userEmailString,userPasswordString).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()){
                                        //    Toast.makeText(SignUpActivity.this, "Account Created", Toast.LENGTH_LONG).show();
                                            mDatabase.child(user.getUid()).child("Name").setValue(userNameS);
                                            mDatabase.child(user.getUid()).child("Email").setValue(user.getEmail());
                                            startActivity(new Intent(SignUpActivity.this,DisplayName.class));
                                        }else {
                                            Toast.makeText(SignUpActivity.this, "Account could not be created", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                        }
                    });
                }
            }
        };

        //OnClick Listeners



        MoveToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent=new Intent(SignUpActivity.this,LoginActivity.class);
                startActivity(loginIntent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(mAuthListener);

    }
}

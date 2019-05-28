package com.snltech.siddhantlad.chefiecompile;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
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
                            Toast.makeText(SignUpActivity.this, "Creating Account", Toast.LENGTH_SHORT).show();
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
                                            showDisplayNameDialog();
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
                    showDisplayNameDialog();
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
                                            showDisplayNameDialog();
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
                finish();
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
    private void showDisplayNameDialog(){
        try {
            final AlertDialog.Builder mBuilder = new AlertDialog.Builder(SignUpActivity.this);
            View mView = getLayoutInflater().inflate(R.layout.activity_display_name, null);
            mBuilder.setView(mView);
            final AlertDialog dialog = mBuilder.create();
            dialog.show();
            final EditText displayName = (EditText) mView.findViewById(R.id.editText2);
            final EditText displayMessage = (EditText) mView.findViewById(R.id.editText3);
            ImageView prof_image = (ImageView) mView.findViewById(R.id.imageView4);
            final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("users");
            Button confirm = (Button) mView.findViewById(R.id.button);
            user = FirebaseAuth.getInstance().getCurrentUser();
            displayName.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String userdtct = displayName.getText().toString();
                    if (userdtct.contains("&") || userdtct.contains("=") || userdtct.contains("-") || userdtct.contains("|") || userdtct.contains(";")
                            || userdtct.contains("%") || userdtct.contains("/") || userdtct.contains("(") || userdtct.contains(")") || userdtct.contains(":")
                            || userdtct.contains("{") || userdtct.contains("}") || userdtct.contains(" ") || userdtct.contains("!") || userdtct.contains(",") || userdtct.equals("")) {
                        Toast.makeText(SignUpActivity.this, "Username Can't Contain Spaces Or Special Characters", Toast.LENGTH_SHORT).show();
                    /*errorTV.setText("Username Can't Contain Spaces Or Special Characters");
                    errorTV.setVisibility(View.VISIBLE);*/
                    } else {/*
                    errorTV.setVisibility(View.INVISIBLE);*/
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String userdtct = displayName.getText().toString();
                    if (userdtct.contains("&") || userdtct.contains("=") || userdtct.contains("-") || userdtct.contains("|") || userdtct.contains(";")
                            || userdtct.contains("%") || userdtct.contains("/") || userdtct.contains("(") || userdtct.contains(")") || userdtct.contains(":")
                            || userdtct.contains("{") || userdtct.contains("}") || userdtct.contains(" ") || userdtct.contains("!") || userdtct.contains(",") || userdtct.equals("")) {
                        Toast.makeText(SignUpActivity.this, "Username Can't Contain Spaces Or Special Characters", Toast.LENGTH_SHORT).show();
                    /*errorTV.setText("Username Can't Contain Spaces Or Special Characters");
                    errorTV.setVisibility(View.VISIBLE);*/
                    } else {/*
                    errorTV.setVisibility(View.INVISIBLE);*/
                        if (!TextUtils.isEmpty(displayName.getText()) || !displayName.getText().toString().equals("") || !TextUtils.isEmpty(displayMessage.getText()) || !displayMessage.getText().toString().equals("")) {
                            mDatabase.child(user.getUid()).child("Username").setValue(displayName.getText().toString());
                            mDatabase.child(user.getUid()).child("Message").setValue(displayMessage.getText().toString());
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setDisplayName(displayName.getText().toString()).setPhotoUri(Uri.parse("https://")).build();
                            profileUpdates = new UserProfileChangeRequest.Builder().setDisplayName(displayName.getText().toString()).setPhotoUri(Uri.parse("https://")).build();
                            user.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(SignUpActivity.this, "Profile Created", Toast.LENGTH_SHORT).show();
                                    dialog.cancel();
                                    startActivity(new Intent(SignUpActivity.this, Welcome.class));
                                    finish();
                                }
                            });
                        }
                    }

                }
            });
        }catch (Exception E){
            Toast.makeText(this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
        }
    }
}

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
import com.snltech.siddhantlad.chefiecompile.DatabaseSource.AddRecipeActivity;

public class LoginActivity extends AppCompatActivity {
    //FireBase Authentication Field
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;
TextView SignUpText,ForgotPass;
    Button loginbtn;
    EditText userEmailEdit,UserPasswordEdit;
    FirebaseUser user;
    //String Fields
    public static String userEmailString;
    String userPasswordString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//Assign ID's
        SignUpText=(TextView)findViewById(R.id.textView2);
loginbtn=(Button)findViewById(R.id.loginbtn);
ForgotPass=(TextView)findViewById(R.id.fgtpass);
ForgotPass.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        if (!TextUtils.isEmpty(userEmailEdit.getText().toString())) {
            FirebaseAuth.getInstance().sendPasswordResetEmail(userEmailEdit.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(LoginActivity.this, "Email to Reset the Password Sent", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(LoginActivity.this, "Email is not Valid", Toast.LENGTH_SHORT).show();
                            }
                        }

                    });
        }else{
            Toast.makeText(LoginActivity.this, "Enter an E-mail to Reset Password", Toast.LENGTH_SHORT).show();
        }
    }
});
userEmailEdit=(EditText)findViewById(R.id.EmailEdittext);
        UserPasswordEdit=(EditText)findViewById(R.id.PasswordeditText);
SignUpText.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent SignUpIntent=new Intent(LoginActivity.this,SignUpActivity.class);
        startActivity(SignUpIntent);
        finish();
    }
});
        //Assign Instances
        mAuth= FirebaseAuth.getInstance();
        mAuthListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user= firebaseAuth.getCurrentUser();
                if(user!=null){
                        startActivity(new Intent(LoginActivity.this,Welcome.class));
                        finish();
                }else{

                }
            }
        };
//OnCreateListener
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Perform Login Operation
               userEmailString=userEmailEdit.getText().toString().trim();
                userPasswordString=UserPasswordEdit.getText().toString().trim();
                if (!TextUtils.isEmpty(userEmailString) && !TextUtils.isEmpty(userPasswordString)){
                    mAuth.signInWithEmailAndPassword(userEmailString,userPasswordString).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                user=mAuth.getCurrentUser();
                                if (user.getDisplayName()==null){
                                   /* final AlertDialog.Builder mBuilder = new AlertDialog.Builder(LoginActivity.this);
                                    View mView = getLayoutInflater().inflate(R.layout.activity_display_name, null);
                                    mBuilder.setView(mView);
                                    final AlertDialog dialog = mBuilder.create();
                                    dialog.show();*/
                                    showDisplayNameDialog();
                                }else {
                                    startActivity(new Intent(LoginActivity.this, Welcome.class));
                                }
                                }else {
                                Toast.makeText(LoginActivity.this, "User Login Failed", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
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
            final AlertDialog.Builder mBuilder = new AlertDialog.Builder(LoginActivity.this);
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
                        Toast.makeText(LoginActivity.this, "Username Can't Contain Spaces Or Special Characters", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(LoginActivity.this, "Username Can't Contain Spaces Or Special Characters", Toast.LENGTH_SHORT).show();
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
                                    Toast.makeText(LoginActivity.this, "Profile Created", Toast.LENGTH_SHORT).show();
                                    dialog.cancel();
                                    startActivity(new Intent(LoginActivity.this, Welcome.class));
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


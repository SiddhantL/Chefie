package com.example.siddhantlad.chefiecompile;

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

public class LoginActivity extends AppCompatActivity {
    //FireBase Authentication Field
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;
TextView SignUpText;
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
                                    startActivity(new Intent(LoginActivity.this, DisplayName.class));
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
}

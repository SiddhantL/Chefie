package com.example.siddhantlad.chefiecompile;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DisplayName extends AppCompatActivity {
    FirebaseUser user;
    //FireBase Authentication Field
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;
    ImageView prof_image;
    EditText displayName,displayMessage;
    Button confirm;
    UserProfileChangeRequest profileUpdates;
    DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_name);
        displayName=(EditText)findViewById(R.id.editText2);
        displayMessage=(EditText)findViewById(R.id.editText3);
        prof_image=(ImageView)findViewById(R.id.imageView4);
        mDatabase = FirebaseDatabase.getInstance().getReference("users");
        confirm=(Button)findViewById(R.id.button);
       final TextView errorTV=(TextView)findViewById(R.id.errorTV);
        profileUpdates=new UserProfileChangeRequest.Builder().setDisplayName(displayName.getText().toString()).setPhotoUri(Uri.parse("https://")).build();
        user=FirebaseAuth.getInstance().getCurrentUser();
        displayName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
               String userdtct=displayName.getText().toString();
                if (userdtct.contains("&") || userdtct.contains("=") || userdtct.contains("-") || userdtct.contains("|") || userdtct.contains(";")
                        || userdtct.contains("%") || userdtct.contains("/") || userdtct.contains("(") || userdtct.contains(")") || userdtct.contains(":")
                        || userdtct.contains("{") || userdtct.contains("}") || userdtct.contains(" ") || userdtct.contains("!") || userdtct.contains(",") || userdtct.equals(""))
                {
                    errorTV.setText("Username can only contain letters, numbers, and underscore");
                    errorTV.setVisibility(View.VISIBLE);
                }else {
                    errorTV.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (errorTV.getVisibility() == View.INVISIBLE) {
                    if (!TextUtils.isEmpty(displayName.getText()) || !displayName.getText().toString().equals("") || !TextUtils.isEmpty(displayMessage.getText()) || !displayMessage.getText().toString().equals("")) {
                        mDatabase.child(user.getUid()).child("Username").setValue(displayName.getText().toString());
                        mDatabase.child(user.getUid()).child("Message").setValue(displayMessage.getText().toString());
                        profileUpdates=new UserProfileChangeRequest.Builder().setDisplayName(displayName.getText().toString()).setPhotoUri(Uri.parse("https://")).build();
                        user.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(DisplayName.this, "Profile Created", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(DisplayName.this,Welcome.class));
                            }
                        });
                    }
                }else{}
            }
        });
    }
}

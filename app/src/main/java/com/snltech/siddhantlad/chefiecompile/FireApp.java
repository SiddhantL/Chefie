package com.snltech.siddhantlad.chefiecompile;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;

public class FireApp extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
if (FirebaseApp.getApps(this).isEmpty()){
FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        }
    }
}

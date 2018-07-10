package com.example.siddhantlad.chefiecompile.DatabaseSource;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.siddhantlad.chefiecompile.R;

public class SelectedItems extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_items);
        Bundle bundle = getIntent().getExtras();
        String Checks = bundle.getString("Checks");
        Toast.makeText(this, Checks, Toast.LENGTH_LONG).show();
    }
}

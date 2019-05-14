package com.example.siddhantlad.chefiecompile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class AboutPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_page);
        ListView list=(ListView)findViewById(R.id.layoutPage);
        ArrayList items=new ArrayList();
        items.add("About the CEO");
        items.add("About Chefie");
        items.add("About SNL Tech.");
        items.add("Version 1.1");
        ArrayAdapter adapter=new ArrayAdapter(this,R.layout.simple_list_item_1,items);
        list.setAdapter(adapter);
    }
}

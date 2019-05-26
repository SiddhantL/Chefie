package com.snltech.siddhantlad.chefiecompile;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FollowingUserProfile extends AppCompatActivity {
    DatabaseReference mDatabase;
    ArrayList<String> followers,emails,uuids;
    ListView listUsers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_following_user_profile);
        final Intent intent = getIntent();
        final String uuid = intent.getStringExtra("uuid");
        final String name = intent.getStringExtra("nameProfile");
        final String email = intent.getStringExtra("emailProfile");
        followers=new ArrayList<>();
        emails=new ArrayList<>();
        uuids=new ArrayList<>();
        mDatabase= FirebaseDatabase.getInstance().getReference("users");
        listUsers=(ListView)findViewById(R.id.listViewFollow);
        mDatabase.child(uuid+"/Following").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot postsnap:dataSnapshot.getChildren()){
                    String Value=String.valueOf(postsnap.getValue());
                    uuids.add(Value);
                    mDatabase.child(Value).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot2) {
                            Author author=dataSnapshot2.getValue(Author.class);
                            Toast.makeText(FollowingUserProfile.this, author.getUsername(), Toast.LENGTH_SHORT).show();
                            followers.add(author.getUsername());
                            emails.add(author.getEmail());
                            ArrayAdapter<String>  adapter=new ArrayAdapter<String>(FollowingUserProfile.this,R.layout.simple_list_item_white,followers);
                            adapter.notifyDataSetChanged();
                            listUsers.setAdapter(adapter);
                        }


                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        listUsers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent profile=new Intent(FollowingUserProfile.this,ProfileActivity.class);
                profile.putExtra("uuid",uuids.get(position));
                profile.putExtra("nameProfile",followers.get(position));
                profile.putExtra("emailProfile",emails.get(position));
                startActivity(profile);
            }
        });
    }
}

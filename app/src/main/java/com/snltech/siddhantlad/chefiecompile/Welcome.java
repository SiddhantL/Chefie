package com.snltech.siddhantlad.chefiecompile;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.snltech.siddhantlad.chefiecompile.DatabaseSource.MainActivity;
import com.snltech.siddhantlad.chefiecompile.DatabaseSource.MainActivity2;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Welcome extends AppCompatActivity {
Button logOutBtn,breakfastActivity;
    //FireBase Authentication Field
    TextView follower,follows,expandFab;
    FirebaseAuth mAuth;
    Button myRecipes;
    FirebaseUser user;
    RatingBar rateBar;
    int TotalDivide;
    String subName;
    Double total;
    FirebaseAuth.AuthStateListener mAuthListener;
    DatabaseReference mDatabase,mRecipeByName,rateDatabase;
    EditText username, mail,contact;
    com.getbase.floatingactionbutton.FloatingActionButton fabSearch,fabLog,fabAdd,fabAbout;
    com.getbase.floatingactionbutton.FloatingActionsMenu menuFloat;
    //TextView display;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        fabSearch=(com.getbase.floatingactionbutton.FloatingActionButton)findViewById(R.id.fabAction1);
        fabAbout=(com.getbase.floatingactionbutton.FloatingActionButton)findViewById(R.id.fabAction4);
        fabAdd=(com.getbase.floatingactionbutton.FloatingActionButton)findViewById(R.id.fabAction2);
        fabLog=(com.getbase.floatingactionbutton.FloatingActionButton)findViewById(R.id.fabAction3);
        menuFloat=(com.getbase.floatingactionbutton.FloatingActionsMenu)findViewById(R.id.menu);
        expandFab=(TextView)findViewById(R.id.textView22);
        expandFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (menuFloat.isExpanded()) {
                    menuFloat.collapse();
                    expandFab.setVisibility(View.GONE);
                } else {
                    menuFloat.expand();
                    expandFab.setVisibility(View.GONE);
                }
            }
        });
        user = FirebaseAuth.getInstance().getCurrentUser();
        username=(EditText)findViewById(R.id.textView8);
        mAuth=FirebaseAuth.getInstance();
        myRecipes=(Button)findViewById(R.id.myRecipes);
        rateBar=(RatingBar)findViewById(R.id.ratingBar);
        mDatabase = FirebaseDatabase.getInstance().getReference("users");
        mail=(EditText)findViewById(R.id.textView9);
        contact=(EditText)findViewById(R.id.textView10);
        follower=(TextView)findViewById(R.id.followers);
        follows=(TextView)findViewById(R.id.following);
        TextView tvFollower=(TextView)findViewById(R.id.textView13);
        TextView tvFollowing=(TextView)findViewById(R.id.textView14);

        username.setKeyListener(null);
        mail.setKeyListener(null);
        contact.setKeyListener(null);
        total=0.0;
        TotalDivide=0;

        rateBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Toast.makeText(Welcome.this, "Add Recipe's to Increase Rating", Toast.LENGTH_LONG).show();
                return false;
            }
        });
        mRecipeByName = FirebaseDatabase.getInstance().getReference("RecipeByName/"+user.getUid());
        mRecipeByName.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
           for (DataSnapshot post:dataSnapshot.getChildren()){
               String recipeList=post.getKey().toString();
               String recipeName=recipeList.substring(1);
               //Should run 3 times for 3 recipes
               rateDatabase = FirebaseDatabase.getInstance().getReference("rate/"+recipeName);
               rateDatabase.addValueEventListener(new ValueEventListener() {
                   @Override
                   public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                  for (DataSnapshot post2:dataSnapshot.getChildren()){
                      Rate rate=post2.getValue(Rate.class);
                      total=rate.getRating()+total;
                      TotalDivide=TotalDivide+1;
                      Double mean=total/TotalDivide;
                    //  Toast.makeText(Welcome.this, Double.toString(mean), Toast.LENGTH_SHORT).show();
                      rateBar.setRating(Float.parseFloat(Double.toString(mean)));
                  }
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
        /*mRecipeByName.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                for (final DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    String name = postSnapshot.getKey().toString().trim();
                    subName = name.substring(1);
                    Summation=0.0;
                    Toast.makeText(Welcome.this, name, Toast.LENGTH_SHORT).show();
                    rateDatabase = FirebaseDatabase.getInstance().getReference("rate/" + subName);
                    rateDatabase.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                            for (final DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                final String uuidRated = postSnapshot.getKey().toString().trim();
                                NoValue=0.0;
                               // Toast.makeText(Welcome.this, rates, Toast.LENGTH_SHORT).show();
                                rateDatabase.child(uuidRated).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot2) {
                                        for (final DataSnapshot postSnapshot2 : dataSnapshot2.getChildren()) {
                                            String RateValue=String.valueOf(postSnapshot2.getValue());
                                            Toast.makeText(Welcome.this, uuidRated+"="+RateValue+subName, Toast.LENGTH_SHORT).show();
                                            Summation=Double.parseDouble(RateValue)+Summation;
                                          //  Toast.makeText(Welcome.this, Value, Toast.LENGTH_SHORT).show();
                                            NoValue=Double.parseDouble(Long.toString(dataSnapshot.getChildrenCount()))+NoValue;
                                            Average=Double.parseDouble(Summation.toString())/Double.parseDouble(NoValue.toString());
                                          //  Toast.makeText(Welcome.this, subName+" "+Double.toString(Summation)+" "+rates, Toast.LENGTH_SHORT).show();
                                            // Toast.makeText(Welcome.this, Double.toString(Average)+"="+Double.toString(Summation)+"/"+Double.toString(NoValue), Toast.LENGTH_SHORT).show();
                                             if (Average==0) {


                                            }else if (Average>0 && Average<=0.5) {
                                                rate.setRating(Float.parseFloat("0.5"));

                                            }else if (Average>0.5 && Average<=1) {
                                                rate.setRating(Float.parseFloat("1.0"));

                                            }else if (Average>1 && Average<=1.5) {
                                                rate.setRating(Float.parseFloat("1.5"));

                                            }else if (Average>1.5 && Average<=2) {
                                                rate.setRating(Float.parseFloat("2.0"));

                                            }else if (Average>2 && Average<=2.5) {
                                                rate.setRating(Float.parseFloat("2.5"));

                                            }else if (Average>2.5 && Average<=3) {
                                                rate.setRating(Float.parseFloat("3.0"));

                                            }else if (Average>3 && Average<=3.5) {
                                                rate.setRating(Float.parseFloat("3.5"));

                                            }else if (Average>3.5 && Average<=4) {
                                                rate.setRating(Float.parseFloat("4.0"));

                                            }else if (Average>4 && Average<=4.5) {
                                                rate.setRating(Float.parseFloat("4.5"));

                                            }else if (Average>4.5 && Average<=5) {
                                                rate.setRating(Float.parseFloat("5.0"));

                                            }
                                        }
                                        // Toast.makeText(Welcome.this, "Summation= "+Double.toString(Summation), Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                                 }
                            Toast.makeText(Welcome.this, "Summation= "+Double.toString(Summation), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            } @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/

        myRecipes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent recipeProfile=new Intent(Welcome.this,RecipeListProfile.class);
                recipeProfile.putExtra("uuid",mAuth.getCurrentUser().getUid().toString());
                startActivity(recipeProfile);
            }
        });
        mDatabase.child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserDetails ud = dataSnapshot.getValue(UserDetails.class);
                if (ud.getMessage() != null) {
                    contact.setText(ud.getMessage().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        mDatabase.child(mAuth.getCurrentUser().getUid()+"/Followers").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                follower.setText(Long.toString(dataSnapshot.getChildrenCount()));
                follower.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        mDatabase.child(mAuth.getCurrentUser().getUid()+"/Following").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                follows.setText(Long.toString(dataSnapshot.getChildrenCount()));
                follows.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        follower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent follower=new Intent(Welcome.this,FollowerUsersProfile.class);
                follower.putExtra("uuid",mAuth.getCurrentUser().getUid());
                follower.putExtra("name",mAuth.getCurrentUser().getDisplayName());
                follower.putExtra("email",mAuth.getCurrentUser().getEmail());
                startActivity(follower);
            }
        });
        follows.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent follows=new Intent(Welcome.this,FollowingUserProfile.class);
                follows.putExtra("uuid",mAuth.getCurrentUser().getUid());
                follows.putExtra("name",mAuth.getCurrentUser().getDisplayName());
                follows.putExtra("email",mAuth.getCurrentUser().getEmail());
                startActivity(follows);
            }
        });
        tvFollower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent follower=new Intent(Welcome.this,FollowerUsersProfile.class);
                follower.putExtra("uuid",mAuth.getCurrentUser().getUid());
                follower.putExtra("name",mAuth.getCurrentUser().getDisplayName());
                follower.putExtra("email",mAuth.getCurrentUser().getEmail());
                startActivity(follower);
            }
        });
        tvFollowing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent follows=new Intent(Welcome.this,FollowingUserProfile.class);
                follows.putExtra("uuid",mAuth.getCurrentUser().getUid());
                follows.putExtra("name",mAuth.getCurrentUser().getDisplayName());
                follows.putExtra("email",mAuth.getCurrentUser().getEmail());
                startActivity(follows);
            }
        });
        mail.setText(user.getEmail().toString());

        //contact.setText(user.getPhoneNumber());
        if (user.getDisplayName()!=null) {
            username.setText(user.getDisplayName().toString());

        }else{
            Toast.makeText(this, "No Username", Toast.LENGTH_SHORT).show();
        }
        //nameDispl.setText(user.getEmail().toString());
        mDatabase = FirebaseDatabase.getInstance().getReference();
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addRecipeFabIntent=new Intent(Welcome.this,MainActivity2.class);
                startActivity(addRecipeFabIntent);

            }
        });
     //   display=(TextView)findViewById(R.id.textView);

    //OnClick Listener
        fabAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent MainAbout=new Intent(Welcome.this,AboutPage.class);
                startActivity(MainAbout);
            }
        });

        fabLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
mAuth.signOut();
finish();
startActivity(new Intent(Welcome.this,LoginActivity.class));
            }
        });

        fabSearch.setOnClickListener(new View.OnClickListener() {
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
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot name : dataSnapshot.getChildren()) {

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

      //  display.setText(userEmailString);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(mAuthListener);
    }

}

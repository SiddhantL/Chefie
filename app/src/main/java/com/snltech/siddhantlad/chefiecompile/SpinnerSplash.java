package com.snltech.siddhantlad.chefiecompile;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SpinnerSplash extends AppCompatActivity {
    private ImageView img;
    Animation aniRotateClk;
    ProgressBar pBar;
    DatabaseReference mDatabase;
    FirebaseAuth mAuth;
    String Load;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spinner_splash);
        mDatabase = FirebaseDatabase.getInstance().getReference("users");
        mAuth = FirebaseAuth.getInstance();
        img = (ImageView) findViewById(R.id.imgvw);
        if (mAuth.getCurrentUser() != null) {
            mDatabase.child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    UserDetails ud = dataSnapshot.getValue(UserDetails.class);
                    if (ud.getMessage() != null) {
                        Load = ud.getMessage().toString();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        /*aniRotateClk = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_clockwise);
                img.startAnimation(aniRotateClk);
                final ValueAnimator animator = ValueAnimator.ofInt(0, pBar.getMax());
                animator.setDuration(1600);
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation){
                        pBar.setProgress((Integer)animation.getAnimatedValue());
                    }
                });
                animator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        // start your activity here

                        startActivity(new Intent(SpinnerSplash.this, LoginActivity.class));
                        finish();
                    }
                });
                animator.start();
*/

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                final Intent mainIntent = new Intent(SpinnerSplash.this, LoginActivity.class);
                SpinnerSplash.this.startActivity(mainIntent);
                SpinnerSplash.this.finish();
            }
        }, 2500);

    }
}
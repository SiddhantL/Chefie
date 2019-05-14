package com.example.siddhantlad.chefiecompile.DatabaseSource;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.siddhantlad.chefiecompile.Author;
import com.example.siddhantlad.chefiecompile.DisplayRecipeInfo;
import com.example.siddhantlad.chefiecompile.R;
import com.example.siddhantlad.chefiecompile.Rate;
import com.example.siddhantlad.chefiecompile.Welcome;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;


public class RecipeArtistList extends ArrayAdapter<RecipeArtist> {
    private Activity context;
    List<RecipeArtist> artists;
    DatabaseReference rateDatabase,creditDatabase;
    Double Summation,Average,NoValue,total;
    RatingBar ratingBar;
    int TotalDivide;
    TextView credits,usersX;
    public RecipeArtistList(Activity context, List<RecipeArtist> artists) {
        super(context, R.layout.layout_artist_list, artists);
        this.context = context;
        this.artists = artists;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_language_list_item, null, true);
        ratingBar=(RatingBar)listViewItem.findViewById(R.id.ratingBar2);
        usersX=(TextView)listViewItem.findViewById(R.id.textView27);
        TextView textViewName = (TextView) listViewItem.findViewById(R.id.tv_language);
      //  TextView textViewGenre = (TextView) listViewItem.findViewById(R.id.textViewGenre);
        ImageView cardImage =(ImageView)listViewItem.findViewById(R.id.im_language);
        RecipeArtist artist = artists.get(position);
        textViewName.setText(artist.getArtistName());
        rateDatabase = FirebaseDatabase.getInstance().getReference("rate/"+artist.getArtistName());
        creditDatabase = FirebaseDatabase.getInstance().getReference("credits/"+artist.getArtistName());
        credits=(TextView)listViewItem.findViewById(R.id.authorTv);
        total=0.0;
        TotalDivide=0;
        creditDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Author author = dataSnapshot.getValue(Author.class);
                credits.setText(author.getUsername().toString());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        rateDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot post2:dataSnapshot.getChildren()){
                    Rate rate=post2.getValue(Rate.class);
                    total=rate.getRating()+total;
                    TotalDivide=TotalDivide+1;
                    Double mean=total/TotalDivide;
                    //Toast.makeText(context, Double.toString(mean), Toast.LENGTH_SHORT).show();
                    ratingBar.setRating(Float.parseFloat(Double.toString(mean)));
                    ratingBar.setVisibility(View.VISIBLE);
                    usersX.setText("Rated By "+Integer.toString(TotalDivide)+" Users");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("images/" + artist.getArtistName().toString()+"/"+artist.getArtistName().toString() + ".jpg");
        Glide.with(this.context)
                .load(storageReference).apply(new RequestOptions().placeholder(R.drawable.lunchpic)).into(cardImage);

        //textViewGenre.setText(artist.getArtistGenre());

        return listViewItem;
    }
}
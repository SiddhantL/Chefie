package com.snltech.siddhantlad.chefiecompile;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;


public class RecipeProfileList extends ArrayAdapter<String> {
    private Activity context;
    List<String> artists;
    DatabaseReference rateDatabase,creditDatabase;
    int TotalDivide;
    Double Summation,Average,NoValue,total;
    RatingBar ratingBar;
    TextView credits,usersX;
    public RecipeProfileList(Activity context, List<String> artists) {
        super(context, R.layout.layout_artist_list, artists);
        this.context = context;
        this.artists = artists;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_language_list_item, null, true);
        rateDatabase = FirebaseDatabase.getInstance().getReference("rate");
        creditDatabase = FirebaseDatabase.getInstance().getReference("credits");
        TextView textViewName = (TextView) listViewItem.findViewById(R.id.tv_language);
        usersX=(TextView)listViewItem.findViewById(R.id.textView27);
        //  TextView textViewGenre = (TextView) listViewItem.findViewById(R.id.textViewGenre);
        ImageView cardImage =(ImageView)listViewItem.findViewById(R.id.im_language);
        String artist = artists.get(position);
        textViewName.setText(artist.toString());
        total=0.0;
        TotalDivide=0;
        ratingBar=(RatingBar)listViewItem.findViewById(R.id.ratingBar2);
        credits=(TextView)listViewItem.findViewById(R.id.authorTv);
        String imageFilter=artist;
        creditDatabase.child(artist.toString()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Author author = dataSnapshot.getValue(Author.class);
                credits.setText(author.getUsername().toString());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        rateDatabase.child(artist.toString()).addValueEventListener(new ValueEventListener() {
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
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("images/" + imageFilter.toString()+"/"+imageFilter.toString() + ".jpg");
        Glide.with(this.context)
                .load(storageReference).apply(new RequestOptions().placeholder(R.drawable.lunchpic)).into(cardImage);

        //textViewGenre.setText(artist.getArtistGenre());

        return listViewItem;
    }
}
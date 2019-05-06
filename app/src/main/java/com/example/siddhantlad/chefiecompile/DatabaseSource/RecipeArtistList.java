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
    Double Summation,Average,NoValue;
    RatingBar ratingBar;
    TextView credits;
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
        TextView textViewName = (TextView) listViewItem.findViewById(R.id.tv_language);
      //  TextView textViewGenre = (TextView) listViewItem.findViewById(R.id.textViewGenre);
        ImageView cardImage =(ImageView)listViewItem.findViewById(R.id.im_language);
        RecipeArtist artist = artists.get(position);
        textViewName.setText(artist.getArtistName());
        rateDatabase = FirebaseDatabase.getInstance().getReference("rate/"+artist.getArtistName());
        creditDatabase = FirebaseDatabase.getInstance().getReference("credits/"+artist.getArtistName());
        credits=(TextView)listViewItem.findViewById(R.id.authorTv);
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
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                for (final DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    String rates = postSnapshot.getKey().toString().trim();
                    Summation=0.0;
                    rateDatabase.child(rates).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot2) {
                            for(final DataSnapshot postSnapshot2 : dataSnapshot2.getChildren()){
                                String Value=String.valueOf(postSnapshot2.getValue());
                                //Toast.makeText(DisplayRecipeInfo.this, Value, Toast.LENGTH_SHORT).show();
                                Summation=Double.parseDouble(Value)+Summation;
                                NoValue=Double.parseDouble(Long.toString(dataSnapshot.getChildrenCount()));
                                Average=Double.parseDouble(Summation.toString())/Double.parseDouble(NoValue.toString());
                                Toast.makeText(context, Double.toString(Average), Toast.LENGTH_SHORT).show();
                                ratingBar.setRating(Float.parseFloat(Double.toString(Average)));
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
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("images/" + artist.getArtistName().toString() + ".jpg");
        Glide.with(this.context)
                .load(storageReference).apply(new RequestOptions().placeholder(R.drawable.lunchpic)).into(cardImage);

        //textViewGenre.setText(artist.getArtistGenre());

        return listViewItem;
    }
}
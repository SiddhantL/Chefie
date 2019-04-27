package com.example.siddhantlad.chefiecompile.DatabaseSource;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.siddhantlad.chefiecompile.R;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;


public class RecipeArtistList extends ArrayAdapter<RecipeArtist> {
    private Activity context;
    List<RecipeArtist> artists;

    public RecipeArtistList(Activity context, List<RecipeArtist> artists) {
        super(context, R.layout.layout_artist_list, artists);
        this.context = context;
        this.artists = artists;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_language_list_item, null, true);

        TextView textViewName = (TextView) listViewItem.findViewById(R.id.tv_language);
      //  TextView textViewGenre = (TextView) listViewItem.findViewById(R.id.textViewGenre);
        ImageView cardImage =(ImageView)listViewItem.findViewById(R.id.im_language);
        RecipeArtist artist = artists.get(position);
        textViewName.setText(artist.getArtistName());
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("images/" + artist.getArtistName().toString() + ".jpg");
        Glide.with(this.context)
                .load(storageReference).apply(new RequestOptions().placeholder(R.drawable.lunchpic)).into(cardImage);

        //textViewGenre.setText(artist.getArtistGenre());

        return listViewItem;
    }
}
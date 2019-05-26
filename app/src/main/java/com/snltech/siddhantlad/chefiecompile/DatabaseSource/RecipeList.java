package com.snltech.siddhantlad.chefiecompile.DatabaseSource;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.snltech.siddhantlad.chefiecompile.R;

import java.util.List;


public class RecipeList extends ArrayAdapter<Artist> {
    private Activity context;
    List<Strings> strings;

    public RecipeList(Activity context, List<Artist> artists) {
        super(context, R.layout.layout_artist_list, artists);
        this.context = context;
        this.strings = strings;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_artist_list, null, true);

        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewName);
        TextView textViewGenre = (TextView) listViewItem.findViewById(R.id.textViewGenre);

        Strings string = strings.get(position);
        textViewName.setText(string.getRecipeName());
        textViewGenre.setText(string.getRecipeType());

        return listViewItem;
    }
}
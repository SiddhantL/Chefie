package com.snltech.siddhantlad.chefiecompile.DatabaseSource;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.text.method.KeyListener;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.snltech.siddhantlad.chefiecompile.AddRecipeFab;
import com.snltech.siddhantlad.chefiecompile.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

            //we will use these constants later to pass the artist name and id to another activity
            //view objects
//    SearchView editTextName;
    SearchView search;
    Spinner spinnerGenre;
    Button buttonAddArtist, RecipeActivityButton;
    TextView artistname;
    String newText,theOne;
    RatingBar rBar;
    EditText selectedET;
    ListView listViewArtists;
    View views;
    ArrayAdapter<String> concencatedAdapter;
    String selectedString;
    public Map<String, Boolean> myMap;
    public static String Checks;
    //a list to store all the artist from firebase database
    List<Artist> artists;
    ArrayList<String> my_array_of_selected_ingredients,concencated_ingredients,backup;
    NetworkInfo activeNetworkInfo;
    ArrayList<String> match,savetheOne;
    ArrayAdapter<String> matchAdapter;
    //
    public static DatabaseReference databaseArtists;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //getting the reference of artists node
        databaseArtists = FirebaseDatabase.getInstance().getReference("artists");
        //getting views
        rBar=(RatingBar)findViewById(R.id.ratingBar);
      /*  rBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Add Your Own Recipe's To Change Your Rating", Toast.LENGTH_SHORT).show();
            }
        });*/
        my_array_of_selected_ingredients = new ArrayList<String>();
        selectedET=(EditText)findViewById(R.id.selectedEditText);
        selectedET.setKeyListener(null);
        selectedET.setTag(selectedET.getKeyListener());
        selectedET.setKeyListener((KeyListener) selectedET.getTag());
        Context context = this;
        selectedString=new String();
        final ArrayAdapter<String> adapter_Selected = new ArrayAdapter<String>(context,R.layout.layout_artist_list,my_array_of_selected_ingredients);
        myMap = new HashMap();
        search = (SearchView) findViewById(R.id.editTextName);
        spinnerGenre = (Spinner) findViewById(R.id.spinnerGenres);
        artistname=(TextView)findViewById(R.id.textView1);
        listViewArtists = (ListView) findViewById(R.id.listViewArtists);
        concencated_ingredients=new ArrayList<String>();
        backup=new ArrayList<String>();
        RecipeActivityButton = (Button) findViewById(R.id.recipeButton);
        buttonAddArtist = (Button) findViewById(R.id.buttonAddArtist);
        //list to store artists
        artists = new ArrayList<>();
        match=new ArrayList<String>();
        savetheOne=new ArrayList<String>();
        theOne=new String();
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (!TextUtils.isEmpty(search.getQuery().toString()) || !search.getQuery().toString().equals("")) {
                    concencatedAdapter.getFilter().filter(newText);
                    /* for (int i = 0; i < concencated_ingredients.size(); i++) {
                        if (!concencated_ingredients.get(i).toLowerCase().contains(search.getQuery().toString().toLowerCase()
                        )) {

                            concencated_ingredients.remove(i);
                            final ArrayAdapter<String> concencatedAdapter = new ArrayAdapter<String>(MainActivity.this, R.layout.simple_list_item_white, concencated_ingredients);
                            concencatedAdapter.notifyDataSetChanged();
                            listViewArtists.setAdapter(concencatedAdapter);
                            savetheOne=concencated_ingredients;
                        } else {
                           // Toast.makeText(MainActivity.this, "It's a match", Toast.LENGTH_SHORT).show();
                        }
                    }*/
                    concencatedAdapter.notifyDataSetChanged();
                    listViewArtists.setAdapter(concencatedAdapter);
                }else{ concencated_ingredients.clear();
                    concencated_ingredients.addAll(backup);
                    final ArrayAdapter<String> concencatedAdapter = new ArrayAdapter<String>(MainActivity.this, R.layout.simple_list_item_white, concencated_ingredients);
                    concencatedAdapter.notifyDataSetChanged();
                    listViewArtists.setAdapter(concencatedAdapter);
                }
                return false;
            }
        });

     //   IntentTransfer();
        RecipeActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isNetworkAvailable();
                if (activeNetworkInfo != null) {
                    RecipeActivityButton.setText("Check Recipes");
                    concencated_ingredients.clear();
                    concencated_ingredients.addAll(backup);
                    final ArrayAdapter<String> concencatedAdapter = new ArrayAdapter<String>(MainActivity.this, R.layout.simple_list_item_white, concencated_ingredients);
                    concencatedAdapter.notifyDataSetChanged();
                    listViewArtists.setAdapter(concencatedAdapter);
                    int TotalItems = listViewArtists.getAdapter().getCount();
                    //Checking all true booleans
                    for (int AtItem = 0; AtItem < TotalItems; AtItem++) {
                        Artist artist = artists.get(AtItem);
                        String CheckingName = artist.getArtistName().toString();
                        try {
                            if (myMap.get(CheckingName)) {
                                //Toast.makeText(MainActivity.this, CheckingName+" was added", Toast.LENGTH_SHORT).show();
                                my_array_of_selected_ingredients.add(CheckingName);
                            }
                        } catch (Exception e) {

                        }

                    }

                    IntentTransfer();
                }else {
                    RecipeActivityButton.setText("No Network");
                }
            }
        });


        listViewArtists.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
               Artist artist = artists.get(i);

                showUpdateDeleteDialog(artist.getArtistId(), artist.getArtistName());
                return true;
            }
        });
        listViewArtists.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                view.setSelected(true);
                views=view;
                //view.setBackgroundColor(Color.rgb(42,182,247));
                Artist artist = artists.get(i);
                Checks=concencatedAdapter.getItem(i).toString();
              //  myMap.put("Banana",true);
                    if (myMap.get(Checks)!=null){
                        listViewArtists.setItemChecked(i,false);
                    //    view.setBackgroundResource(R.drawable.gradient);
                        myMap.remove(Checks);
                        Toast.makeText(MainActivity.this, Checks+" Removed", Toast.LENGTH_SHORT).show();
                        selectedString=selectedString.replace(Checks+",","");
                        selectedET.setText(selectedString);
                    }else {
                        myMap.put(Checks, true);
                        Toast.makeText(MainActivity.this, Checks+" Added", Toast.LENGTH_SHORT).show();
                        listViewArtists.setItemChecked(i,true);
                        selectedString=selectedString+Checks+",";
                        selectedET.setText(selectedString);
                    }
                    //myMap.put("Orange",true);

                }
                });


        //adding an onclicklistener to button
        buttonAddArtist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              }
        });
}

    @Override
    protected void onStart() {
        super.onStart();
        databaseArtists.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                //clearing the previous artist list
                artists.clear();
                concencated_ingredients.clear();
                backup.clear();
                //iterating through all the nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //getting artist
                    Artist artist = postSnapshot.getValue(Artist.class);
                    //adding artist to the list
                    concencated_ingredients.add(artist.getArtistName().toString().trim());
                    backup.add(artist.getArtistName().toString().trim());
                    artists.add(artist);
                }

                //creating adapter
                final ArtistList artistAdapter = new ArtistList(MainActivity.this, artists);
                concencatedAdapter=new ArrayAdapter<String>(MainActivity.this, R.layout.simple_list_item_white,concencated_ingredients);
                artistAdapter.notifyDataSetChanged();
                concencatedAdapter.notifyDataSetChanged();
                //attaching adapter to the listview
                //Checking all true booleans
                for (int AtItems = 0; AtItems < artistAdapter.getCount(); AtItems++) {
                    Artist artist = artists.get(AtItems);
                    String CheckingName = artist.getArtistName().toString();
                }
                listViewArtists.setAdapter(concencatedAdapter);
                           }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
listViewArtists.setAdapter(concencatedAdapter);
    }


    private boolean updateArtist(String id, String name, String genre) {
        //getting the specified artist reference
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("artists").child(id);
        //updating artist
        Artist artist = new Artist(id, name, genre);
        dR.setValue(artist);
        Toast.makeText(getApplicationContext(), "Recipe Updated", Toast.LENGTH_LONG).show();
        return true;
    }

    private void showUpdateDeleteDialog(final String artistId, String artistName) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_dialog, null);
        dialogBuilder.setView(dialogView);
        final EditText editTextName = (EditText) dialogView.findViewById(R.id.editTextName);
        final Spinner spinnerGenre = (Spinner) dialogView.findViewById(R.id.spinnerGenres);
        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.buttonUpdateArtist);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.buttonDeleteArtist);

        dialogBuilder.setTitle(artistName);
        final AlertDialog b = dialogBuilder.create();
        b.show();

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editTextName.getText().toString().trim();
                String genre = spinnerGenre.getSelectedItem().toString();
                if (!TextUtils.isEmpty(name)) {
                    updateArtist(artistId, name, genre);
                    b.dismiss();
                }
            }
        });


        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteArtist(artistId);
                b.dismiss();
                /*
                 * we will code this method to delete the artist
                 * */

            }
        });

    }

    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
        TextView c = (TextView) v.findViewById(R.id.listViewArtists);
        String playerChanged = c.getText().toString();

        Toast.makeText(MainActivity.this, playerChanged, Toast.LENGTH_SHORT).show();
    }

    private boolean deleteArtist(String id) {
        //getting the specified artist reference
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("artists").child(id);

        //removing artist
        dR.removeValue();

        //getting the tracks reference for the specified artist
        DatabaseReference drTracks = FirebaseDatabase.getInstance().getReference("tracks").child(id);

        //removing all tracks
        drTracks.removeValue();
        Toast.makeText(getApplicationContext(), "Recipe Deleted", Toast.LENGTH_LONG).show();

        return true;
    }
public void IntentTransfer(){
    Intent recipeActivityIntent = new Intent(MainActivity.this, RecipeActivity.class);
    Intent addRecipeFab = new Intent(MainActivity.this, AddRecipeFab.class);
    Intent newRecipe=new Intent(MainActivity.this,AddRecipeActivity.class);
    Bundle bun = new Bundle();
    bun.putSerializable("myMap", (Serializable) myMap);
    recipeActivityIntent.putExtra("bundle",bun);
    recipeActivityIntent.putExtra("my_array_of_selected_ingredients",my_array_of_selected_ingredients);
    newRecipe.putExtra("my_array_of_selected_ingredients",my_array_of_selected_ingredients);
    addRecipeFab.putExtra("my_array_of_selected_ingredients",my_array_of_selected_ingredients);
    startActivity(newRecipe);
    my_array_of_selected_ingredients.clear();

   }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}

package com.example.siddhantlad.chefiecompile.DatabaseSource;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.text.method.KeyListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.siddhantlad.chefiecompile.AddRecipeFab;
import com.example.siddhantlad.chefiecompile.IngredientAdder;
import com.example.siddhantlad.chefiecompile.R;
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

public class MainActivity2 extends AppCompatActivity {

            //we will use these constants later to pass the artist name and id to another activity
            //view objects
    EditText editTextName,selectedET;
    Spinner spinnerGenre;
    SearchView search;
    Button buttonAddArtist, RecipeActivityButton,addSpontaneous;
    TextView artistname,addArtistText;
    ListView listViewArtists;
    String querySave;
    String selectedString;
    public Map<String, Boolean> myMap;
    ArrayAdapter<String> concencatedAdapter;
    ArrayList<String>concencated_ingredients,backup,selected;
    public static String Checks;
    //a list to store all the artist from firebase database
    List<Artist> artists;
    ArrayList<String> my_array_of_selected_ingredients;
    View row;

    //our database reference object
    public static DatabaseReference databaseArtists;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        //getting the reference of artists node
        databaseArtists = FirebaseDatabase.getInstance().getReference("artists");
        addSpontaneous=(Button)findViewById(R.id.button2);
        //getting views
        concencated_ingredients=new ArrayList<String>();
        my_array_of_selected_ingredients = new ArrayList<String>();
        selected=new ArrayList<String>();
        search=(SearchView)findViewById(R.id.searchQuery);
        querySave=new String();
        selectedET=(EditText)findViewById(R.id.textView17);
        Context context = this;
        backup=new ArrayList<String>();
        selectedET.setKeyListener(null);
        selectedET.setTag(selectedET.getKeyListener());
        selectedET.setKeyListener((KeyListener) selectedET.getTag());
        addArtistText=(TextView)findViewById(R.id.textView18);
        final ArrayAdapter<String> adapter_Selected = new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1,my_array_of_selected_ingredients);
        myMap = new HashMap();
        editTextName = (EditText) findViewById(R.id.editTextName);
        spinnerGenre = (Spinner) findViewById(R.id.spinnerGenres);
        artistname=(TextView)findViewById(R.id.textView1);
        listViewArtists = (ListView) findViewById(R.id.listViewArtists);
        selectedString=new String();
        RecipeActivityButton = (Button) findViewById(R.id.recipeButton);
        buttonAddArtist = (Button) findViewById(R.id.buttonAddArtist);
        //list to store artists
        artists = new ArrayList<>();
        addSpontaneous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addArtist();
            }
        });
        addArtistText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity2.this, IngredientAdder.class));
            }
        });
        RecipeActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int TotalItems=listViewArtists.getAdapter().getCount();
                                //Checking all true booleans
                for(int AtItem = 0; AtItem < TotalItems; AtItem++){
                    Artist artist = artists.get(AtItem);
                    String CheckingName=artist.getArtistName().toString();
                    try{
                        if (myMap.get(CheckingName)){
                           //Toast.makeText(MainActivity.this, CheckingName+" was added", Toast.LENGTH_SHORT).show();
                           my_array_of_selected_ingredients.add(CheckingName);
                        }
                    }catch (Exception e){

                    }

                }
IntentTransfer();
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
                /*//getting the selected artist
                //creating an intent
                Intent intent = new Intent(getApplicationContext(), ArtistActivity.class);

                //putting artist name and id to intent

                //starting the activity with intent
                startActivity(intent);
                */
                view.setSelected(true);
                row=view;
                Artist artist = artists.get(i);
                Checks=concencatedAdapter.getItem(i).toString();

               // row.setBackgroundColor(Color.rgb(42,182,247));
                if (myMap.get(Checks)!=null){
                    listViewArtists.setItemChecked(i,false);
                    //    view.setBackgroundResource(R.drawable.gradient);
                    myMap.remove(Checks);
                    Toast.makeText(MainActivity2.this, Checks+" Removed", Toast.LENGTH_SHORT).show();
                    selectedString=selectedString.replace(Checks+",","");
                    selectedET.setText(selectedString);
                    selected.remove(Checks);
                }else {
                    myMap.put(Checks, true);
                    Toast.makeText(MainActivity2.this, Checks+" Added", Toast.LENGTH_SHORT).show();
                    listViewArtists.setItemChecked(i,true);
                    selectedString=selectedString+Checks+",";
                    selected.add(Checks);
                    selectedET.setText(selectedString);
                }

                //myMap.put("Banana",true);
             //   myMap.put(Checks, true);
                //myMap.put("Orange",true);
               // Toast.makeText(MainActivity2.this, Checks, Toast.LENGTH_SHORT).show();
            }
        });

        //adding an onclicklistener to button


        Animation animation = AnimationUtils.loadAnimation(MainActivity2.this,  R.anim.push_up_in);
//       listViewArtists.startAnimation(animation);

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (concencatedAdapter.isEmpty()) {
                    addSpontaneous.setVisibility(View.VISIBLE);
                    addSpontaneous.setText("Add " + query + " To Ingredients List");
                    querySave=query;
                } else if (!concencatedAdapter.isEmpty()){
                    addSpontaneous.setVisibility(View.GONE);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (!TextUtils.isEmpty(search.getQuery().toString()) || !search.getQuery().toString().equals("")) {
                    if (concencatedAdapter.getCount()!=0){
                        addSpontaneous.setVisibility(View.GONE);
                    }
                   concencatedAdapter.getFilter().filter(newText);

                    /*for (int i = 0; i < concencated_ingredients.size(); i++) {
                        if (!concencated_ingredients.get(i).toLowerCase().contains(search.getQuery().toString().toLowerCase())) {
                            concencated_ingredients.remove(i);
                            final ArrayAdapter<String> concencatedAdapter = new ArrayAdapter<String>(MainActivity2.this, R.layout.simple_list_item_white, concencated_ingredients);
                            concencatedAdapter.notifyDataSetChanged();
                            listViewArtists.setAdapter(concencatedAdapter);
                        } else {
                            // Toast.makeText(MainActivity.this, "It's a match", Toast.LENGTH_SHORT).show();
                        }
                    }*/
                    concencatedAdapter.notifyDataSetChanged();
                listViewArtists.setAdapter(concencatedAdapter);

                }else{
                    concencated_ingredients.clear();
                    concencated_ingredients.addAll(backup);
                    final ArrayAdapter<String> concencatedAdapter = new ArrayAdapter<String>(MainActivity2.this, R.layout.simple_list_item_white, concencated_ingredients);
                    concencatedAdapter.notifyDataSetChanged();
                    listViewArtists.setAdapter(concencatedAdapter);
                }
                return false;
            }
        });
    }

    /*
     * This method is saving a new artist to the
     * Firebase Realtime Database
     * */


    @Override
    protected void onStart() {
        super.onStart();
        //attaching value event listener
      /*  Animation animation = AnimationUtils.loadAnimation(MainActivity2.this,  R.anim.push_up_in);
        listViewArtists.startAnimation(animation);*/
        databaseArtists.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //clearing the previous artist list
                artists.clear();
concencated_ingredients.clear();
backup.clear();
                //iterating through all the nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //getting artist
                    Artist artist = postSnapshot.getValue(Artist.class);
                    concencated_ingredients.add(artist.getArtistName().toString().trim());
                    backup.add(artist.getArtistName().toString().trim());
                    concencatedAdapter=new ArrayAdapter<String>(MainActivity2.this, R.layout.simple_list_item_white,concencated_ingredients);
                    concencatedAdapter.notifyDataSetChanged();
                    //adding artist to the list
                    artists.add(artist);
                }

                //creating adapter
                ArtistList artistAdapter = new ArtistList(MainActivity2.this, artists);
                //attaching adapter to the listview
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

        Toast.makeText(MainActivity2.this, playerChanged, Toast.LENGTH_SHORT).show();
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
        Intent addRecipeFab = new Intent(MainActivity2.this, AddRecipeFab.class);
        Intent newRecipe=new Intent(MainActivity2.this,AddRecipeActivity.class);
        Bundle bun = new Bundle();
        bun.putSerializable("myMap", (Serializable) myMap);
        addRecipeFab.putExtra("bundle",bun);
        addRecipeFab.putExtra("my_array_of_selected_ingredients",selected);
        //addRecipeFab.putExtra("my_array_of_selected_ingredients",my_array_of_selected_ingredients);
        startActivity(addRecipeFab);
        my_array_of_selected_ingredients.clear();

    }
    private void addArtist() {
        //getting the values to save
        //checking if the value is provided
        if (!TextUtils.isEmpty(search.getQuery())) {

            //getting a unique id using push().getKey() method
            //it will create a unique id and we will use it as the Primary Key for our Artist
            String id = "-"+querySave.toString().toLowerCase();

            //creating an Artist Object
            Artist artist = new Artist(id, querySave, "");

            //Saving the Artist
            databaseArtists.child(id).setValue(artist);

            //setting edittext to blank again
            //displaying a success toast
            Toast.makeText(this, "Ingredient added", Toast.LENGTH_LONG).show();
            if (myMap.get(querySave)!=null){
                //    view.setBackgroundResource(R.drawable.gradient);
                myMap.remove(querySave);
                Toast.makeText(MainActivity2.this, querySave+" Removed", Toast.LENGTH_SHORT).show();
                selectedString=selectedString.replace(querySave+",","");
                selectedET.setText(selectedString);
                selected.remove(querySave);
            }else {
                myMap.put(querySave, true);
                Toast.makeText(MainActivity2.this, querySave+" Added", Toast.LENGTH_SHORT).show();
                selectedString=selectedString+querySave+",";
                selected.add(querySave);
                selectedET.setText(selectedString);
            }
        } else {
            //if the value is not given displaying a toast
            Toast.makeText(this, "Please enter a name", Toast.LENGTH_LONG).show();
        }
    }
}

package com.example.siddhantlad.chefiecompile.DatabaseSource;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.siddhantlad.chefiecompile.AddRecipeFab;
import com.example.siddhantlad.chefiecompile.R;
import com.example.siddhantlad.chefiecompile.Welcome;
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
    EditText editTextName;
    Spinner spinnerGenre;
    Button buttonAddArtist, RecipeActivityButton;
    TextView artistname;
    ListView listViewArtists;
    public Map<String, Boolean> myMap;
    public static String Checks;
    //a list to store all the artist from firebase database
    List<Artist> artists;
    ArrayList<String> my_array_of_selected_ingredients;

    //our database reference object
    public static DatabaseReference databaseArtists;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //getting the reference of artists node
        databaseArtists = FirebaseDatabase.getInstance().getReference("artists");
        //getting views
        my_array_of_selected_ingredients = new ArrayList<String>();
        Context context = this;
        final ArrayAdapter<String> adapter_Selected = new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1,my_array_of_selected_ingredients);
        myMap = new HashMap();
        editTextName = (EditText) findViewById(R.id.editTextName);
        spinnerGenre = (Spinner) findViewById(R.id.spinnerGenres);
        artistname=(TextView)findViewById(R.id.textView1);
        listViewArtists = (ListView) findViewById(R.id.listViewArtists);
        RecipeActivityButton = (Button) findViewById(R.id.recipeButton);
        buttonAddArtist = (Button) findViewById(R.id.buttonAddArtist);
        //list to store artists
        artists = new ArrayList<>();
     //   IntentTransfer();
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
                Artist artist = artists.get(i);
                Checks=artist.getArtistName().toString();
                myMap.put("Banana",true);
                myMap.put(Checks, true);
                myMap.put("Orange",true);
                Toast.makeText(MainActivity.this, Checks, Toast.LENGTH_SHORT).show();
           /* try {
                if (myMap.get("Sugar")==true){
                    Toast.makeText(MainActivity.this, "Yes", Toast.LENGTH_SHORT).show();
                }else if (myMap.get("Kale")&&(myMap.get("Sugar"))){
                    Toast.makeText(MainActivity.this, "OKayy", Toast.LENGTH_SHORT).show();
                }
            }catch (Exception e){

            }*/
//Boolean Check=myMap.get(artist.getArtistName());

            }
        });

        //adding an onclicklistener to button
        buttonAddArtist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //calling the method addArtist()
                //the method is defined below
                //this method is actually performing the write operation
                addArtist();
            }
        });

        /*
        ListView Animation
        Animation animation = AnimationUtils.loadAnimation(MainActivity.this,  R.anim.push_up_in);
       listViewArtists.startAnimation(animation);*/
    }

    /*
     * This method is saving a new artist to the
     * Firebase Realtime Database
     * */
    private void addArtist() {
        //getting the values to save
        String name = editTextName.getText().toString().trim();
        String genre = spinnerGenre.getSelectedItem().toString();

        //checking if the value is provided
        if (!TextUtils.isEmpty(name)) {

            //getting a unique id using push().getKey() method
            //it will create a unique id and we will use it as the Primary Key for our Artist
            String id = databaseArtists.push().getKey();

            //creating an Artist Object
            Artist artist = new Artist(id, name, genre);

            //Saving the Artist
            databaseArtists.child(id).setValue(artist);

            //setting edittext to blank again
            editTextName.setText("");

            //displaying a success toast
            Toast.makeText(this, "Recipe added", Toast.LENGTH_LONG).show();
        } else {
            //if the value is not given displaying a toast
            Toast.makeText(this, "Please enter a name", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        //attaching value event listener
       /*ListView Animation
        Animation animation = AnimationUtils.loadAnimation(MainActivity.this,  R.anim.push_up_in);
        listViewArtists.startAnimation(animation);*/
        databaseArtists.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //clearing the previous artist list
                artists.clear();

                //iterating through all the nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //getting artist
                    Artist artist = postSnapshot.getValue(Artist.class);
                    //adding artist to the list
                    artists.add(artist);
                }

                //creating adapter
                ArtistList artistAdapter = new ArtistList(MainActivity.this, artists);
                //attaching adapter to the listview
                listViewArtists.setAdapter(artistAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
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
}

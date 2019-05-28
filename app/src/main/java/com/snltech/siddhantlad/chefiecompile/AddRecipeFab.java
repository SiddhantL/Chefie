package com.snltech.siddhantlad.chefiecompile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.snltech.siddhantlad.chefiecompile.DatabaseSource.ImageUploader;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AddRecipeFab extends AppCompatActivity {

    private Button btn;
    private ListView lv;
    Boolean continuing;
    private SpinnerAdapter customeAdapter;
    EditText editText,noteET;
    EditText listSelected;
    String selected;
    public ArrayList<EditModel> editModelArrayList;
    ArrayList<String> my_array_of_selected_ingredients;
    DatabaseReference mDatabase,mNewDatabase,creditDatabase,RecipeByNameDatabase;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe_fab);
        try {
            lv = (ListView) findViewById(R.id.listView);
            listSelected = (EditText) findViewById(R.id.textView28);
            selected = new String();
            btn = (Button) findViewById(R.id.btn);
            continuing = true;
            noteET = (EditText) findViewById(R.id.editText4);
            mDatabase = FirebaseDatabase.getInstance().getReference("recipes");
            RecipeByNameDatabase = FirebaseDatabase.getInstance().getReference("RecipeByName");
            creditDatabase = FirebaseDatabase.getInstance().getReference("credits");
            mNewDatabase = FirebaseDatabase.getInstance().getReference("steps");
            editModelArrayList = populateList();
            mAuth = FirebaseAuth.getInstance();
            customeAdapter = new SpinnerAdapter(this, editModelArrayList, my_array_of_selected_ingredients);
            lv.setAdapter(customeAdapter);
            editText = (EditText) findViewById(R.id.editText);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for (int check = 0; check < my_array_of_selected_ingredients.size(); check++) {
                        if (TextUtils.isEmpty(editModelArrayList.get(check).getEditTextValue())) {
                            Toast.makeText(AddRecipeFab.this, my_array_of_selected_ingredients.get(check) + " requires an action", Toast.LENGTH_SHORT).show();
                            continuing = false;
                        }
                    }
                    if (!TextUtils.isEmpty(editText.getText().toString()) && continuing == true) {
                        Intent intent = new Intent(AddRecipeFab.this, ImageUploader.class);
                        intent.putExtra("nameOfRecipe", editText.getText().toString());
                        startActivity(intent);
                        //finish();
                        String RecipeNameNew = "-" + editText.getText().toString().trim();
                        for (int counter = 0; counter < my_array_of_selected_ingredients.size(); counter++) {
                            if (counter < my_array_of_selected_ingredients.size()) {
                                String name = my_array_of_selected_ingredients.get(counter);
                                mDatabase.child(RecipeNameNew).push().setValue(name);
                                mDatabase.child(RecipeNameNew).child("artistName").setValue(editText.getText().toString());
                                creditDatabase.child(editText.getText().toString().trim()).child("author").setValue(mAuth.getCurrentUser().getUid());
                                creditDatabase.child(editText.getText().toString().trim()).child("Username").setValue(mAuth.getCurrentUser().getDisplayName());
                                creditDatabase.child(editText.getText().toString().trim()).child("Email").setValue(mAuth.getCurrentUser().getEmail().toString());
                                RecipeByNameDatabase.child(mAuth.getCurrentUser().getUid()).child(RecipeNameNew).child("artistName").setValue(editText.getText().toString());
                                // mDatabase.child(RecipeNameNew).child("artistAuthor").setValue();
                                EditModel editModel = new EditModel();
                                //       editModel.setEditTextValue(String.valueOf(counter));
                                int step = counter + 1;
                                mNewDatabase.child(editText.getText().toString().trim()).child(Integer.toString(counter)).setValue("Step " + Integer.toString(step) + ": " + editModelArrayList.get(counter).getEditTextValue());
                            }
                        }
                        if (!TextUtils.isEmpty(noteET.getText().toString())) {
                            mNewDatabase.child(editText.getText().toString().trim()).child(Integer.toString(my_array_of_selected_ingredients.size())).setValue("Note" + ": " + noteET.getText().toString());
                        }
                    } else {
                        if (TextUtils.isEmpty(editText.getText().toString()) && continuing == false) {
                            Toast.makeText(AddRecipeFab.this, "Add a Recipe Name and Action for Recipes", Toast.LENGTH_SHORT).show();
                        } else if (continuing == false) {
                            Toast.makeText(AddRecipeFab.this, "Add an Action to all recipes", Toast.LENGTH_SHORT).show();
                        } else if (TextUtils.isEmpty(editText.getText().toString())) {
                            Toast.makeText(AddRecipeFab.this, "Add a Recipe Name", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        }catch (Exception E){
            Toast.makeText(this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
        }
    }

    private ArrayList<EditModel> populateList(){

        ArrayList<EditModel> list = new ArrayList<>();
       Intent intent = getIntent();
        my_array_of_selected_ingredients = intent.getStringArrayListExtra("my_array_of_selected_ingredients");
        for(int i = 0; i <= my_array_of_selected_ingredients.size(); i++){
            EditModel editModel = new EditModel();
            if (i<my_array_of_selected_ingredients.size()) {
               editModel.setEditTextValue(my_array_of_selected_ingredients.get(i).toString());
               list.add(editModel);
               selected=selected+my_array_of_selected_ingredients.get(i)+ ",";
               listSelected.setText(selected);
           }else if (i==my_array_of_selected_ingredients.size()){
                editModel.setEditTextValue("Extra");
                list.add(editModel);
            }
        }
        return list;
    }


}
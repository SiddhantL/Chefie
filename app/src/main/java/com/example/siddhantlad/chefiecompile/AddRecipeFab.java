package com.example.siddhantlad.chefiecompile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.siddhantlad.chefiecompile.DatabaseSource.ImageUploader;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.UUID;

public class AddRecipeFab extends AppCompatActivity {

    private Button btn;
    private ListView lv;
    private SpinnerAdapter customeAdapter;
    EditText editText;
    public ArrayList<EditModel> editModelArrayList;
    ArrayList<String> my_array_of_selected_ingredients;
    DatabaseReference mDatabase,mNewDatabase,creditDatabase,RecipeByNameDatabase;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe_fab);
        lv = (ListView) findViewById(R.id.listView);
        btn = (Button) findViewById(R.id.btn);
        mDatabase = FirebaseDatabase.getInstance().getReference("recipes");
        RecipeByNameDatabase = FirebaseDatabase.getInstance().getReference("RecipeByName");
        creditDatabase = FirebaseDatabase.getInstance().getReference("credits");
        mNewDatabase= FirebaseDatabase.getInstance().getReference("steps");
        editModelArrayList = populateList();
        mAuth=FirebaseAuth.getInstance();
        customeAdapter = new SpinnerAdapter(this,editModelArrayList);
        lv.setAdapter(customeAdapter);
editText=(EditText)findViewById(R.id.editText);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddRecipeFab.this,ImageUploader.class);
                intent.putExtra("nameOfRecipe",editText.getText().toString());
                startActivity(intent);
               String RecipeNameNew = "-" + editText.getText().toString().trim();
                for (int counter=0;counter<my_array_of_selected_ingredients.size();counter++){
                    String name = my_array_of_selected_ingredients.get(counter);
                    mDatabase.child(RecipeNameNew).push().setValue(name);
                    mDatabase.child(RecipeNameNew).child("artistName").setValue(editText.getText().toString());
                    creditDatabase.child(editText.getText().toString().trim()).child("author").setValue(mAuth.getCurrentUser().getUid());
                    creditDatabase.child(editText.getText().toString().trim()).child("Username").setValue(mAuth.getCurrentUser().getDisplayName());
                    RecipeByNameDatabase.child(mAuth.getCurrentUser().getUid()).child(RecipeNameNew).child("artistName").setValue(editText.getText().toString());
                    // mDatabase.child(RecipeNameNew).child("artistAuthor").setValue();
                    EditModel editModel = new EditModel();
                    editModel.setEditTextValue(String.valueOf(counter));
                   mNewDatabase.child(editText.getText().toString().trim()).child(Integer.toString(counter)).setValue(my_array_of_selected_ingredients.get(counter)+": "+editModelArrayList.get(counter).getEditTextValue());
                    }
            }
        });

    }

    private ArrayList<EditModel> populateList(){

        ArrayList<EditModel> list = new ArrayList<>();
       Intent intent = getIntent();
        my_array_of_selected_ingredients = intent.getStringArrayListExtra("my_array_of_selected_ingredients");
        for(int i = 0; i < my_array_of_selected_ingredients.size(); i++){
            EditModel editModel = new EditModel();
            editModel.setEditTextValue(my_array_of_selected_ingredients.get(i).toString());
            list.add(editModel);
        }

        return list;
    }

}
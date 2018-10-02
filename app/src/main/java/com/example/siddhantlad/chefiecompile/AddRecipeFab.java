package com.example.siddhantlad.chefiecompile;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.siddhantlad.chefiecompile.DatabaseSource.ImageUploader;
import com.example.siddhantlad.chefiecompile.DatabaseSource.RecipeActivity;
import com.example.siddhantlad.chefiecompile.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class AddRecipeFab extends AppCompatActivity {
    ListView listView;
    EditText editText;
    int CountLast;
    Button ContinueBtn;
    String id;
    ArrayList<String> my_array_of_selected_ingredients;
    Context context;
    DatabaseReference mDatabase,mNewDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe_fab);
        mDatabase = FirebaseDatabase.getInstance().getReference("recipes");
        mNewDatabase=FirebaseDatabase.getInstance().getReference("steps");
        listView=(ListView) findViewById(R.id.listView);
        editText=(EditText)findViewById(R.id.RecipeName);
        ContinueBtn=(Button)findViewById(R.id.continueBtn);
        context=this;
        FillListView();
        id = mDatabase.push().getKey();
        ContinueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(editText.getText().toString()) && !my_array_of_selected_ingredients.isEmpty()) {
                    try {
                        int CountLast = listView.getAdapter().getCount();
                        String RecipeNameNew = "-" + editText.getText().toString().trim();
                        for (int Count = 0; Count <= CountLast - 1; Count++) {
                            String name = my_array_of_selected_ingredients.get(Count);
                            mDatabase.child(RecipeNameNew).push().setValue(name);
                            String recipeNameFinal = RecipeNameNew.substring(1);
                            mDatabase.child(RecipeNameNew).child("artistName").setValue(recipeNameFinal);
                            Intent AllRecipe = new Intent(AddRecipeFab.this, ImageUploader.class);
                            startActivity(AllRecipe);
                        }
                    } catch (Exception e) {
                    }
                } else {
                    Toast.makeText(context, "Add a name for the new recipe", Toast.LENGTH_SHORT).show();
                    Intent AllRecipe = new Intent(AddRecipeFab.this, ImageUploader.class);
                    AllRecipe.putExtra("my_array_of_selected_ingredients", my_array_of_selected_ingredients);
                    startActivity(AllRecipe);
                }
                int total=my_array_of_selected_ingredients.size();
                for (int i = 1; i <= total; i++) {
                    Spinner spinner = (Spinner)findViewById(R.id.row_item_spinner);
                    Spinner spinnerPos = (Spinner)findViewById(R.id.row_item_spinner_position);
                    mNewDatabase.child(editText.getText().toString()).child(Integer.toString(i)).setValue(
                            spinner.getSelectedItem().toString()+" "+listView.getItemAtPosition(i-1)/*+spinnerPos.getSelectedItem().toString()*/);
                }
            }

        });
    }
    public void FillListView(){
        Intent intent = getIntent();
        my_array_of_selected_ingredients = intent.getStringArrayListExtra("my_array_of_selected_ingredients");
        ArrayAdapter<String> adapter_Selected = new ArrayAdapter<String>(context,R.layout.simple_list_item_white,my_array_of_selected_ingredients);
        try {
            ArrayList<String> mSpinnerData = new ArrayList<>();
            mSpinnerData.add("Action");
            mSpinnerData.add("Add");
            mSpinnerData.add("Boil");
            mSpinnerData.add("Chop");
            mSpinnerData.add("Dice");
            mSpinnerData.add("Bake");
            mSpinnerData.add("Heat");
            mSpinnerData.add("Cool");
            mSpinnerData.add("Microwave");
            mSpinnerData.add("Soak");
            mSpinnerData.add("Dry");
            mSpinnerData.add("Bake");
            mSpinnerData.add("Fry");
            ArrayList<String> mSpinnerDataPosition = new ArrayList<>();
            mSpinnerDataPosition.add("Position");
            for (int x=1;x<=my_array_of_selected_ingredients.size();x++){
                mSpinnerDataPosition.add(Integer.toString(x));
            }
           /* mSpinnerDataPosition.add("1");
            mSpinnerDataPosition.add("2");
            mSpinnerDataPosition.add("3");
            mSpinnerDataPosition.add("4");*/
            SpinnerAdapter adapterPosition = new SpinnerAdapter(my_array_of_selected_ingredients, mSpinnerData,mSpinnerDataPosition,this);
            listView.setAdapter(adapterPosition);
         //   SpinnerAdapter adapter = new SpinnerAdapter(my_array_of_selected_ingredients, mSpinnerData, this);
          //  listView.setAdapter(adapter);
           // listView.setAdapter(adapter_Selected);
        }catch (Exception e){}

    }
}

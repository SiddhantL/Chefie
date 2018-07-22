package com.example.siddhantlad.chefiecompile.DatabaseSource;

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
import android.widget.Toast;

import com.example.siddhantlad.chefiecompile.DatabaseSource.RecipeActivity;
import com.example.siddhantlad.chefiecompile.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class AddRecipeActivity extends AppCompatActivity {
    ListView listView;
    EditText editText;
    int CountLast;
    Button ContinueBtn;
    String id;
    ArrayList<String> my_array_of_selected_ingredients;
    Context context;
    DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);
        mDatabase = FirebaseDatabase.getInstance().getReference("recipes");
        listView=(ListView) findViewById(R.id.listView);
        editText=(EditText)findViewById(R.id.RecipeName);
        ContinueBtn=(Button)findViewById(R.id.continueBtn);
        context=this;
        FillListView();
        id = mDatabase.push().getKey();
        ContinueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(editText.getText().toString()) && !my_array_of_selected_ingredients.isEmpty() ) {
                    try{
                        int CountLast=listView.getAdapter().getCount();
                        String RecipeNameNew ="-"+editText.getText().toString().trim();
                        for (int Count =0;Count<=CountLast-1;Count++) {
                            String name = my_array_of_selected_ingredients.get(Count);
                            mDatabase.child(RecipeNameNew).push().setValue(name);
                            String recipeNameFinal=RecipeNameNew.substring(1);
                            mDatabase.child(RecipeNameNew).child("artistName").setValue(recipeNameFinal);
                            Intent AllRecipe = new Intent(AddRecipeActivity.this, RecipeActivity.class);
                            startActivity(AllRecipe);
                        }
                    }catch (Exception e){}
                }else{
                    Toast.makeText(context, "Add a name for the new recipe", Toast.LENGTH_SHORT).show();
                    Intent AllRecipe=new Intent(AddRecipeActivity.this,RecipeActivity.class);
                    AllRecipe.putExtra("my_array_of_selected_ingredients",my_array_of_selected_ingredients);
                    startActivity(AllRecipe);
                }
            }
        });
    }
    public void FillListView(){
        Intent intent = getIntent();
        my_array_of_selected_ingredients = intent.getStringArrayListExtra("my_array_of_selected_ingredients");
        ArrayAdapter<String> adapter_Selected = new ArrayAdapter<String>(context,R.layout.simple_list_item_white,my_array_of_selected_ingredients);
        try {
            listView.setAdapter(adapter_Selected);
        }catch (Exception e){}

    }
}

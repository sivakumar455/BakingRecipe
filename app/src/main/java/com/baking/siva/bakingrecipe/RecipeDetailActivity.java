package com.baking.siva.bakingrecipe;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.Arrays;
import java.util.HashMap;

public class RecipeDetailActivity extends AppCompatActivity {
    HashMap<String, HashMap<String, String> > recipeDet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        Intent intent = getIntent();
        Bundle bundle = new Bundle();

        if(intent.getSerializableExtra("hashIngredients") != null) {
            recipeDet = (HashMap<String, HashMap<String, String>>) intent.getSerializableExtra("hashIngredients");
            Log.v("MAP", String.valueOf(Arrays.asList(recipeDet)));
            bundle.putSerializable("hashIngredients",recipeDet);
        }else if (intent.getSerializableExtra("hashSteps") != null){
               HashMap<String,String> steps = new HashMap<String,String>();
               steps = (HashMap<String,String>) intent.getSerializableExtra("hashSteps");
            Log.v("MAP", String.valueOf(Arrays.asList(steps)));
            bundle.putSerializable("hashSteps",steps);
        }

        //Log.v("MAP", String.valueOf(Arrays.asList(recipeDet)));

        RecipeDetailFragment recipeDetailFragment = new RecipeDetailFragment();
        recipeDetailFragment.setArguments(bundle);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.recipe_detail_container,recipeDetailFragment)
                .commit();
    }
}

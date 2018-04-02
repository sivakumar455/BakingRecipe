package com.baking.siva.bakingrecipe;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import java.util.Arrays;
import java.util.HashMap;

public class RecipeStepsActivity extends AppCompatActivity  implements  RecipeStepsFragment.OnStepClickListener{

    HashMap<String, HashMap<String, String>> recipeDet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_steps);
        final Intent intentStarted = getIntent();

        if (intentStarted.hasExtra(Intent.EXTRA_TEXT)) {
            recipeDet =
                    (HashMap<String, HashMap<String, String>>) intentStarted.getSerializableExtra(Intent.EXTRA_TEXT);

            Log.v("MAP",recipeDet.get("ingredients1").get("ingredient"));
            Log.v("MAP",recipeDet.get("Length").get("ingredientLength"));
            Log.v("MAP",recipeDet.get("Length").get("stepLength"));
            Log.v("MAP", String.valueOf(Arrays.asList(recipeDet)));
            //Picasso.with(getApplicationContext()).load(movieDet.get("poster")).placeholder(R.drawable.ic_launcher_background).into(poster);
        }

        RecipeStepsFragment recipeStepsFragment = new RecipeStepsFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("hashmap",recipeDet);
        recipeStepsFragment.setArguments(bundle);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.recipe_steps_container,recipeStepsFragment)
                .commit();
    }

    @Override
    public void OnStepSelected(int position) {
        Toast.makeText(this, "Position clicked = " + position, Toast.LENGTH_SHORT).show();
        HashMap<String, HashMap<String, String>> hashMap;
        Bundle b = new Bundle();

        if(position ==0){
            //hashMap = recipeDet.get("ingredients").get("ingredient0");
            b.putSerializable("hashIngredients",recipeDet);
            Log.v("RecipeStepsActivity", String.valueOf(Arrays.asList(recipeDet)));
        }else{
            position = position-1;
            b.putSerializable("hashSteps",recipeDet.get("steps"+position));
            Log.v("RecipeStepsActivity", String.valueOf(Arrays.asList(recipeDet.get("steps"+position))));
        }

        //b.putSerializable("hashmap",recipeDet);

        // Attach the Bundle to an intent
        Intent intent = new Intent(this, RecipeDetailActivity.class);
        intent.putExtras(b);
        startActivity(intent);
    }
}

package com.baking.siva.bakingrecipe;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import java.util.Collections;
import java.util.HashMap;

public class RecipeStepsActivity extends AppCompatActivity  implements  RecipeStepsFragment.OnStepClickListener{

    private HashMap<String, HashMap<String, String>> recipeDet;
    private boolean mTwoPane;

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
            Log.v("MAP", String.valueOf(Collections.singletonList(recipeDet)));
            //Picasso.with(getApplicationContext()).load(movieDet.get("poster")).placeholder(R.drawable.ic_launcher_background).into(poster);
        }

        RecipeStepsFragment recipeStepsFragment = new RecipeStepsFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("hashmap",recipeDet);

        if(findViewById(R.id.tab_container) != null) {
            // This LinearLayout will only initially exist in the two-pane tablet case
            Log.v("TAB","Checking tab");
            mTwoPane = true;

            bundle.putString("mode","twopane");
            recipeStepsFragment.setArguments(bundle);

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.recipe_steps_container,recipeStepsFragment)
                    .commit();

            RecipeDetailFragment recipeDetailFragment = new RecipeDetailFragment();
            Bundle b = new Bundle();
            b.putSerializable("hashIngredients",recipeDet);
            recipeDetailFragment.setArguments(b);

            Log.v("TAB","Checking tab1");
            FragmentManager fragmentDetManager = getSupportFragmentManager();
            fragmentDetManager.beginTransaction()
                    .add(R.id.recipe_detail_container,recipeDetailFragment)
                    .commit();
        }else {
            // We're in single-pane mode and displaying fragments on a phone in separate activities
            recipeStepsFragment.setArguments(bundle);

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.recipe_steps_container,recipeStepsFragment)
                    .commit();
            mTwoPane = false;
        }
    }

    @Override
    public void OnStepSelected(int position) {
        Toast.makeText(this, "Position clicked = " + position, Toast.LENGTH_SHORT).show();
        //HashMap<String, HashMap<String, String>> hashMap;
        Bundle b = new Bundle();

        if(position ==0){
            //hashMap = recipeDet.get("ingredients").get("ingredient0");
            b.putSerializable("hashIngredients",recipeDet);
            Log.v("RecipeStepsActivity", String.valueOf(Collections.singletonList(recipeDet)));
        }else{
            position = position-1;
            b.putSerializable("hashSteps",recipeDet.get("steps"+position));
            Log.v("RecipeStepsActivity", String.valueOf(Collections.singletonList(recipeDet.get("steps" + position))));
        }

        //b.putSerializable("hashmap",recipeDet);

        // Attach the Bundle to an intent
        if (mTwoPane) {
            Toast.makeText(this, "Cicked in Mtwopane", Toast.LENGTH_SHORT).show();
            //Toast.makeText(this, "d in Mtwopane", Toast.LENGTH_SHORT).show();

            RecipeDetailFragment recipeDetailFragment = new RecipeDetailFragment();
            recipeDetailFragment.setArguments(b);

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.recipe_detail_container,recipeDetailFragment)
                    .commit();

        }else {
            Intent intent = new Intent(this, RecipeDetailActivity.class);
            intent.putExtras(b);
            startActivity(intent);
        }
    }
}

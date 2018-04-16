package com.baking.siva.bakingrecipe;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.baking.siva.bakingrecipe.data.RecipeDbContract;
import com.baking.siva.bakingrecipe.data.RecipeDbHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

import static com.baking.siva.bakingrecipe.data.RecipeDbContract.RecipeDb.COLUMN_IMAGE_URL;
import static com.baking.siva.bakingrecipe.data.RecipeDbContract.RecipeDb.COLUMN_INGREDIENT;
import static com.baking.siva.bakingrecipe.data.RecipeDbContract.RecipeDb.COLUMN_INGREDIENTS_ID;
import static com.baking.siva.bakingrecipe.data.RecipeDbContract.RecipeDb.COLUMN_INGREDIENTS_SIZE;
import static com.baking.siva.bakingrecipe.data.RecipeDbContract.RecipeDb.COLUMN_MEASURE;
import static com.baking.siva.bakingrecipe.data.RecipeDbContract.RecipeDb.COLUMN_QUANTITY;
import static com.baking.siva.bakingrecipe.data.RecipeDbContract.RecipeDb.COLUMN_RECIPE_ID;
import static com.baking.siva.bakingrecipe.data.RecipeDbContract.RecipeDb.COLUMN_RECIPE_NAME;
import static com.baking.siva.bakingrecipe.data.RecipeDbContract.RecipeDb.COLUMN_STEPS_SIZE;
import static com.baking.siva.bakingrecipe.data.RecipeDbContract.RecipeDb.CONTENT_URI;
import static com.baking.siva.bakingrecipe.data.RecipeDbContract.RecipeDb._ID;

public class RecipeStepsActivity extends AppCompatActivity  implements  RecipeStepsFragment.OnStepClickListener{

    private HashMap<String, HashMap<String, String>> recipeDet;
    private boolean mTwoPane;
    private FragmentManager fragmentManagerOnePane;
    private RecipeStepsFragment recipeStepsFragment;
    private static final String MY_PREFS_NAME = "myPref";
    private final boolean  DEF= false;
    private  RecipeDetailFragment recipeDetailFragment;
    private SQLiteDatabase mDb;
    private HashMap<String,String> recipeHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_steps);

        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        Boolean pane = prefs.getBoolean("mPane",DEF);

        /*if(pane){*/
            if (savedInstanceState != null) {
                mTwoPane = pane;
                recipeDet = (HashMap<String, HashMap<String, String>>) savedInstanceState.getSerializable("recipeDet");
                recipeStepsFragment = (RecipeStepsFragment) getSupportFragmentManager().getFragment(savedInstanceState, "myStepFragment");
                if (mTwoPane) {
                    recipeDetailFragment = (RecipeDetailFragment) getSupportFragmentManager().getFragment(savedInstanceState, "myDetailFragment");
                }
            }
             else{
                stepRecipe();
            }

       /* }
        else {
            stepRecipe();
       }*/
    }

    private void addFavRecipes(){

        Log.v("addFavrecipes",String.valueOf(Collections.singletonList(recipeDet)));

        ArrayList<String> mArrayList = new ArrayList<>();
        RecipeDbHelper recipeDbHelper = new RecipeDbHelper(getApplicationContext());
        mDb = recipeDbHelper.getReadableDatabase();
        try {

            Cursor cursor = getContentResolver().query(CONTENT_URI,
                    null,
                    null,
                    null,
                    _ID);
            cursor.moveToFirst();
            mArrayList.add(cursor.getString(cursor.getColumnIndex(COLUMN_RECIPE_ID)));
            Log.v("StepActivity", "Dumping cursor");
            DatabaseUtils.dumpCursor(cursor);
            while (cursor.moveToNext()) {
                mArrayList.add(cursor.getString(cursor.getColumnIndex(COLUMN_RECIPE_ID)));
            }
            Log.v("StepActivity", String.valueOf(Arrays.asList(mArrayList)));
        }
        catch (Exception e){
            e.printStackTrace();
        }
        Log.v("StepActivity", String.valueOf(recipeHeader.get("id")));
        if(! mArrayList.contains(recipeHeader.get("id"))) {
            ContentValues cv = new ContentValues();
            for(int idx=0 ; idx < Integer.parseInt(recipeDet.get("Length").get("ingredientLength"));idx++) {
                cv.put(COLUMN_RECIPE_ID, String.valueOf(recipeHeader.get("id")));
                cv.put(COLUMN_RECIPE_NAME, String.valueOf(recipeHeader.get("name")));
                cv.put(COLUMN_IMAGE_URL, String.valueOf(recipeHeader.get("image")));
                cv.put(COLUMN_STEPS_SIZE, String.valueOf(recipeDet.get("Length").get("stepLength")));
                cv.put(COLUMN_INGREDIENTS_SIZE, String.valueOf(recipeDet.get("Length").get("ingredientLength")));

                cv.put(COLUMN_INGREDIENTS_ID, String.valueOf(idx));
                cv.put(COLUMN_QUANTITY, String.valueOf(recipeDet.get("ingredients"+idx).get("quantity")));
                cv.put(COLUMN_MEASURE, String.valueOf(recipeDet.get("ingredients"+idx).get("measure")));
                cv.put(COLUMN_INGREDIENT, String.valueOf(recipeDet.get("ingredients"+idx).get("ingredient")));
                cv.put(RecipeDbContract.RecipeDb.COLUMN_STEPS_ID, String.valueOf(idx));
                cv.put(RecipeDbContract.RecipeDb.COLUMN_SHORT_DESCRIPTION, String.valueOf(recipeHeader.get("name")));

                Uri uri = getContentResolver().insert(CONTENT_URI, cv);
                finish();
            }
             /*
            for (int idx=0 ; idx < Integer.parseInt(recipeDet.get("Length").get("stepLength"));idx++) {
                cv.put(COLUMN_RECIPE_ID, String.valueOf(recipeHeader.get("id")));
                cv.put(COLUMN_RECIPE_NAME, String.valueOf(recipeHeader.get("name")));
                cv.put(COLUMN_IMAGE_URL, String.valueOf(recipeHeader.get("image")));
                cv.put(COLUMN_STEPS_SIZE, String.valueOf(recipeDet.get("Length").get("stepLength")));
                cv.put(COLUMN_INGREDIENTS_SIZE, String.valueOf(recipeDet.get("Length").get("ingredientLength")));

                cv.put(COLUMN_STEPS_ID, String.valueOf(idx));
                cv.put(COLUMN_SHORT_DESCRIPTION, String.valueOf(recipeDet.get("steps"+idx).get("shortDescription")));
                cv.put(COLUMN_DESCRIPTION, String.valueOf(recipeDet.get("steps"+idx).get("description")));
                cv.put(COLUMN_VIDEO_URL, String.valueOf(recipeDet.get("steps"+idx).get("videoURL")));
                cv.put(COLUMN_THUMBNAIL_URL, String.valueOf(recipeDet.get("steps"+idx).get("thumbnailURL")));
                Uri uri = getContentResolver().insert(CONTENT_URI, cv);
                finish();
            }*/

        }else {
            Toast.makeText(getApplicationContext(),"Already added ",Toast.LENGTH_SHORT).show();
        }
    }

    private void stepRecipe(){
        final Intent intentStarted = getIntent();
        if (intentStarted.hasExtra(Intent.EXTRA_TEXT)) {
            recipeDet =
                    (HashMap<String, HashMap<String, String>>) intentStarted.getSerializableExtra(Intent.EXTRA_TEXT);

            Log.v("MAP", recipeDet.get("ingredients1").get("ingredient"));
            Log.v("MAP", recipeDet.get("Length").get("ingredientLength"));
            Log.v("MAP", recipeDet.get("Length").get("stepLength"));
            Log.v("MAP", String.valueOf(Collections.singletonList(recipeDet)));
            //Picasso.with(getApplicationContext()).load(movieDet.get("poster")).placeholder(R.drawable.ic_launcher_background).into(poster);
        }

        if(intentStarted.hasExtra("recipeHeader")){
            recipeHeader = (HashMap<String, String>) intentStarted.getSerializableExtra("recipeHeader");
            Log.v("recipeHeader", String.valueOf(Collections.singletonList(recipeHeader)));
        }

        Button fav = findViewById(R.id.fav_recipe_btn);
        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Favourites",Toast.LENGTH_SHORT).show();
                addFavRecipes();
            }
        });

        recipeStepsFragment = new RecipeStepsFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("hashmap", recipeDet);

        if (findViewById(R.id.tab_container) != null) {
            // This LinearLayout will only initially exist in the two-pane tablet case
            Log.v("TAB", "Checking tab");
            mTwoPane = true;
            SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
            editor.putBoolean("mPane",mTwoPane);
            editor.apply();

            bundle.putString("mode", "twopane");
            recipeStepsFragment.setArguments(bundle);

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.recipe_steps_container, recipeStepsFragment)
                    .commit();

            recipeDetailFragment = new RecipeDetailFragment();
            Bundle b = new Bundle();
            b.putSerializable("hashIngredients", recipeDet);
            recipeDetailFragment.setArguments(b);

            Log.v("TAB", "Checking tab1");
            FragmentManager fragmentDetManager = getSupportFragmentManager();
            fragmentDetManager.beginTransaction()
                    .add(R.id.recipe_detail_container, recipeDetailFragment)
                    .commit();
        } else {
            // We're in single-pane mode and displaying fragments on a phone in separate activities
            recipeStepsFragment.setArguments(bundle);

            fragmentManagerOnePane = getSupportFragmentManager();
            fragmentManagerOnePane.beginTransaction()
                    .add(R.id.recipe_steps_container, recipeStepsFragment)
                    .commit();
            mTwoPane = false;
        }
    }

    @Override
    public void OnStepSelected(int position) {
        //Toast.makeText(this, "Position clicked = " + position, Toast.LENGTH_SHORT).show();
        Log.v("POS", String.valueOf(position));
        Log.v("RecipeStepsActivity", String.valueOf(Collections.singletonList(recipeDet)));

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
            //Toast.makeText(this, "Cicked in Mtwopane", Toast.LENGTH_SHORT).show();
            recipeDetailFragment = new RecipeDetailFragment();
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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getSupportFragmentManager().putFragment(outState, "myStepFragment", recipeStepsFragment);
        if(mTwoPane) {
            getSupportFragmentManager().putFragment(outState, "myDetailFragment", recipeDetailFragment);
        }
        outState.putSerializable("recipeDet",recipeDet);
    }
}

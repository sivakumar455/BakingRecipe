package com.baking.siva.bakingrecipe;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import com.baking.siva.bakingrecipe.util.HttpRequest;
import com.baking.siva.bakingrecipe.util.RecipeList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {
    private final static String RECIPE_URL_KEY = "RECIPE_URL_KEY";
    private final static String RECIPE_URL =
            "https://gist.githubusercontent.com/sivakumar455/ef700c03d15d1d6530e082b0f96e93e9/raw/e726bf72a2b8b21c4620b3ca2c701c75280b0b95/baking.json";
            //"https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";
    private static final int RECIPE_LOADER = 99;
    private static Parcelable state;
    private ListView listView;
    private GridView gridView;
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle myBundle = new Bundle();
        myBundle.putString(RECIPE_URL_KEY, RECIPE_URL);

        if(findViewById(R.id.recipe_grid_view) != null){
            mTwoPane = true;
        }

        LoaderManager loaderManager = getSupportLoaderManager();
        Loader<String> recipeLoader = loaderManager.getLoader(RECIPE_LOADER);

        if (recipeLoader == null) {
            Log.v("MainActivity", "loader is null");
            loaderManager.initLoader(RECIPE_LOADER, myBundle, this);
        } else {
            Log.v("MainActivity", "loader not null");
            loaderManager.restartLoader(RECIPE_LOADER, myBundle, this);
        }
    }

    @Override
    public Loader<String> onCreateLoader(int id, final Bundle args) {
        return new AsyncTaskLoader<String>(this) {
            String jsonRes;
            @Override
            protected void onStartLoading() {
                //super.onStartLoading();
                if (args == null)
                {
                    Log.v("MainActivity","onStartLoading is null");
                    return;
                }
                if (jsonRes != null) {
                    deliverResult(jsonRes);
                } else {
                    forceLoad();
                }
            }

            @Override
            public String loadInBackground() {
                Log.v("MainActivity","loadInBackground");
                String myrecipe = args.getString(RECIPE_URL_KEY);
                if(myrecipe == null || TextUtils.isEmpty(myrecipe)){
                    return null;
                }
                try {
                    Log.v("MainActivity","loadInBackground try ");
                    Log.v("MainActivity",myrecipe);
                    HttpRequest myRecipeList = new HttpRequest(myrecipe);
                    return myRecipeList.getJsonString();
                }catch (Exception e){
                    e.printStackTrace();
                    return null;
                }
                //return null;
            }

            @Override
            public void deliverResult(String data) {
                super.deliverResult(data);
                jsonRes =data;
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<String> loader,final String data) {

        RecipeList recipeNameList = new RecipeList(data);
        ArrayList<String> recipeList = recipeNameList.getRecipeList();

        if(!mTwoPane) {
            Log.v("MODE","Single Pane");

            listView = findViewById(R.id.recipe_list_view);
            RecipeAdapter recipeAdapter = new RecipeAdapter(this, recipeList);
            listView.setAdapter(recipeAdapter);
            if (state != null) {
                Log.d("MainAcitvity", "trying to restore listview state..");
                listView.onRestoreInstanceState(state);
            }
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    HashMap<String, HashMap<String, String>> myRecipe;
                    RecipeList recipeDtl = new RecipeList(data);
                    myRecipe = recipeDtl.getRecipeDetails(position);
                    Log.v("MAP", String.valueOf(Collections.singletonList(myRecipe)));
                    Intent intent = new Intent(getApplicationContext(), RecipeStepsActivity.class);
                    intent.putExtra(Intent.EXTRA_TEXT, myRecipe);
                    startActivity(intent);
                }
            });
        }else{
            Log.v("MODE","Two Pane");
            gridView = findViewById(R.id.recipe_grid_view);
            RecipeAdapter recipeAdapter = new RecipeAdapter(this, recipeList);
            gridView.setAdapter(recipeAdapter);
            if (state != null) {
                Log.d("MainAcitvity", "trying to restore gridview state..");
                gridView.onRestoreInstanceState(state);
            }
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    HashMap<String, HashMap<String, String>> myRecipe;
                    RecipeList recipeDtl = new RecipeList(data);
                    myRecipe = recipeDtl.getRecipeDetails(position);
                    Log.v("MAP", String.valueOf(Collections.singletonList(myRecipe)));
                    Intent intent = new Intent(getApplicationContext(), RecipeStepsActivity.class);
                    intent.putExtra(Intent.EXTRA_TEXT, myRecipe);
                    startActivity(intent);
                }
            });
        }
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {

    }
    @Override
    protected void onPause() {
        super.onPause();
        Log.d("MainActivity", "saving listview state @onPause");
        if (listView != null) {
            state = listView.onSaveInstanceState();

        }
    }
}

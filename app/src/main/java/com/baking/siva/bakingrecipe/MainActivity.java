package com.baking.siva.bakingrecipe;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;

import com.baking.siva.bakingrecipe.util.HeaderAdapter;
import com.baking.siva.bakingrecipe.util.HttpRequest;
import com.baking.siva.bakingrecipe.util.RecipeList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String>,HeaderAdapter.ListItemClickListener {
    private final static String RECIPE_URL_KEY = "RECIPE_URL_KEY";
    private final static String RECIPE_URL =
            "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";
     //"https://gist.githubusercontent.com/sivakumar455/ef700c03d15d1d6530e082b0f96e93e9/raw/e726bf72a2b8b21c4620b3ca2c701c75280b0b95/baking.json";
    private static final int RECIPE_LOADER = 99;
    private static Parcelable state;
    private RecyclerView listRecycView;
    private RecyclerView gridRecycView;
    private boolean mTwoPane;
    private RecyclerView.LayoutManager  mLayoutManager;
    private GridLayoutManager gridLayoutManager;
    private HeaderAdapter mAdapter;
    private String mData;
    private Parcelable stateList;
    private Parcelable stateGrid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle myBundle = new Bundle();
        myBundle.putString(RECIPE_URL_KEY, RECIPE_URL);

        if(findViewById(R.id.recipe_grid_view) != null){
            mTwoPane = true;
        }

        if(savedInstanceState != null) {
            if(savedInstanceState.getParcelable("listRecycView") != null) {
                stateList = savedInstanceState.getParcelable("listRecycView");
            }else {
                stateGrid = savedInstanceState.getParcelable("gridRecycView");
            }

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

        mData = data;
        RecipeList recipeNameList = new RecipeList(data);
        ArrayList<String> recipeList = recipeNameList.getRecipeList();

        if(!mTwoPane) {
            Log.v("MODE","Single Pane");

            listRecycView = findViewById(R.id.recipe_list_view);
            //RecipeAdapter recipeAdapter = new RecipeAdapter(this, recipeList);
            mLayoutManager = new LinearLayoutManager(getApplicationContext());

            listRecycView.setLayoutManager(mLayoutManager);

            mAdapter = new HeaderAdapter(getApplicationContext(),recipeList,this);
            listRecycView.setAdapter(mAdapter);

            if (stateList != null) {
                Log.d("MainAcitvity", "trying to restore listview state..");
                //listRecycView.onRestoreInstanceState(state);
                mLayoutManager.onRestoreInstanceState(stateList);
            }
        }else{
            Log.v("MODE","Two Pane");

            gridRecycView = findViewById(R.id.recipe_grid_view);
            //RecipeAdapter recipeAdapter = new RecipeAdapter(this, recipeList);
            gridLayoutManager = new GridLayoutManager(getApplicationContext(),3);

            gridRecycView.setLayoutManager(gridLayoutManager);

            mAdapter = new HeaderAdapter(getApplicationContext(),recipeList,this);
            gridRecycView.setAdapter(mAdapter);

            if (state != null) {
                Log.d("MainAcitvity", "trying to restore gridview state..");
                //gridView.onRestoreInstanceState(state);
                gridLayoutManager.onRestoreInstanceState(stateGrid);
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Log.d("MainActivity", "saving listview/gridview state @onSaveInstanceState");
        if (listRecycView != null) {
            state = mLayoutManager.onSaveInstanceState();
            outState.putParcelable("listRecycView", state);
        }else if (gridRecycView != null){
            state = gridLayoutManager.onSaveInstanceState();
            outState.putParcelable("gridRecycView", state);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        /*if(savedInstanceState != null){
            Log.v("MainActivity", "saving listview/gridview state @OnRestoreInstance");
            if (savedInstanceState.getParcelable("listRecycView") != null) {
                Log.v("MainActivity", "saving listviewstate @OnRestoreInstance");
                Parcelable stateList = savedInstanceState.getParcelable("listRecycView");
            }else if (savedInstanceState.getParcelable("gridRecycView") != null){
                Log.v("MainActivity", "saving gridview state @OnRestoreInstance");
                state = savedInstanceState.getParcelable("gridRecycView");
                gridLayoutManager.onRestoreInstanceState(state);
            }
        }*/
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        //Toast.makeText(getApplicationContext(),String.valueOf(clickedItemIndex),Toast.LENGTH_SHORT).show();
        HashMap<String, HashMap<String, String>> myRecipe;
        if (mData != null) {
            RecipeList recipeDtl = new RecipeList(mData);
            myRecipe = recipeDtl.getRecipeDetails(clickedItemIndex);
            Log.v("MAP", String.valueOf(Collections.singletonList(myRecipe)));
            Intent intent = new Intent(getApplicationContext(), RecipeStepsActivity.class);
            intent.putExtra(Intent.EXTRA_TEXT, myRecipe);

            HashMap<String,String> recipeHeader;
            recipeHeader = recipeDtl.getRecipeHeader(clickedItemIndex);
            intent.putExtra("recipeHeader", recipeHeader);

            startActivity(intent);
        }
    }
}

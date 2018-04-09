package com.baking.siva.bakingrecipe;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.baking.siva.bakingrecipe.util.HttpRequest;
import com.baking.siva.bakingrecipe.util.RecipeList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * @author Siva Kumar Padala
 * @version 1.0
 * @since 04/02/18
 */

public class GridWidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new GridRemoteViewsFactory(this.getApplicationContext(),intent);
    }
}

class GridRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory{

    private final Context mContext;
    private Intent mIntent;
    private List<String> collection = new ArrayList<>();
    private RecipeList recipeNameList;
    private HttpRequest myRecipeList;

    private final static String RECIPE_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

    private void initData(){


        /*collection.clear();
        for (int idx= 0; idx<10;idx++){
            collection.add("List"+idx);
        }*/
        String myrecipe = RECIPE_URL ;

        try {
            Log.v("GridRemoteViewsFactory","GridRemoteViewsFactory try ");
            Log.v("GridRemoteViewsFactory",myrecipe);
            myRecipeList = new HttpRequest(myrecipe);
            recipeNameList = new RecipeList(myRecipeList.getJsonString());
            collection = recipeNameList.getRecipeList();
            //return myRecipeList.getJsonString();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public GridRemoteViewsFactory(Context applicationContext,Intent intent){
        mContext= applicationContext;
        mIntent = intent;
    }

    @Override
    public void onCreate() {
        initData();

    }

    @Override
    public void onDataSetChanged() {
        initData();

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return collection.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {

        RemoteViews view = new RemoteViews(mContext.getPackageName(),
                android.R.layout.simple_list_item_1);
        view.setTextViewText(android.R.id.text1, collection.get(position));

        Bundle extras = new Bundle();
        //extras.putInt("position", position);
        HashMap<String, HashMap<String, String>> myRecipe;

        RecipeList recipeDtl = new RecipeList(myRecipeList.getJsonString());
        myRecipe = recipeDtl.getRecipeDetails(position);
        extras.putSerializable("hashIngredients", myRecipe);
        Log.v("GridRemoteViewsFactory", String.valueOf(Collections.singletonList(myRecipe)));
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);
        view.setOnClickFillInIntent(android.R.id.text1, fillInIntent);

        return view;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}

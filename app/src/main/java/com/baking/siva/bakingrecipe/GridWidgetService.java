package com.baking.siva.bakingrecipe;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.baking.siva.bakingrecipe.data.RecipeDbContract;
import com.baking.siva.bakingrecipe.data.RecipeDbHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    //private RecipeList recipeNameList;
    //private HttpRequest myRecipeList;
    private SQLiteDatabase mDb;
    private ArrayList<String>  mIdList = new ArrayList<>() ;
    private ArrayList<String>  mIngList = new ArrayList<>() ;

    //private final static String RECIPE_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

    private void initData(){

        /*collection.clear();
        for (int idx= 0; idx<10;idx++){
            collection.add("List"+idx);
        }*/
        /*String myrecipe = RECIPE_URL ;

        try {
            Log.v("GridRemoteViewsFactory","GridRemoteViewsFactory try ");
            Log.v("GridRemoteViewsFactory",myrecipe);
            myRecipeList = new HttpRequest(myrecipe);
            recipeNameList = new RecipeList(myRecipeList.getJsonString());
            collection = recipeNameList.getRecipeList();
            //return myRecipeList.getJsonString();
        }catch (Exception e){
            e.printStackTrace();
        }*/
        Log.v("TST","checking0");
        /*gridRecipeAsyncTask mytask = new gridRecipeAsyncTask();
        mytask.execute(myrecipe);*/
        recipeData();
    }

    private void recipeData(){
        ArrayList<String> mArrayList = new ArrayList<>();

        RecipeDbHelper moviesDbHelper = new RecipeDbHelper(mContext);
        mDb = moviesDbHelper.getReadableDatabase();
        try {
            Cursor cursor =  mContext.getContentResolver().query(RecipeDbContract.RecipeDb.CONTENT_URI,
                    null,
                    null,
                    null,
                    RecipeDbContract.RecipeDb._ID );
            if(cursor != null) {
                cursor.moveToFirst();
                mArrayList.add(cursor.getString(cursor.getColumnIndex(RecipeDbContract.RecipeDb.COLUMN_RECIPE_NAME)));
                //DatabaseUtils.dumpCursor(cursor);
                while (cursor.moveToNext()) {
                    mArrayList.add(cursor.getString(cursor.getColumnIndex(RecipeDbContract.RecipeDb.COLUMN_RECIPE_NAME)));
                    //Log.v("28", cursor.getString(cursor.getColumnIndex(RecipeDbContract.RecipeDb._ID)));
                }
                cursor.close();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        Log.v("Widget", String.valueOf(Collections.singletonList(mArrayList)));
        Set<String> hs = new HashSet<>();
        hs.addAll(mArrayList);
        mArrayList.clear();
        mArrayList.addAll(hs);
        Log.v("Widget", String.valueOf(Collections.singletonList(mArrayList)));
        collection = mArrayList;
    }

    private ArrayList<String> getRecipeId(String recipeName){
        ArrayList<String> mArrayList = new ArrayList<>();

        String AUTHORITY = "com.baking.siva.bakingrecipe.data";

        Uri BASE_CONTENT_URI = Uri.parse("content://"+AUTHORITY+"/RecipeDb");

        String PATH_NAME = recipeName;
        Uri uri = BASE_CONTENT_URI.buildUpon()
                    .appendPath(PATH_NAME).build();

        RecipeDbHelper moviesDbHelper = new RecipeDbHelper(mContext);
        mDb = moviesDbHelper.getReadableDatabase();
        /*Uri uri = Uri.parse(RecipeDbContract.RecipeDb.CONTENT_URI+"/#").buildUpon()
                .appendPath(recipeName)
                .build();*/

        Log.v("Widget getRecipeId",String.valueOf(uri));
        try {
            Cursor cursor =  mContext.getContentResolver().query(uri,
                    null,
                    null,
                    null,
                    RecipeDbContract.RecipeDb._ID );
            if(cursor != null) {
                cursor.moveToFirst();
                mArrayList.add(cursor.getString(cursor.getColumnIndex(RecipeDbContract.RecipeDb.COLUMN_RECIPE_ID)));
                //DatabaseUtils.dumpCursor(cursor);
                while (cursor.moveToNext()) {
                    mArrayList.add(cursor.getString(cursor.getColumnIndex(RecipeDbContract.RecipeDb.COLUMN_RECIPE_ID)));
                    //Log.v("28", cursor.getString(cursor.getColumnIndex(RecipeDbContract.RecipeDb._ID)));
                }
                cursor.close();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        Log.v("Widget", String.valueOf(Collections.singletonList(mArrayList)));
        return mArrayList;
    }


    private ArrayList<String> getIngredients(String Id){
        ArrayList<String> mArrayList = new ArrayList<>();

        String AUTHORITY = "com.baking.siva.bakingrecipe.data";
        Uri BASE_CONTENT_URI = Uri.parse("content://"+AUTHORITY+"/RecipeDb");
        String PATH_NAME = Id;
        Uri uri = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_NAME).build();

        RecipeDbHelper moviesDbHelper = new RecipeDbHelper(mContext);
        mDb = moviesDbHelper.getReadableDatabase();
        Log.v("Widget getIngredients",String.valueOf(uri));

        try {
            Cursor cursor =  mContext.getContentResolver().query(uri,
                    null,
                    null,
                    null,
                    RecipeDbContract.RecipeDb._ID );
            if(cursor != null) {
                cursor.moveToFirst();
                mArrayList.add(cursor.getString(cursor.getColumnIndex(RecipeDbContract.RecipeDb.COLUMN_INGREDIENT))+ "\t" +
                        cursor.getString(cursor.getColumnIndex(RecipeDbContract.RecipeDb.COLUMN_QUANTITY)) + "\t" +
                        cursor.getString(cursor.getColumnIndex(RecipeDbContract.RecipeDb.COLUMN_MEASURE)));
                //DatabaseUtils.dumpCursor(cursor);
                while (cursor.moveToNext()) {
                    mArrayList.add(cursor.getString(cursor.getColumnIndex(RecipeDbContract.RecipeDb.COLUMN_INGREDIENT))+ "\t" +
                            cursor.getString(cursor.getColumnIndex(RecipeDbContract.RecipeDb.COLUMN_QUANTITY)) + "\t" +
                            cursor.getString(cursor.getColumnIndex(RecipeDbContract.RecipeDb.COLUMN_MEASURE)));
                }
                cursor.close();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        Log.v("Widget getIngredients", String.valueOf(Collections.singletonList(mArrayList)));
        return mArrayList;
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
        Log.v("TST","getViewAt");

        RemoteViews view = new RemoteViews(mContext.getPackageName(),
                android.R.layout.simple_list_item_1);
        view.setTextViewText(android.R.id.text1, collection.get(position));

        mIdList = getRecipeId(collection.get(position));
        mIngList = getIngredients(mIdList.get(0));

        Bundle extras = new Bundle();
        extras.putStringArrayList("mIngList", mIngList);
        //HashMap<String, HashMap<String, String>> myRecipe;
       /* RecipeList recipeDtl = new RecipeList(myRecipeList.getJsonString());
        myRecipe = recipeDtl.getRecipeDetails(position);
        extras.putSerializable("hashIngredients", myRecipe);
        Log.v("GridRemoteViewsFactory", String.valueOf(Collections.singletonList(myRecipe)));*/


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

    /*private class gridRecipeAsyncTask extends AsyncTask<String,Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            Log.v("TST","checking");
            myRecipeList = new HttpRequest(strings[0]);
            return myRecipeList.getJsonString();
        }

        @Override
        protected void onPostExecute(final String trStr) {
            if(trStr != null){
                Log.v("TST","checking1");
                recipeNameList = new RecipeList(trStr);
                collection = recipeNameList.getRecipeList();
            }
            Log.v("TST","checking1"+collection);
            super.onPostExecute(trStr);
        }
    }*/
}

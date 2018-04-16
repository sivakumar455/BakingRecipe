package com.baking.siva.bakingrecipe.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * @author  Siva Kumar Padala
 * @version 1.0
 * @since   16/04/18
 */

public class RecipeContentProvider extends ContentProvider {

    private static final int TASKS = 100;
    private static final int TASK_WITH_ID = 101;
    private static final int TASK_WITH_NAME = 102;
    private RecipeDbHelper recipeDbHelper;

    private static final UriMatcher sUriMatcher = buildUriMatcher();

    private static UriMatcher buildUriMatcher(){
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(RecipeDbContract.AUTHORITY,RecipeDbContract.PATH_TASKS,TASKS);
        uriMatcher.addURI(RecipeDbContract.AUTHORITY,RecipeDbContract.PATH_TASKS+"/#",TASK_WITH_ID);
        uriMatcher.addURI(RecipeDbContract.AUTHORITY,RecipeDbContract.PATH_TASKS+"/*",TASK_WITH_NAME);
        return uriMatcher;
    }


    @Override
    public boolean onCreate() {
        Context context = getContext();
        recipeDbHelper = new RecipeDbHelper(context);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        final SQLiteDatabase db = recipeDbHelper.getReadableDatabase();
        int match = sUriMatcher.match(uri);
        Log.v("ContentProvider", String.valueOf(uri));

        Cursor retCursor;
        switch (match){
            case TASKS:
                Log.v("ContentProvider","TASKS");
                retCursor = db.query(RecipeDbContract.RecipeDb.TABLE_NAME,
                        strings,
                        s,
                        strings1,
                        null,
                        null,
                        s1);
                break;
            case TASK_WITH_ID:
                // Get the id from the URI
                Log.v("ContentProvider","TASKS_WITH_ID");
                String id = uri.getPathSegments().get(1);
                //String id = "Brownies";

                Log.v("ContentProvider",id);

                // Selection is the _ID column = ?, and the Selection args = the row ID from the URI
                String mSelection = "RecipeId=?";
                String[] mSelectionArgs = new String[]{id};

                // Construct a query as you would normally, passing in the selection/args
                retCursor =  db.query(RecipeDbContract.RecipeDb.TABLE_NAME,
                        strings,
                        mSelection,
                        mSelectionArgs,
                        null,
                        null,
                        s1);
                break;
            case TASK_WITH_NAME:
                //throw new UnsupportedOperationException("Unsupported  query "+ uri);
                Log.v("ContentProvider","TASKS_WITH_NAME");
                String idDef = uri.getPathSegments().get(1);
                //String idDef = "Brownies";

                Log.v("ContentProvider",idDef);

                // Selection is the _ID column = ?, and the Selection args = the row ID from the URI
                String mSelectionDef = "RecipeName=?";
                String[] mSelectionArgsDef = new String[]{idDef};

                // Construct a query as you would normally, passing in the selection/args
                retCursor =  db.query(RecipeDbContract.RecipeDb.TABLE_NAME,
                        strings,
                        mSelectionDef,
                        mSelectionArgsDef,
                        RecipeDbContract.RecipeDb.COLUMN_RECIPE_ID,
                        null,
                        s1);
                break;
            default:
                throw new UnsupportedOperationException("Unsupported  query "+ uri);
        }

        retCursor.setNotificationUri(getContext().getContentResolver(),uri);
        return retCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        final SQLiteDatabase db = recipeDbHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);

        Uri retrunUri;

        switch (match){
            case TASKS:
                long id = db.insert(RecipeDbContract.RecipeDb.TABLE_NAME,null,contentValues);
                if (id > 0){
                    retrunUri = ContentUris.withAppendedId(RecipeDbContract.RecipeDb.CONTENT_URI,id);
                }else{
                    throw new UnsupportedOperationException("Failed to insert  "+ uri);
                }

                break;
            default:
                throw new UnsupportedOperationException("Default query in Insert "+ uri);
        }
        getContext().getContentResolver().notifyChange(uri,null);
        return  retrunUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        final SQLiteDatabase db = recipeDbHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        String id = uri.getPathSegments().get(1);
        String mWhere = "_id=?";
        String[] mWhereArgs = new String[]{id};

        int res;
        switch (match){
            case TASK_WITH_ID:
                res = db.delete(RecipeDbContract.RecipeDb.TABLE_NAME,mWhere,mWhereArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unsupported delete"+ uri);
        }
        getContext().getContentResolver().notifyChange(uri,null);

        return res;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}

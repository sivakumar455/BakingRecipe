package com.baking.siva.bakingrecipe.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author Siva Kumar Padala
 * @version 1.0
 * @since 04/02/18
 */


public class RecipeDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "myrecipe.db";
    private static final int DATABASE_VERSION = 1;

    public RecipeDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_RECIPELIST_TABLE = "CREATE TABLE " + RecipeDbContract.RecipeDb.TABLE_NAME + " (" +
                RecipeDbContract.RecipeDb._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                RecipeDbContract.RecipeDb.COLUMN_RECIPE_ID+ " INTEGER NOT NULL," +
                RecipeDbContract.RecipeDb.COLUMN_RECIPE_NAME + " TEXT NOT NULL, " +
                RecipeDbContract.RecipeDb.COLUMN_IMAGE_URL + " TEXT NOT NULL, " +
                RecipeDbContract.RecipeDb.COLUMN_STEPS_SIZE + " TEXT NOT NULL, " +
                RecipeDbContract.RecipeDb.COLUMN_INGREDIENT_SIZE + " TEXT NOT NULL, " +
                RecipeDbContract.RecipeDb.COLUMN_INGREDIENTS_ID + " TEXT, " +
                RecipeDbContract.RecipeDb.COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                RecipeDbContract.RecipeDb.COLUMN_INGREDIENTS_ID + " TEXT NOT NULL, " +
                RecipeDbContract.RecipeDb.COLUMN_QUANTITY + " TEXT NOT NULL, " +
                RecipeDbContract.RecipeDb.COLUMN_MEASURE + " TEXT NOT NULL, " +
                RecipeDbContract.RecipeDb.COLUMN_INGREDIENT + " TEXT NOT NULL, " +
                RecipeDbContract.RecipeDb.COLUMN_STEPS_ID + " TEXT NOT NULL, " +
                RecipeDbContract.RecipeDb.COLUMN_SHORT_DESCRIPTION + " TEXT NOT NULL, " +
                RecipeDbContract.RecipeDb.COLUMN_DESCRIPTION + " TEXT NOT NULL, " +
                RecipeDbContract.RecipeDb.COLUMN_VIDEO_URL + " TEXT NOT NULL, " +
                RecipeDbContract.RecipeDb.COLUMN_THUMBNAIL_URL + " TEXT NOT NULL, " +
                "); ";
        sqLiteDatabase.execSQL(SQL_CREATE_RECIPELIST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + RecipeDbContract.RecipeDb.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}

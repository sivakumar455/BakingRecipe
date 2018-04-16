package com.baking.siva.bakingrecipe.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * @author Siva Kumar Padala
 * @version 1.0
 * @since 04/02/18
 */


public class RecipeDbContract {
    public static final String AUTHORITY = "com.baking.siva.bakingrecipe.data";

    private static final Uri BASE_CONTENT_URI = Uri.parse("content://"+AUTHORITY);

    public static final String PATH_TASKS = "RecipeDb";

    public static final class RecipeDb implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_TASKS).build();

        public static final String TABLE_NAME = "RecipeDb";
        public static final String COLUMN_RECIPE_ID = "RecipeId";
        public static final String COLUMN_RECIPE_NAME = "RecipeName";
        public static final String COLUMN_IMAGE_URL = "Image";
        public static final String COLUMN_STEPS_SIZE = "StepSize";
        public static final String COLUMN_INGREDIENTS_SIZE = "IngredientsSize";
        public static final String COLUMN_TIMESTAMP = "timestamp";
        public static final String COLUMN_INGREDIENTS_ID = "IngredientsId";
        public static final String COLUMN_QUANTITY = "Quantity";
        public static final String COLUMN_MEASURE = "Measure";
        public static final String COLUMN_INGREDIENT = "Ingredient";
        public static final String COLUMN_STEPS_ID = "StepsId";
        public static final String COLUMN_SHORT_DESCRIPTION = "ShortDescription";
        public static final String COLUMN_DESCRIPTION = "Description";
        public static final String COLUMN_VIDEO_URL = "VideoUrl";
        public static final String COLUMN_THUMBNAIL_URL = "ThumbnailUrl";
    }
}

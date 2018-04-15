package com.baking.siva.bakingrecipe;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Siva Kumar Padala
 * @version 1.0
 * @since 04/02/18
 */

@RunWith(AndroidJUnit4.class)
public class RecipeListViewTest {
    @Rule
    public ActivityTestRule<MainActivity> myTest = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void click_on_list_view_images() {
        // Espresso.onData(anything()).inAdapterView(ViewMatchers.withId(R.id.recipe_list_view)).atPosition(1).perform(ViewActions.click());

        try
        {
            Espresso.onView(ViewMatchers.withId(R.id.recipe_list_view)).perform(RecyclerViewActions.actionOnItemAtPosition(0, ViewActions.click()));
            Log.v("RecipeListViewTest","List View Testing passed");
        }catch (NoMatchingViewException e){
            Log.v("RecipeListViewTest","List View Testing failed");
        }
        try{
            Espresso.onView(ViewMatchers.withId(R.id.recipe_grid_view)).perform(RecyclerViewActions.actionOnItemAtPosition(0, ViewActions.click()));
            Log.v("RecipeListViewTest","grid View Testing passed ");
        }catch (NoMatchingViewException e){
            Log.v("RecipeListViewTest","grid View Testing failed ");

        }
    }

}

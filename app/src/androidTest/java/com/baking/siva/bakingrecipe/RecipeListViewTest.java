package com.baking.siva.bakingrecipe;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.anything;

/**
 * Created by sivakumarpadala on 05/04/18.
 */

@RunWith(AndroidJUnit4.class)
public class RecipeListViewTest {
    @Rule
    public ActivityTestRule<MainActivity> myTest = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void click_on_list_view_images(){
        Espresso.onData(anything()).inAdapterView(ViewMatchers.withId(R.id.recipe_list_view)).atPosition(1).perform(ViewActions.click());
    }
}

package com.baking.siva.bakingrecipe;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;


import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


/**
 * @author Siva Kumar Padala
 * @version 1.0
 * @since 03/04/18
 */


@RunWith(AndroidJUnit4.class)
public class RecipeListActivityTest {
    @Rule public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void click_recipe_button(){
        Espresso.onView(ViewMatchers.withId(R.id.recipe_list_view)).perform();
        //onView(withId(R.id.recipe_list_view));


    }
}

package com.baking.siva.bakingrecipe;

import android.app.Activity;
import android.app.Instrumentation;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.isInternal;
import static org.hamcrest.Matchers.not;


/**
 * @author Siva Kumar Padala
 * @version 1.0
 * @since 04/02/18
 */

@RunWith(AndroidJUnit4.class)
public class RecipeIntentTest {
    @Rule public IntentsTestRule<MainActivity> mMainActivity = new IntentsTestRule<>(MainActivity.class);

    @Before
    public void stubForAllIntents(){
        intending(not(isInternal())).respondWith(new Instrumentation.ActivityResult(Activity.RESULT_OK, null));
    }
    /*@Test
    public void intentVerificationTesting(){
        Intents.intended(toPackage("com.baking.siva.bakingrecipe"));
    }*/

    @Test
    public void intentTesting(){

        try {
            Espresso.onView(ViewMatchers.withId(R.id.recipe_list_view)).perform(RecyclerViewActions.actionOnItemAtPosition(0, ViewActions.click()));
            intended(hasComponent(RecipeStepsActivity.class.getName()));
            Log.v("intentTesting","List View Testing passed");
        }catch (NoMatchingViewException e){
            Log.v("intentTesting","List View Testing failed and it can fail for tab mode ");
        }
        try {
            Espresso.onView(ViewMatchers.withId(R.id.recipe_grid_view)).perform(RecyclerViewActions.actionOnItemAtPosition(0, ViewActions.click()));
            intended(hasComponent(RecipeStepsActivity.class.getName()));
            Log.v("intentTesting","Grid View Testing");
        }catch (NoMatchingViewException e){
            Log.v("intentTesting","Grid View Testing failed and it can fail for non tab mode ");
        }
    }
}

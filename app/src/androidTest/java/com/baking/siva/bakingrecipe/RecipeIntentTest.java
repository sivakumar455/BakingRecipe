package com.baking.siva.bakingrecipe;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.intent.Intents;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static android.support.test.espresso.intent.matcher.IntentMatchers.toPackage;


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
        //Intents.intending(not(isInternal())).respondWith();
        //Intents.intending( not(isInternal())).respondWith(new ActivityResult(Activity.RESULT_OK, null));
        Intent resultData = new Intent();
        String phoneNumber = "123-345-6789";
        resultData.putExtra("phone", phoneNumber);

        Intents.intending(toPackage("com.baking.siva.bakingrecipe")).respondWith(new Instrumentation.ActivityResult(Activity.RESULT_OK, resultData));
    }
    /*@Test
    public void intentVerificationTesting(){
        Intents.intended(toPackage("com.baking.siva.bakingrecipe"));
    }*/

    @Test
    public void intentStubTesting(){
       // Espresso.onData(anything()).inAdapterView(ViewMatchers.withId(R.id.recipe_list_view)).atPosition(1).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.recipe_list_view)).perform();
        Intents.intended(hasExtra(Intent.EXTRA_TEXT,"123-345-6789"));
        //Intents.intended(toPackage("com.baking.siva.bakingrecipe"));

    }

}

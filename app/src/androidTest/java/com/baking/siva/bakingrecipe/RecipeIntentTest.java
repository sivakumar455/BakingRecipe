package com.baking.siva.bakingrecipe;

import android.app.Activity;
import android.content.Intent;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.intent.Intents;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.app.Instrumentation.ActivityResult;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static android.support.test.espresso.intent.matcher.IntentMatchers.isInternal;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.AllOf.allOf;


/**
 * Created by sivakumarpadala on 07/04/18.
 */

@RunWith(AndroidJUnit4.class)
public class RecipeIntentTest {
    @Rule public IntentsTestRule<MainActivity> mMainActivity = new IntentsTestRule<>(MainActivity.class);

    @Before
    public void stubForAllIntents(){
        //Intents.intending(not(isInternal())).respondWith();
        Intents.intending(not(isInternal())).respondWith(new ActivityResult(Activity.RESULT_OK, null));
    }
    @Test
    public void intentVerificationTesting(){
        Intents.intended(allOf(
                hasAction(Intent.CATEGORY_HOME)));
    }

    @Test
    public void intentStubTesting(){
        Espresso.onData(anything()).inAdapterView(ViewMatchers.withId(R.id.recipe_list_view)).atPosition(1).perform(ViewActions.click());
    }

}

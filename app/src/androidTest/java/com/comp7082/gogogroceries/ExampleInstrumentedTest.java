package com.comp7082.gogogroceries;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import com.comp7082.gogogroceries.Views.AddFragment;
import com.comp7082.gogogroceries.Views.EditFragment;
import com.comp7082.gogogroceries.Views.HomeFragment;
import com.comp7082.gogogroceries.Views.MainActivity;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class ExampleInstrumentedTest {
    Fragment addFrag = new AddFragment();
    Fragment editFrag = new EditFragment();
    Fragment homeFrag = new HomeFragment();

    @Before
    public void setup() {
        addFrag = new AddFragment();
        addFrag.getActivity();
    }

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.comp7082.gogogroceries", appContext.getPackageName());
    }

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule
            = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void addItem(){
        ViewInteraction viewInt;

        onView(withId(R.id.addItemFAB)).perform(click());
        viewInt = onView(withId(R.id.etItemName)).perform(click());
        viewInt.perform(typeText("test name"));

        viewInt = onView(withId(R.id.etItemNotes)).perform(click());
        viewInt.perform(typeText("test notes"));
    }
}
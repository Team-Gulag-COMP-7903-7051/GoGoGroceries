package com.comp7082.gogogroceries;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.JMock1Matchers.equalTo;
import static org.hamcrest.Matchers.hasEntry;

import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.comp7082.gogogroceries.Models.Item;
import com.comp7082.gogogroceries.Views.MainActivity;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Map;

@RunWith(AndroidJUnit4.class)
public class UITest {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityTestRule = new ActivityScenarioRule<>(MainActivity.class);


    @Test
    public void UITest() {

        // Now that we have the stub in place, click on the button in our app that launches into the Camera
        //onView(withId(R.id.snap)).perform(click());

        onView(withId(R.id.bottomNavView)).perform(click());
        onView(withId(R.id.addItemFAB)).perform(click());
        onView(withId(R.id.etItemName)).perform(typeText("Orange Juice"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.confirmItemFAB)).perform(click());
        //onView(withId(R.id.tvItemName).matches("Orange Juice"));
        onData(allOf(is(instanceOf(Item.class)), hasEntry(equalTo("_name"), is("Orange Juice")))).perform(click());
//        onView(withId(R.id.btnPrev)).perform(click());
//        onView(withId(R.id.btnNext)).perform(click());
//        onView(withId(R.id.btnSearch)).perform(click());
//        onView(withId(R.id.etFromDateTime)).perform(replaceText("2021‐10‐00 00:00:00"), closeSoftKeyboard());
//        onView(withId(R.id.etToDateTime)).perform(replaceText("2021‐11‐01 00:00:00"), closeSoftKeyboard());
//        onView(withId(R.id.etKeywords)).perform(typeText("Sofa"), closeSoftKeyboard());
//        onView(withId(R.id.go)).perform(click());
//        onView(withId(R.id.etCaption)).check(matches(withText("Sofa")));
//        onView(withId(R.id.btnNext)).perform(click());
//        onView(withId(R.id.btnPrev)).perform(click());
    }

}

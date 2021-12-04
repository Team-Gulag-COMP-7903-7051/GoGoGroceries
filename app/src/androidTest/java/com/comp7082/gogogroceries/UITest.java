package com.comp7082.gogogroceries;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.instanceOf;

import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.BoundedMatcher;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.comp7082.gogogroceries.Models.Category;
import com.comp7082.gogogroceries.Models.Item;
import com.comp7082.gogogroceries.Views.MainActivity;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;

@RunWith(AndroidJUnit4.class)
public class UITest {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityTestRule = new ActivityScenarioRule<>(MainActivity.class);

    //Test for adding item to list
    @Test
    public void AddItem() {
        Item item = new Item(
                "Orange Juice",
                Category.MISCELLANEOUS,
                new Date(),
                true,
                "Juice"); //Item information to create

        //Perform UI operations to create Item
        onView(withId(R.id.bottomNavView)).perform(click());
        onView(withId(R.id.addItemFAB)).perform(click());
        onView(withId(R.id.etItemName)).perform(typeText(item.getName()), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.confirmItemFAB)).perform(click());

        //Check if Item is in list and if there, click on it
        onData(allOf(instanceOf(Item.class), itemContent(item))).perform(click());

    }

    //Test for checking existing item
    @Test
    public void existingItem() {
        Item existingitem = new Item(
                "Apple",
                Category.FRUIT,
                new Date(),
                true,
                "Gala"); //Existing default item information
        onData(allOf(instanceOf(Item.class), itemContent(existingitem))).perform(click()); //Check if Item is in list
    }

    //Matcher function for ItemsAdapter which contains custom objects
    public static Matcher<Object> itemContent(final Item expectedItem){
        return new BoundedMatcher<Object, Item>(Item.class){
            @Override
            public boolean matchesSafely(Item myItem) {
                return myItem.getName().equals(expectedItem.getName());
            }

            @Override
            public void describeTo(org.hamcrest.Description description) {
                description.appendText("with content '" + expectedItem.getName() +"'");
            }
        };
    }


}

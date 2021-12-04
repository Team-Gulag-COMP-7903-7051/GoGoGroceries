package com.comp7082.gogogroceries;

import org.junit.Assert;
import org.junit.Test;

import com.comp7082.gogogroceries.Models.Category;
import com.comp7082.gogogroceries.Models.Item;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class UnitTests {
    Item item = new Item("Kiwi", Category.FRUIT, new Date(), true, "Juice");

    @Test
    public void itemTodayDate_isCorrect() {
        Assert.assertEquals(item.getExpiryDate(), new Date());
    }

    @Test
    public void itemIsExpired() {
        boolean itemExpired;
        Date date = new GregorianCalendar(2021, Calendar.JULY, 01).getTime();
        Item expiredItem = new Item(
                "Kiwi",
                Category.FRUIT,
                date,
                true,
                "Juice");

        if(expiredItem.getExpiryDate().before(new Date())){
            itemExpired = true;
        } else {
            itemExpired = false;
        }
        Assert.assertTrue(itemExpired);
    }


}
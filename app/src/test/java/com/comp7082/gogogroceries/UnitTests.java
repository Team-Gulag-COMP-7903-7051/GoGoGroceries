package com.comp7082.gogogroceries;

import org.junit.Assert;
import org.junit.Test;

import com.comp7082.gogogroceries.Models.Category;
import com.comp7082.gogogroceries.Models.Item;
import com.comp7082.gogogroceries.Views.DateValidator;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class UnitTests {
    Item item = new Item("Kiwi", Category.FRUIT, new Date(), true, "Juice");

    @Test
    public void ItemTodayDate_isCorrect() {
        Assert.assertEquals(item.getExpiryDate(), new Date());
    }

    @Test
    public void ItemIsExpired() {
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

    @Test
    public void BoundaryValueDate1() {
        boolean validDate;
        String str_date = "30-Feb-20"; //Leap Year + Invalid Date
        if(DateValidator.isValid(str_date)) {
            validDate = true;
        } else {
            validDate = false;
        }
        Assert.assertFalse(validDate);
    }

    @Test
    public void BoundaryValueDate2() {
        boolean validDate;
        String str_date = "29-Feb-20"; //Leap Year
        if(DateValidator.isValid(str_date)) {
            validDate = true;
        } else {
            validDate = false;
        }
        Assert.assertTrue(validDate);
    }

    @Test
    public void BoundaryValueDate3() {
        boolean validDate;
        String str_date = "2A-February-EF"; //Invalid Date
        if(DateValidator.isValid(str_date)) {
            validDate = true;
        } else {
            validDate = false;
        }
        Assert.assertFalse(validDate);
    }
}
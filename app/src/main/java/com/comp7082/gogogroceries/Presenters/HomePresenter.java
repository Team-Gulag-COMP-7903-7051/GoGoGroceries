package com.comp7082.gogogroceries.Presenters;

import com.comp7082.gogogroceries.Models.Category;
import com.comp7082.gogogroceries.Models.Item;
import com.comp7082.gogogroceries.Models.UserData;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class HomePresenter {
    private final UserData _userData = UserData.getInstance();

    public UserData getUserData() {
        return _userData;
    }

    /**
     * FOR TESTING ONLY.
     */
    public void tempData() {

        String str_date = "20-Nov-21";
        DateFormat formatter;
        Date date = new Date();
        formatter = new SimpleDateFormat("dd-MMM-yy", Locale.getDefault());
        try {
            date = (Date) formatter.parse(str_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Item item1 = new Item("Apple",
                Category.FRUIT,
                new Date(),
                true,
                "Gala"
        );

        Item item2 = new Item("Banana",
                Category.FRUIT,
                date,
                false,
                "banoonoo"
        );

        Item item3 = new Item("Chocolate",
                Category.MISCELLANEOUS,
                date,
                true,
                "dark chocolate is best"
        );

        Item item4 = new Item("Chocolate Milk",
                Category.DAIRY,
                date,
                true,
                "choco > !choco"
        );

        Item item5 = new Item("Meat",
                Category.MEAT,
                date,
                false,
                "nuf said"
        );

        _userData.itemsList().add(item1);
        _userData.itemsList().add(item2);
        _userData.itemsList().add(item3);
        _userData.itemsList().add(item4);
        _userData.itemsList().add(item5);
    }
}

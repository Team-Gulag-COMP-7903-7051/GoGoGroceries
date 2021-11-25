package com.comp7082.gogogroceries;

import java.util.ArrayList;

/**
 * Singleton instance that contains user data.
 */
public class UserData {
    private static UserData instance = null;

    private ArrayList<Item> _items;

    private UserData() {
        _items = new ArrayList<>();

    }

    public static UserData getInstance() {
        if (instance == null) {
            instance = new UserData();
        }

        return instance;
    }

    public void setItemsList(ArrayList<Item> list) {
        _items = list;
    }

    public ArrayList<Item> itemsList() {
        return _items;
    }
}

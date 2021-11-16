package com.comp7082.gogogroceries;

import java.util.ArrayList;

/**
 * Singleton instance that contains user data.
 */
public class UserData {
    private static UserData instance = null;

    private final ArrayList<Item> _items;

    private UserData() {
        _items = new ArrayList<>();
        // TODO: Get data from phone storage if it exists, then add to the list.
    }

    public static UserData getInstance() {
        if (instance == null) {
            instance = new UserData();
        }

        return instance;
    }

    public ArrayList<Item> itemsList() {
        return _items;
    }
}

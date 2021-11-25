package com.comp7082.gogogroceries.Presenters;

import com.comp7082.gogogroceries.Models.Item;
import com.comp7082.gogogroceries.Models.UserData;

import java.util.Date;

public class EditPresenter {
    private final UserData _userData = UserData.getInstance();
    private Item _item; // Param1; The Item retrieved after deserializing params.
    private int _index; // Param2; The item's position in userData.itemsList();
    private Date _selectedDate; // The user selected date in the CalendarView

    public UserData getUserData() {
        return _userData;
    }

    public Item getItem() {
        return _item;
    }

    public int getIndex() {
        return _index;
    }

    public Date getSelectedDate() {
        return _selectedDate;
    }

    public void setItem(Item item) {
        _item = item;
    }

    public void setIndex(int i) {
        _index = i;
    }

    public void setSelectedDate(Date date) {
        _selectedDate = date;
    }
}

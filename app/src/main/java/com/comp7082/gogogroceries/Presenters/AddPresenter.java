package com.comp7082.gogogroceries.Presenters;

import com.comp7082.gogogroceries.Models.UserData;

import java.util.Date;

public class AddPresenter {
    private final UserData _userData = UserData.getInstance();
    private Date _selectedDate = new Date();

    public UserData getUserData() {
        return _userData;
    }

    public Date getSelectedDate() {
        return _selectedDate;
    }

    public void setSelectedDate(Date date) {
        _selectedDate = date;
    }
}

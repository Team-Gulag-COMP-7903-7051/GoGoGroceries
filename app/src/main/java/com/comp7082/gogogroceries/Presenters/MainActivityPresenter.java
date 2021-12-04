package com.comp7082.gogogroceries.Presenters;

import com.comp7082.gogogroceries.Models.UserData;

public class MainActivityPresenter {
    private final UserData _userData = UserData.getInstance();

    public UserData getUserData() {
        return _userData;
    }
}

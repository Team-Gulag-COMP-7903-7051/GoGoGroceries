package com.comp7082.gogogroceries.presenters;

import com.comp7082.gogogroceries.models.UserData;

public class MainActivityPresenter {
    private final UserData _userData = UserData.getInstance();

    public UserData getUserData() {
        return _userData;
    }
}

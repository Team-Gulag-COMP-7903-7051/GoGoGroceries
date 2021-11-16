package com.comp7082.gogogroceries;

import java.io.Serializable;
import java.util.Date;

public class Item implements Serializable {
    private String _name;
    private Category _category;
    private Date _expiryDate;
    private boolean _isRecurring;
    private String _note;

    public Item(String name, Category cat, Date expiryDate, boolean isRecurring, String note) {
        _name = name;
        _category = cat;
        _expiryDate = expiryDate;
        _isRecurring = isRecurring;
        _note = note;
    }

    public void setName(String newName) {
        _name = (newName.trim().isEmpty()) ? _name : newName;
    }

    public String getName() {
        return _name;
    }

    public void setCategory(Category newCat) {
        _category = newCat;
    }

    public Category getCategory() {
        return _category;
    }

    public void setExpiryDate(Date newExpiry) {
        _expiryDate = newExpiry;
    }

    public Date getExpiryDate() {
        return _expiryDate;
    }

//    public long getLongExpiryDate() {
//        long longDate = _expiryDate.getTime();
//        return _expiryDate;
//    }

    public void setIsRecurring(boolean isReoccurring) {
        _isRecurring = isReoccurring;
    }

    public boolean getIsRecurring() {
        return _isRecurring;
    }

    public void setNote(String newNote) {
        _note = newNote;
    }

    public String getNote() {
        return _note;
    }

}

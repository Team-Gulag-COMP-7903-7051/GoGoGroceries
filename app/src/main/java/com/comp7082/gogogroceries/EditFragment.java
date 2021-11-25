package com.comp7082.gogogroceries;

import android.os.Bundle;

import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Spinner;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    private static final String ARG_ITEM_KEY = "bundleItemKey";
    private static final String ARG_ITEM_INDEX = "bundleItemIndex";

    private final UserData _userData = UserData.getInstance();
    private Item _item; // Param1; The Item retrieved after deserializing params.
    private int _index; // Param2; The item's position in userData.itemsList();
    private Date _selectedDate; // The user selected date in the CalendarView

    public EditFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param item Parameter 1.
     * @param index Parameter 2.
     * @return A new instance of fragment EditFragment.
     */
    public static EditFragment newInstance(Serializable item, int index) {
        EditFragment fragment = new EditFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_ITEM_KEY, item);
        args.putInt(ARG_ITEM_INDEX, index);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = this.getArguments();
        
        if (bundle != null) {
            _item = (Item) bundle.getSerializable(ARG_ITEM_KEY);
            _index = bundle.getInt(ARG_ITEM_INDEX);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit, container, false);

        // Category Dropdown
        Spinner categorySpinner = view.findViewById(R.id.spinnerEditCategory);
        String[] categories = Category.names();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item, categories);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);
        categorySpinner.setOnItemSelectedListener(this);

        EditText itemName = view.findViewById(R.id.etItemName);
        SwitchCompat itemRecurring = view.findViewById(R.id.editIsReoccurringSwitch);
        EditText itemNote = view.findViewById(R.id.etItemNotes);
        CalendarView itemCalendar = view.findViewById(R.id.editExpiryDate);
        itemCalendar.setOnDateChangeListener((calendarView, year, month, day) -> {
            String strDate = String.format(Locale.getDefault(), "%d-%d-%d", day, month+1, year);
            DateFormat formatter;
            Date date = new Date();
            formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
            try {
                date = (Date) formatter.parse(strDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            _selectedDate = date;
        });

        categorySpinner.setSelection(_item.getCategory().ordinal());
        itemName.setText(_item.getName());
        itemCalendar.setDate(_item.getExpiryDate().getTime());
        itemRecurring.setChecked(_item.getIsRecurring());
        itemNote.setText(_item.getNote());

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();

        View view = getView();

        if (view == null) {
            Log.d("Error", "There was an problem retrieving the view for EditFragment");
            return;
        }

        Spinner categorySpinner = view.findViewById(R.id.spinnerEditCategory);
        Category itemCat = Category.values()[categorySpinner.getSelectedItemPosition()];
        EditText itemName = view.findViewById(R.id.etItemName);
        SwitchCompat itemRecurring = view.findViewById(R.id.editIsReoccurringSwitch);
        EditText itemNote = view.findViewById(R.id.etItemNotes);

        // Update Item values
        _item.setCategory(itemCat);
        _item.setName(itemName.getText().toString());
        _item.setIsRecurring(itemRecurring.isChecked());
        _item.setNote(itemNote.getText().toString());

        if (_selectedDate != null) {
            // Date was changed
            _item.setExpiryDate(_selectedDate);
        }

        _userData.itemsList().set(_index, _item);   // Replace existing item with new values
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
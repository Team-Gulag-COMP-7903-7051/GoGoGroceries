package com.comp7082.gogogroceries.Views;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import com.comp7082.gogogroceries.Models.Category;
import com.comp7082.gogogroceries.Models.Item;
import com.comp7082.gogogroceries.Models.UserData;
import com.comp7082.gogogroceries.Presenters.AddPresenter;
import com.comp7082.gogogroceries.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    private final AddPresenter _presenter = new AddPresenter();

    public AddFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit, container, false);

        // Add listener for change to date in CalendarView
        CalendarView itemCalendar = view.findViewById(R.id.editExpiryDate);
        itemCalendar.setOnDateChangeListener((calendarView, year, month, day) -> {
            String strDate = String.format(Locale.getDefault(), "%d-%d-%d", day, month+1, year);
            DateFormat formatter;
            Date date = new Date();
            formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
            try {
                date = formatter.parse(strDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            _presenter.setSelectedDate(date);
        });

        // Category Dropdown
        Spinner categorySpinner = view.findViewById(R.id.spinnerEditCategory);
        String[] categories = Category.names();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item, categories);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);
        categorySpinner.setOnItemSelectedListener(this);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        View view = getView();
        if (view == null) {
            Log.e("ERROR", "There was an problem retrieving the view for EditFragment");
            return;
        }

        EditText itemName = view.findViewById(R.id.etItemName);
        Spinner categorySpinner = view.findViewById(R.id.spinnerEditCategory);
        CalendarView itemCalendar = view.findViewById(R.id.editExpiryDate);
        SwitchCompat itemRecurring = view.findViewById(R.id.editIsReoccurringSwitch);
        EditText itemNote = view.findViewById(R.id.etItemNotes);

        itemName.setText("");
        categorySpinner.setSelection(0);
        itemCalendar.setDate(new Date().getTime());
        itemRecurring.setChecked(false);
        itemNote.setText("");
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    /**
     * Get data from the View and add it to UserData list.
     */
    public void addItem() {
        View view = getView();

        if (view == null) {
            Log.e("ERROR", "There was an problem retrieving the view for EditFragment");
            return;
        }

        EditText itemName = view.findViewById(R.id.etItemName);
        Spinner categorySpinner = view.findViewById(R.id.spinnerEditCategory);
        SwitchCompat itemRecurring = view.findViewById(R.id.editIsReoccurringSwitch);
        EditText itemNote = view.findViewById(R.id.etItemNotes);

        // Assumes data is already validated.
        Item item = new Item(
                itemName.getText().toString(),
                Category.values()[categorySpinner.getSelectedItemPosition()],
                _presenter.getSelectedDate(),
                itemRecurring.isChecked(),
                itemNote.getText().toString()
        );

        _presenter.getUserData().itemsList().add(item);
    }

    /**
     * Check if the data fields in the view have valid inputs.
     * @return true if all data is valid, else return false
     */
    public boolean isDataValid() {
        View view = getView();

        if (view == null) {
            Log.e("ERROR", "There was an problem retrieving the view for EditFragment");
            return false;
        }

        EditText itemName = view.findViewById(R.id.etItemName);

        // Data is invalid
        if (itemName.getText().toString().trim().isEmpty()) {
            Context context = getContext();
            Toast toast = Toast.makeText(context, R.string.add_item_name_error_msg, Toast.LENGTH_LONG);
            toast.show();
            return false;
        }

        return true;
    }
}
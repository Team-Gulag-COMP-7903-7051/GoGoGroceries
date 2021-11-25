package com.comp7082.gogogroceries;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    private final UserData _userData = UserData.getInstance();
    private ItemsAdapter _adapter;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // TODO: REMOVE ONCE TESTING IS COMPLETED
        if (_userData.itemsList().size() == 0) {
            tempData(); // Load test data into array list
        }

        ListView itemsListView = view.findViewById(R.id.lvItemsList);

        // Set behaviour for when item in the list is clicked
        itemsListView.setOnItemClickListener((adapterView, view1, pos, id) -> {
            Item item = (Item) adapterView.getItemAtPosition(pos);
            updateItemDetailView(view, item);
        });

        // On item LongClick, open dialog for Edit or Delete
        itemsListView.setOnItemLongClickListener((adapterView, view1, pos, id) -> {
            Item item = (Item) adapterView.getItemAtPosition(pos);
            showEditDeleteDialogOnLongClick(item, pos);

            return true;
        });

        // Display first item in the list by default
        if (_userData.itemsList().size() > 0) {
            updateItemDetailView(view, _userData.itemsList().get(0));
        }

        _adapter = new ItemsAdapter(getActivity(), _userData.itemsList());
        itemsListView.setAdapter(_adapter);

        // Inflate the layout for this fragment
        return view;
    }

    private void updateItemDetailView(View view, Item item) {
        TextView tvName = view.findViewById(R.id.tvItemNameDetail);
        TextView tvCat = view.findViewById(R.id.tvItemCategoryDetail);
        TextView tvExpiry = view.findViewById(R.id.tvItemExpiryDetail);
        TextView tvNote = view.findViewById(R.id.tvItemNoteDetail);
        TextView tvRecurring = view.findViewById(R.id.tvIsRecurringDetail);

        String expDate = item.getExpiryDate().toString();
        String expDateParsed = expDate.substring(0,10) + " "
                + expDate.substring(expDate.length() - 4);

        tvName.setText(item.getName());
        tvCat.setText(item.getCategory().toString());
        tvExpiry.setText(expDateParsed);
        tvNote.setText(item.getNote());

        String isReoccurringText = item.getIsRecurring() ?
                String.format(getString(R.string.recurring_item), "✅") :
                String.format(getString(R.string.recurring_item), "❌");

        tvRecurring.setText(isReoccurringText);
    }

    private void showEditDeleteDialogOnLongClick(Item item, int index) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Select");
        builder.setMessage("What would you like to do with " + item.getName() + "?");

        // Cancel Dialog
        builder.setNeutralButton("Cancel", null);

        // Launch "EditFragment"
        builder.setPositiveButton("Edit", (dialogInterface, i) -> {
            Fragment editFragment = EditFragment.newInstance(item, index);
            getParentFragmentManager().beginTransaction().replace(R.id.flFragment, editFragment).commit();
        });

        // Remove the item from the list
        builder.setNegativeButton("Delete", ((dialogInterface, i) -> {
            _userData.itemsList().remove(index);
            _adapter.notifyDataSetChanged();
        }));

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * FOR TESTING ONLY.
     */
    private void tempData() {

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
package com.comp7082.gogogroceries;

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
    private final UserData userData = UserData.getInstance();
//    private final ArrayList<Item> _items = new ArrayList<>();

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
        if (userData.itemsList().size() == 0) {
            tempData(); // Load test data into array list
        }

        ListView itemsListView = view.findViewById(R.id.lvItemsList);

        // Set behaviour for when item in the list is clicked
        itemsListView.setOnItemClickListener((adapterView, view1, pos, id) -> {
            Item item = (Item) adapterView.getItemAtPosition(pos);
            updateItemDetailView(view, item);
        });

        // OnLongClick, populate EditFragment with selected item info
        itemsListView.setOnItemLongClickListener((adapterView, view1, pos, id) -> {
//            Bundle result = new Bundle();
//            result.putSerializable("bundleItemKey", item);
//            result.putInt("bundleItemIndex", pos);
//            Fragment editFragment = new EditFragment();
//            editFragment.setArguments(result);

            Item item = (Item) adapterView.getItemAtPosition(pos);
            Fragment editFragment = EditFragment.newInstance(item, pos);
            getParentFragmentManager().beginTransaction().replace(R.id.flFragment, editFragment).commit();

            return true;
        });

        // Display first item in the list by default
        if (userData.itemsList().size() > 0) {
            updateItemDetailView(view, userData.itemsList().get(0));
        }

        ItemsAdapter adapter = new ItemsAdapter(getActivity(), userData.itemsList());
        itemsListView.setAdapter(adapter);

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
        String expDateParsed = expDate.substring(0,10) + " " + expDate.substring(expDate.length() - 4, expDate.length());

        tvName.setText(item.getName());
        tvCat.setText(item.getCategory().toString());
        tvExpiry.setText(expDateParsed);
        tvNote.setText(item.getNote());

        String isReoccurringText = item.getIsRecurring() ?
                String.format(getString(R.string.recurring_item), "✅") :
                String.format(getString(R.string.recurring_item), "❌");

        tvRecurring.setText(isReoccurringText);
    }

    /**
     * FOR TESTING ONLY.
     */
    private void tempData() {

        String str_date = "11-Nov-21";
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
                new Date(),
                false,
                "banoonoo"
        );

        Item item3 = new Item("Chocolate",
                Category.MISCELLANEOUS,
                new Date(),
                true,
                "dark chocolate is best"
        );

        Item item4 = new Item("Chocolate Milk",
                Category.DAIRY,
                new Date(),
                true,
                "choco > !choco"
        );

        Item item5 = new Item("Meat",
                Category.MEAT,
                date,
                false,
                "nuf said"
        );

        userData.itemsList().add(item1);
        userData.itemsList().add(item2);
        userData.itemsList().add(item3);
        userData.itemsList().add(item4);
        userData.itemsList().add(item5);
    }
}
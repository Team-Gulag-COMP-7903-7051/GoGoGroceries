package com.comp7082.gogogroceries;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private final ArrayList<Item> _items = new ArrayList<>();

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

        tempData(); // Load test data into array list
        ListView itemsListView = view.findViewById(R.id.lvItemsList);

        // Set behaviour for when item in the list is clicked
        itemsListView.setOnItemClickListener((adapterView, view1, pos, id) -> {
            Item item = (Item) adapterView.getItemAtPosition(pos);
            updateItemDetailView(view, item);
        });

        // Display first item in the list by default
        if (_items.size() > 0) {
            updateItemDetailView(view, _items.get(0));
        }

        ItemsAdapter adapter = new ItemsAdapter(getActivity(), _items);
        itemsListView.setAdapter(adapter);

        // Inflate the layout for this fragment
        return view;
    }

    private void updateItemDetailView(View view, Item item) {
        TextView tvName = view.findViewById(R.id.tvItemNameDetail);
        TextView tvCat = view.findViewById(R.id.tvItemCategoryDetail);
        TextView tvExpiry = view.findViewById(R.id.tvItemExpiryDetail);
        TextView tvNote = view.findViewById(R.id.tvItemNoteDetail);

        tvName.setText(item.getName());
        tvCat.setText(item.getCategory().toString());
        tvExpiry.setText(item.getExpiryDate().toString());
        tvNote.setText(item.getNote());
    }

    /**
     * FOR TESTING ONLY.
     */
    private void tempData() {
        Item item1 = new Item("Apple",
                Category.FRUIT,
                new Date(),
                false,
                "Gala"
        );

        Item item2 = new Item("Banana",
                Category.FRUIT,
                new Date(),
                true,
                "banoonoo"
        );

        _items.add(item1);
        _items.add(item2);
    }
}
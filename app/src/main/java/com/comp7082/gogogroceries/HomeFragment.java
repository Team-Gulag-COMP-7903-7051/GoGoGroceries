package com.comp7082.gogogroceries;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

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

        ItemsAdapter adapter = new ItemsAdapter(getActivity(), _items);
        itemsListView.setAdapter(adapter);

        // Inflate the layout for this fragment
        return view;
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
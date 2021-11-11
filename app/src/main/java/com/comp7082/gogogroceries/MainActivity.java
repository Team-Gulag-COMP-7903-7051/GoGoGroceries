package com.comp7082.gogogroceries;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    ListView itemsListView;
    ArrayList<Item> items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tempData(); // Load test data into array list
        itemsListView = findViewById(R.id.lvItemsList);
        ItemsAdapter adapter = new ItemsAdapter(this, items);

        itemsListView.setAdapter(adapter);
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

        items.add(item1);
        items.add(item2);
    }
}
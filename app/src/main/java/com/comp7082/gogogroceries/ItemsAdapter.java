package com.comp7082.gogogroceries;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ItemsAdapter extends ArrayAdapter<Item> {
    public ItemsAdapter(Context context, ArrayList<Item> items) {
        super(context, R.layout.list_item, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data for this position
        Item item = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item,
                                                                    parent, false);
        }

        // Lookup view for data population
        TextView tvName = convertView.findViewById(R.id.tvItemName);
        TextView tvCat = convertView.findViewById(R.id.tvItemCategory);
//        TextView tvExpiry = convertView.findViewById(R.id.tvItemExpiry);
//        TextView tvNote = convertView.findViewById(R.id.tvItemNote);

        // Insert data into the template view using the data object
        tvName.setText(item.getName());
        tvCat.setText(item.getCategory().toString());
//        tvExpiry.setText(item.getExpiryDate().toString());
//        tvNote.setText(item.getNote());

        // Return the completed view
        return convertView;
    }
}

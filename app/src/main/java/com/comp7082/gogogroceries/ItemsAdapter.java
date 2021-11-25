package com.comp7082.gogogroceries;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

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

        // Insert data into the template view using the data object
        tvName.setText(item.getName());
        tvCat.setText(item.getCategory().toString());

        if (new Date().after(item.getExpiryDate())) {
            Log.d("---name", item.getName());
            Log.d("---cat", item.getCategory().toString());
            tvName.setBackgroundColor(Color.parseColor("#45FF0000"));
            tvCat.setBackgroundColor(Color.parseColor("#45FF0000"));
        }

        // Return the completed view
        return convertView;
    }
}

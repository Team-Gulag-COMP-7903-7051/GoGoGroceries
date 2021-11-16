package com.comp7082.gogogroceries;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentViewHolder;

import java.util.ArrayList;
import java.util.List;

public class ItemsAdapter extends ArrayAdapter<Item>{

    ArrayList<Item> list;
    public class ViewHolder{
        TextView itemName;
        TextView itemCategory;
        Button b1;
    }

    public ItemsAdapter(Context context, ArrayList<Item> _items) {
        super(context, 0, _items);
        this.list = _items;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        // Get the data for this position
        final Item item = getItem(position);
        ViewHolder viewHolder;
        viewHolder = new ViewHolder();

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item,
                                                                    parent, false);
            // Lookup view for data population
//            TextView tvName = convertView.findViewById(R.id.tvItemName);
//            TextView tvCat = convertView.findViewById(R.id.tvItemCategory);

            viewHolder.itemName = convertView.findViewById(R.id.tvItemName);
            viewHolder.itemCategory = convertView.findViewById(R.id.tvItemCategory);
            viewHolder.b1 = convertView.findViewById(R.id.delete);

        } else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }


//        TextView tvExpiry = convertView.findViewById(R.id.tvItemExpiry);
//        TextView tvNote = convertView.findViewById(R.id.tvItemNote);

        // Insert data into the template view using the data object
        viewHolder.itemName.setText(item.getName());
        viewHolder.itemCategory.setText(item.getCategory().toString());
//        tvExpiry.setText(item.getExpiryDate().toString());
//        tvNote.setText(item.getNote());

        if (item.hashCode() == 1){
            viewHolder.b1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(view.getContext(),"" + getPosition(item),Toast.LENGTH_SHORT).show();
                    list.remove(position);
                }
            });
            notifyDataSetChanged();
        }
        // Return the completed view
        return convertView;

    }


}

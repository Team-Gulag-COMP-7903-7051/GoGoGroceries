package com.comp7082.gogogroceries.Views;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.comp7082.gogogroceries.Models.Category;
import com.comp7082.gogogroceries.Models.Item;
import com.comp7082.gogogroceries.Models.UserData;
import com.comp7082.gogogroceries.Presenters.HomePresenter;
import com.comp7082.gogogroceries.Presenters.ItemsAdapter;
import com.comp7082.gogogroceries.R;
import com.comp7082.gogogroceries.Views.EditFragment;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    private final HomePresenter _presenter = new HomePresenter();
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
        if (_presenter.getUserData().itemsList().size() == 0) {
            _presenter.tempData(); // Load test data into array list
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
        if (_presenter.getUserData().itemsList().size() > 0) {
            updateItemDetailView(view, _presenter.getUserData().itemsList().get(0));
        }

        _adapter = new ItemsAdapter(getActivity(), _presenter.getUserData().itemsList());
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
            _presenter.getUserData().itemsList().remove(index);
            _adapter.notifyDataSetChanged();
        }));

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
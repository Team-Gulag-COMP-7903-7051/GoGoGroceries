package com.comp7082.gogogroceries;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{
    public UserData userData = UserData.getInstance();
    private HomeFragment _homeFragment;

    private ListView listViewItems;
    private ArrayList<String> items;
    private ArrayAdapter adapter;
    private Button addItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //delete list item
        listViewItems = (ListView)findViewById(R.id.list_item);
        addItem = (Button)findViewById(R.id.addItemFAB);

        items = new ArrayList<String>();

        adapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1,items);
        listViewItems.setAdapter(adapter);

        // Fragments
        _homeFragment = new HomeFragment();
        AddFragment addFrag = new AddFragment();
        BottomNavigationView _bottomNavView = findViewById(R.id.bottomNavView);

        _bottomNavView.setOnNavigationItemSelectedListener(this);
        _bottomNavView.setSelectedItemId(R.id.home);

        FloatingActionButton addItemFAB = findViewById(R.id.addItemFAB);
        FloatingActionButton confirmItemFAB = findViewById(R.id.confirmItemFAB);

        // Add Item FAB onClick event
        addItemFAB.setOnClickListener(v -> {
            getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, addFrag).commit();

            // Swap Visibility of FABs
            addItemFAB.setVisibility(View.GONE);
            confirmItemFAB.setVisibility(View.VISIBLE);
        });

        // FAB onClick event for Confirming Item
        confirmItemFAB.setOnClickListener(v -> {
            if (addFrag.isDataValid()) {
                addFrag.addItem();
                getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, _homeFragment).commit();
            }

            // Swap Visibility of FABs
            confirmItemFAB.setVisibility(View.GONE);
            addItemFAB.setVisibility(View.VISIBLE);
        });
    }

    public void replaceFragments(Fragment fragmentClass, Bundle savedInstanceState) {
        Fragment fragment = null;
        try {
            //fragment = (Fragment) fragmentClass.onCreate(savedInstanceState);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flFragment, fragment).commit();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        final int home = R.id.home;

        // Change to Switch statement if adding more navigation items
        if (item.getItemId() == home) {
            getSupportFragmentManager().beginTransaction().
                    replace(R.id.flFragment, _homeFragment).commit();
            findViewById(R.id.confirmItemFAB).setVisibility(View.GONE);
            findViewById(R.id.addItemFAB).setVisibility(View.VISIBLE);
            return true;
        }
        return false;
    }
    
}
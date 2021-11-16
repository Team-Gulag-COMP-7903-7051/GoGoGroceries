package com.comp7082.gogogroceries;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{
    private final UserData _userData = UserData.getInstance();
    private HomeFragment _homeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Try to get data from internal storage
        try {
            FileInputStream fileIS = getBaseContext().openFileInput("Items");
            ObjectInputStream objIS = new ObjectInputStream(fileIS);
            ArrayList<Item> items = (ArrayList<Item>) objIS.readObject();
            _userData.setItemsList(items);
            fileIS.close();
            objIS.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }


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

    @Override
    protected void onPause() {
        super.onPause();
        // Try to save data to internal storage
        try {
            FileOutputStream fileOS = getBaseContext().openFileOutput("Items", Context.MODE_PRIVATE);
            ObjectOutputStream objOS = new ObjectOutputStream(fileOS);
            objOS.writeObject(_userData.itemsList());
            fileOS.close();
            objOS.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
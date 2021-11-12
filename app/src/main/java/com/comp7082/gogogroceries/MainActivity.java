package com.comp7082.gogogroceries;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{
    private final ArrayList<Item> _items = new ArrayList<>();
    private HomeFragment _homeFragment;
    private BottomNavigationView _bottomNavView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _homeFragment = new HomeFragment();
        _bottomNavView = findViewById(R.id.bottomNavView);

        _bottomNavView.setOnNavigationItemSelectedListener(this);
        _bottomNavView.setSelectedItemId(R.id.home);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        final int home = R.id.home;

        if (item.getItemId() == home) {
            getSupportFragmentManager().beginTransaction().
                    replace(R.id.flFragment, _homeFragment).commit();
            return true;
        }
        return false;
    }
}
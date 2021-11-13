package com.comp7082.gogogroceries;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{
    private HomeFragment _homeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Fragments
        _homeFragment = new HomeFragment();
        EditFragment editFrag = new EditFragment();
        BottomNavigationView _bottomNavView = findViewById(R.id.bottomNavView);

        _bottomNavView.setOnNavigationItemSelectedListener(this);
        _bottomNavView.setSelectedItemId(R.id.home);

        // FAB onClick event
        FloatingActionButton addItemFAB = findViewById(R.id.addItemFAB);
        addItemFAB.setOnClickListener(v -> {
            getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, editFrag).commit();
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        final int home = R.id.home;

        // Change to Switch statement if adding more navigation items
        if (item.getItemId() == home) {
            getSupportFragmentManager().beginTransaction().
                    replace(R.id.flFragment, _homeFragment).commit();
            return true;
        }
        return false;
    }
}
package com.comp7082.gogogroceries.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.comp7082.gogogroceries.models.Item;
import com.comp7082.gogogroceries.presenters.MainActivityPresenter;
import com.comp7082.gogogroceries.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;

@SuppressWarnings("deprecation")
public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{
    private final MainActivityPresenter _presenter = new MainActivityPresenter();
    private HomeFragment _homeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createNotificationChannel(); // "You should execute this code as soon as your app starts" - Sun Tzu, probably
        setContentView(R.layout.activity_main);

        // Try to get data from internal storage
        try {
            FileInputStream fileIS = getBaseContext().openFileInput("Items");
            ObjectInputStream objIS = new ObjectInputStream(fileIS);
            @SuppressWarnings("unchecked") ArrayList<Item> items = (ArrayList<Item>) objIS.readObject();
            _presenter.getUserData().setItemsList(items);
            fileIS.close();
            objIS.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        String expiredItems = getExpiredItems();
        if (!expiredItems.equals("")){
            // Build notification content
            Notification notification = new NotificationCompat.Builder(this, "CHANNEL_ID")
                    .setSmallIcon(R.drawable.ic_home_foreground)
                    .setContentTitle("The following items have expired!")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setStyle(new NotificationCompat.BigTextStyle()
                            .bigText(expiredItems))
                    .build();

            // Show notification
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
            // notificationId is a unique int for each notification that you must define
            notificationManager.notify(1, notification);
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
            objOS.writeObject(_presenter.getUserData().itemsList());
            fileOS.close();
            objOS.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Build notification channel
    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        CharSequence name = "name";
        String description = "description";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel("CHANNEL_ID", name, importance);
        channel.setDescription(description);
        // Register the channel with the system; you can't change the importance
        // or other notification behaviors after this
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }
    
    private String getExpiredItems() {
        Date currDate = new Date();
        StringBuilder tempStr = new StringBuilder();
        for (Item item : _presenter.getUserData().itemsList()) {
            if (currDate.getTime() >= item.getExpiryDate().getTime()) {
                tempStr.append(item.getName()).append(",\n");
            }
        }
        // remove last 3 characters from string
        int length = tempStr.length();
        if(length > 2){
            tempStr.setLength(length - 3);
        }

        return tempStr.toString();
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
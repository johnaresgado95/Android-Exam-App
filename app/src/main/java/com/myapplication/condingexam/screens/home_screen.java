package com.myapplication.condingexam.screens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.myapplication.condingexam.R;

public class home_screen extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new homefragment_screen()).commit();
    }

    private final BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @SuppressLint("NonConstantResourceId")
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment = null;

                    switch (menuItem.getItemId()) {
                        case R.id.mHome:
                            selectedFragment = new homefragment_screen();
                            break;
                        case R.id.mCart:
                            selectedFragment = new cartfragment_screen();
                            break;
                        case R.id.mNotification:
                            selectedFragment = new notificationfragment_screen();
                            break;
                        case R.id.mProfile:
                            selectedFragment = new profilefragment_screen();
                            break;
                    }

                    assert selectedFragment != null;

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                    return true;

                }
            };

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
    }
}
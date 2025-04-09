package com.example.smtuapp.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smtuapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public abstract class BaseActivity extends AppCompatActivity {

    protected BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void setupBottomNavigation(int activeItemId) {
        bottomNavigationView = findViewById(R.id.bottomNavigation);

        // Установим активный пункт
        bottomNavigationView.setSelectedItemId(activeItemId);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_schedule && activeItemId != R.id.nav_schedule) {
                startActivity(new Intent(this, ScheduleActivity.class));
                overridePendingTransition(0, 0);
                return true;
            } else if (itemId == R.id.nav_navigation && activeItemId != R.id.nav_navigation) {
                startActivity(new Intent(this, NavigationActivity.class));
                overridePendingTransition(0, 0);
                return true;
            } else if (itemId == R.id.nav_isu && activeItemId != R.id.nav_isu) {
//                startActivity(new Intent(this, IsuActivity.class));
//                overridePendingTransition(0, 0);
                return true;
            } else if (itemId == R.id.nav_services && activeItemId != R.id.nav_services) {
//                startActivity(new Intent(this, ServicesActivity.class));
//                overridePendingTransition(0, 0);
                return true;
            }
            return true;
        });
    }
}

package com.example.smtuapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smtuapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ServicesActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);

        bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_services);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.nav_schedule) {
                Intent scheduleIntent = new Intent(ServicesActivity.this, ScheduleActivity.class);
                scheduleIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(scheduleIntent);
                finish();
                return true;
            } else if (item.getItemId() == R.id.nav_navigation) {
                Intent navigationIntent = new Intent(ServicesActivity.this, NavigationActivity.class);
                navigationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(navigationIntent);
                finish();
                return true;
            } else if (item.getItemId() == R.id.nav_isu) {
                Intent isuIntent = new Intent(ServicesActivity.this, ISUActivity.class);
                isuIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(isuIntent);
                finish();
                return true;
            } else if (item.getItemId() == R.id.nav_services) {
                Toast.makeText(this, "Вы уже на этом экране", Toast.LENGTH_SHORT).show();
                return true;
            }
            return false;
        });
    }
}

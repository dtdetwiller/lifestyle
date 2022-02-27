package com.example.lifestyle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class DashBoard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

        // BMI fragment
        DashboardBMIFragment dashboardBMIFragment = new DashboardBMIFragment();
        DashboardHikesFragment dashboardHikesFragment = new DashboardHikesFragment();
        DashboardFitnessGoalsFragment dashboardFitnessGoalsFragment = new DashboardFitnessGoalsFragment();
        DashboardWeatherFragment dashboardWeatherFragment = new DashboardWeatherFragment();

        // Replace the bmi fragment container
        FragmentTransaction fTrans = getSupportFragmentManager().beginTransaction();
        fTrans.replace(R.id.fl_frag_bmi, dashboardBMIFragment, "frag_bmi");
        fTrans.replace(R.id.fl_frag_hikes, dashboardHikesFragment, "frag_hikes");
        fTrans.replace(R.id.fl_frag_fitness_goals, dashboardFitnessGoalsFragment, "frag_fitness_goals");
        fTrans.replace(R.id.fl_frag_weather, dashboardWeatherFragment, "frag_weather");
        fTrans.commit();


        // Initialize and Assign Variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        // set home selected
        bottomNavigationView.setSelectedItemId(R.id.dashboard);

        // perform ItemSelectedListener
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()) {
                    case R.id.dashboard:
                        return true;
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext(), Profile.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }
}
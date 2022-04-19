package com.example.lifestyle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;

import com.example.lifestyle.model.ProfileViewModel;
import com.example.lifestyle.profilefragments.ProfileFragment;
import com.example.lifestyle.profilefragments.ProfilePageFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class Profile extends AppCompatActivity {

    public ProfileViewModel profileViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);

        setContentView(R.layout.activity_profile);

        // Initialize and Assign Variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        // set home selected
        bottomNavigationView.setSelectedItemId(R.id.profile);

        // perform ItemSelectedListener
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()) {
                    case R.id.dashboard:
                        startActivity(new Intent(getApplicationContext(), DashBoard.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.profile:
                        return true;
                }
                return false;
            }
        });

        ProfilePageFragment ProfilePageFragment = new ProfilePageFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction().replace(R.id.fragment_container, ProfilePageFragment).commit();

    }

//    final Observer<WeatherData> profileObserver = new Observer<ProfileData>() {
//        @Override
//        public void onChanged(@Nullable final ProfileData profileData) {
//
//        }
//
//        @Override
//        public void onChanged(@Nullable final WeatherData weatherData) {
//            //update UI when new weather data is collected
//            if (weatherData != null){
//                receiveWeatherData(weatherData);
//            }
//        }
//    };

    public void pageToProfile() {
        Fragment ProfileFragment = new ProfileFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, ProfileFragment).commit();
    }
    public void profileToPage() {
        Fragment ProfilePageFragment = new ProfilePageFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, ProfilePageFragment).commit();
    }


}
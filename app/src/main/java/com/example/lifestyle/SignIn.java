package com.example.lifestyle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.lifestyle.homefragments.HomeFragment;
import com.example.lifestyle.model.ProfileViewModel;
import com.example.lifestyle.model.WeatherViewModel;
import com.example.lifestyle.signinfragments.SignInFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class SignIn extends AppCompatActivity
{
    private WeatherViewModel weatherViewModel;
    private ProfileViewModel profileViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        SignInFragment signInFragment = new SignInFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_container, signInFragment).commit();
        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        weatherViewModel = new ViewModelProvider(this).get(WeatherViewModel.class);
    }
}
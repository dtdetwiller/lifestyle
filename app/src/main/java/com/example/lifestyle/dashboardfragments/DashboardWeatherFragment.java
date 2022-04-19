package com.example.lifestyle.dashboardfragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.lifestyle.R;
import com.example.lifestyle.dashboardfragments.weather.DisplayWeatherFragment;
import com.example.lifestyle.dashboardfragments.weather.WeatherData;
import com.example.lifestyle.model.ProfileViewModel;
import com.example.lifestyle.model.WeatherViewModel;
import com.example.lifestyle.profilefragments.ProfileData;

public class DashboardWeatherFragment extends Fragment {

    private Button weatherButton;
    public WeatherViewModel weatherViewModel;
    public DisplayWeatherFragment displayWeatherFragment = new DisplayWeatherFragment();
    private ProfileViewModel profileViewModel;
    private ProfileData profileData;

    public DashboardWeatherFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard_weather, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //create the view model
        weatherViewModel = new ViewModelProvider(this).get(WeatherViewModel.class);

        // Get the profile data
        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        profileData = profileViewModel.readProfile(getActivity());

        // Retrieve the cit and country from ProfileData and set the location on the weather view model.
        String cityCountry = profileData.city + "," + profileData.country;
        String location = cityCountry.replace(" ", "%20");

        weatherViewModel.setLocation(location);

        weatherButton = getView().findViewById(R.id.weather_button);

        weatherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(profileData.city != null){
                    weatherViewModel.updateWeatherData();
                    FragmentTransaction fTrans = getParentFragmentManager().beginTransaction();
                    fTrans.replace(R.id.fl_frag_dashboard, displayWeatherFragment);
                    fTrans.commit();
                }

                else {
                    Toast.makeText(getActivity(), "Create a profile first!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
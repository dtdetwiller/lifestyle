package com.example.lifestyle.dashboardfragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.lifestyle.R;
import com.example.lifestyle.dashboardfragments.weather.DisplayWeatherFragment;
import com.example.lifestyle.dashboardfragments.weather.WeatherData;
import com.example.lifestyle.model.WeatherViewModel;

public class DashboardWeatherFragment extends Fragment {

    private Button weatherButton;
    public WeatherViewModel weatherViewModel;
    public DisplayWeatherFragment displayWeatherFragment = new DisplayWeatherFragment();
    ;

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

        weatherButton = getView().findViewById(R.id.weather_button);

        weatherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fTrans = getParentFragmentManager().beginTransaction();
                fTrans.replace(R.id.fl_frag_dashboard, displayWeatherFragment);
                fTrans.commit();
            }
        });
    }
}
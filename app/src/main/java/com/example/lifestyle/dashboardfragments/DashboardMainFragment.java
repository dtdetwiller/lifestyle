package com.example.lifestyle.dashboardfragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lifestyle.DashBoard;
import com.example.lifestyle.R;

public class DashboardMainFragment extends Fragment {

    public DashboardMainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // BMI fragment
        DashboardBMIFragment dashboardBMIFragment = new DashboardBMIFragment();
        DashboardHikesFragment dashboardHikesFragment = new DashboardHikesFragment();
        DashboardFitnessGoalsFragment dashboardFitnessGoalsFragment = new DashboardFitnessGoalsFragment();
        DashboardWeatherFragment dashboardWeatherFragment = new DashboardWeatherFragment();

        // Replace the bmi fragment container
        FragmentTransaction fTrans = getParentFragmentManager().beginTransaction();
        fTrans.replace(R.id.fl_frag_bmi, dashboardBMIFragment, "frag_bmi");
        fTrans.replace(R.id.fl_frag_hikes, dashboardHikesFragment, "frag_hikes");
        fTrans.replace(R.id.fl_frag_fitness_goals, dashboardFitnessGoalsFragment, "frag_fitness_goals");
        fTrans.replace(R.id.fl_frag_weather, dashboardWeatherFragment, "frag_weather");
        fTrans.commit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}
package com.example.lifestyle.dashboardfragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.lifestyle.DashBoard;
import com.example.lifestyle.MainActivity;
import com.example.lifestyle.R;

public class DashboardWeatherFragment extends Fragment {

    private Button weatherButton;
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
        weatherButton = getView().findViewById(R.id.weather_button);

        weatherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Uri weatherSearch = Uri.parse("https://www.google.com/search?q=weather+near+me");
//                //Create implicit intent
//                Intent mapIntent = new Intent(Intent.ACTION_VIEW, weatherSearch);
//                startActivity(mapIntent);

                ((DashBoard)getActivity()).dashboardWeatherToDisplayWeather();

            }
        });
    }


}
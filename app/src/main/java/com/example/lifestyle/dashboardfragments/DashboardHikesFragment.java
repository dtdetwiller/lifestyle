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

import com.example.lifestyle.R;

public class DashboardHikesFragment extends Fragment {

    private Button hikeButton;

    public DashboardHikesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard_hikes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        hikeButton = getView().findViewById(R.id.hike_button);

        //Open Google Maps for nearby hikes
        hikeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri hikeSearch = Uri.parse("geo:40.753977,-111.88172?q=Hikes");

                //Create implicit intent
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, hikeSearch);

                //Fire intent
                startActivity(mapIntent);

            }
        });
    }
}
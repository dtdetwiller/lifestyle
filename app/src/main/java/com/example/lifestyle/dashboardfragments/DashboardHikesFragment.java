package com.example.lifestyle.dashboardfragments;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.lifestyle.R;
import com.example.lifestyle.model.ProfileViewModel;
import com.example.lifestyle.profilefragments.ProfileData;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class DashboardHikesFragment extends Fragment {

    private Button hikeButton;

    private ProfileViewModel profileViewModel;
    private ProfileData profileData;

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

        profileViewModel = new ProfileViewModel(this.getActivity().getApplication());
        profileData = profileViewModel.readProfile(this.getActivity());

        hikeButton = getView().findViewById(R.id.hike_button);

        //Open Google Maps for nearby hikes
        hikeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(profileData.city != null){
                    // Retrieve the cit and country from ProfileData and set the location on the weather view model.
                    String location = profileData.city + "," + profileData.country;

                    double lat = 0;
                    double lon = 0;

                    Geocoder gc = new Geocoder(getContext(), Locale.getDefault());
                    try {
                        List<Address> addresses = gc.getFromLocationName(location, 2);
                        Address address = addresses.get(0);
                        lat = address.getLatitude();
                        lon = address.getLongitude();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    if(lat == 0 || lon ==0){
                        Toast.makeText(getActivity(), "Entered profile location is invalid!", Toast.LENGTH_SHORT).show();
                    }

                    else{
                        Uri hikeSearch = Uri.parse("geo:" + Double.toString(lat) + "," + Double.toString(lon) + "?q=Hikes");

                        //Create implicit intent
                        Intent mapIntent = new Intent(Intent.ACTION_VIEW, hikeSearch);

                        //Fire intent
                        startActivity(mapIntent);
                    }
                }

                else{
                   Toast.makeText(getActivity(), "Create a profile first!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
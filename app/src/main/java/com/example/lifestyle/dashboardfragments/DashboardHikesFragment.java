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
import android.widget.Toast;

import com.example.lifestyle.R;
import com.example.lifestyle.model.ProfileViewModel;
import com.example.lifestyle.profilefragments.ProfileData;

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
                // if(profileData.city != null){
                    // Retrieve the cit and country from ProfileData and set the location on the weather view model.
                    String cityCountry = profileData.city + "," + profileData.country;
                    String location = cityCountry.replace(" ", "%20");

                    Uri hikeSearch = Uri.parse("geo:40.753977,-111.88172?q=Hikes");

                    //Create implicit intent
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, hikeSearch);

                    //Fire intent
                    startActivity(mapIntent);
                //}

                //else{
                   // Toast.makeText(getActivity(), "Create a profile first!", Toast.LENGTH_SHORT).show();
                // }
            }
        });
    }
}
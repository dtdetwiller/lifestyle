package com.example.lifestyle.dashboardfragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lifestyle.R;
import com.example.lifestyle.model.ProfileViewModel;
import com.example.lifestyle.profilefragments.ProfileData;

import java.io.File;
import java.util.Scanner;

public class DashboardBMIFragment extends Fragment {

    private Button bmi_button;
    private TextView bmi_text;

    private ProfileViewModel profileViewModel;
    private ProfileData profileData;


    public DashboardBMIFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_dashboard_b_m_i, container,false);

        return fragmentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        profileViewModel = new ProfileViewModel(this.getActivity().getApplication());
        profileData = profileViewModel.readProfile(this.getActivity());

        bmi_text = getView().findViewById(R.id.bmi_text);

        bmi_button = getView().findViewById(R.id.bmi_button);
        bmi_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(profileData.weight != null){
                    int height_feet = 0;
                    int height_inches = 0;;
                    int weight_lbs = 0;
                    double BMI = 0;

                    if(profileData.heightFeet != null)
                        height_feet = Integer.parseInt(profileData.heightFeet);
                    if(profileData.heightInches != null)
                        height_inches = Integer.parseInt(profileData.heightInches);;
                    if(profileData.weight != null)
                        weight_lbs = Integer.parseInt(profileData.weight);


                    BMI = calculateBMI(height_feet, height_inches, weight_lbs);
                    bmi_text.setText("BMI: " + String.format("%.1f", BMI));
                }

                else{
                    Toast.makeText(getActivity(), "Create a profile first!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * Calculates and returns the BMI
     */
    //made public for junit testing
    public double calculateBMI(int feet, int inches, int weight) {

        int total_inches = (feet * 12) + inches;
        double meters = total_inches / 39.37;
        double kg = weight / 2.205;

        return kg / Math.pow(meters, 2);
    }
}
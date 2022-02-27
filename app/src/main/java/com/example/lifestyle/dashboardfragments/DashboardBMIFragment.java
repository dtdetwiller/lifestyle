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

import com.example.lifestyle.R;

import java.io.File;
import java.util.Scanner;

public class DashboardBMIFragment extends Fragment {

    private Button bmi_button;
    private TextView bmi_text;


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

        bmi_text = getView().findViewById(R.id.bmi_text);

        bmi_button = getView().findViewById(R.id.bmi_button);
        bmi_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File profileFile = new File(getActivity().getFilesDir(), "Profile");

                int height_feet = 0;
                int height_inches = 0;
                int weight_lbs = 0;
                double BMI = 0;

                if (profileFile.exists()) {

                    try {

                        Scanner scnr = new Scanner(profileFile);

                        while (scnr.hasNextLine()) {
                            String line = scnr.nextLine();
                            String[] tokens = line.split(" ");

                            if (tokens[0].equals("height_feet"))
                                height_feet = Integer.parseInt(tokens[1]);
                            else if (tokens[0].equals("height_inches"))
                                height_inches = Integer.parseInt(tokens[1]);
                            else if (tokens[0].equals("weight"))
                                weight_lbs = Integer.parseInt(tokens[1]);
                        }
                    }
                    catch (Exception e) {
                        System.out.println("There was an error tring to read the Profile file.");
                    }

                    BMI = calculateBMI(height_feet, height_inches, weight_lbs);
                    bmi_text.setText("BMI: " + String.format("%.1f", BMI));

                }

            }
        });
    }

    /**
     * Calculates and returns the BMI
     */
    private double calculateBMI(int feet, int inches, int weight) {

        int total_inches = (feet * 12) + inches;
        double meters = total_inches / 39.37;
        double kg = weight / 2.205;

        return kg / Math.pow(meters, 2);
    }
}
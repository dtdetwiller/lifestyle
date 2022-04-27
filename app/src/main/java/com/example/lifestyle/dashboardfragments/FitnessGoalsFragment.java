package com.example.lifestyle.dashboardfragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.example.lifestyle.Profile;
import com.example.lifestyle.R;
import com.example.lifestyle.MainActivity;
import com.example.lifestyle.model.ProfileViewModel;
import com.example.lifestyle.profilefragments.ProfileData;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FitnessGoalsFragment extends Fragment {

    private Button submitButton;
    private Button backButton;
    private Spinner genderSpinner;
    private Spinner feetSpinner;
    private Spinner inchesSpinner;
    private Spinner weightSpinner;
    private Spinner ageSpinner;
    private RadioGroup activityRadioGroup;
    private RadioButton activeRadioButton;
    private RadioButton sedentaryRadioButton;
    private RadioGroup weightGoalRadioGroup;
    private RadioButton loseRadioButton;
    private RadioButton gainRadioButton;
    private RadioButton maintainRadioButton;
    private Spinner poundsPerWeekSpinner;

    private ProfileViewModel profileViewModel;
    private ProfileData profileData;


    public FitnessGoalsFragment() {
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
        return inflater.inflate(R.layout.fragment_fitness_goals, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        profileViewModel = new ProfileViewModel(this.getActivity().getApplication());
        profileData = profileViewModel.readProfile(this.getActivity());

        SetUpForm(view);

        submitButton = view.findViewById(R.id.fg_submit_button);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                profileData.gender = genderSpinner.getSelectedItem().toString();
                profileData.heightFeet = feetSpinner.getSelectedItem().toString();
                profileData.heightInches = inchesSpinner.getSelectedItem().toString();
                profileData.weight = weightSpinner.getSelectedItem().toString();
                profileData.age = ageSpinner.getSelectedItem().toString();
                profileData.poundsPerWeek = poundsPerWeekSpinner.getSelectedItem().toString();

                if (activeRadioButton.isChecked())
                    profileData.activityLevel = "Active";
                else if (sedentaryRadioButton.isChecked())
                    profileData.activityLevel = "Sedentary";
                else
                    profileData.activityLevel = "";

                if (loseRadioButton.isChecked())
                    profileData.weightGoal = "Lose";
                else if (gainRadioButton.isChecked())
                    profileData.weightGoal = "Gain";
                else if (maintainRadioButton.isChecked())
                    profileData.weightGoal = "Maintain";
                else
                    profileData.weightGoal = "";

                //SaveFile();
                profileViewModel.writeProfile(profileData);

                BackToDashboard();
            }
        });

        backButton = view.findViewById(R.id.fg_back_button);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BackToDashboard();
            }
        });

    }

    private void SetUpForm(View view) {

        genderSpinner = view.findViewById(R.id.fg_gender_select);
        feetSpinner = view.findViewById(R.id.fg_height_feet);
        inchesSpinner = view.findViewById(R.id.fg_height_inches);
        weightSpinner = view.findViewById(R.id.fg_weight_select);
        ageSpinner = view.findViewById(R.id.fg_age_select);
        activityRadioGroup = view.findViewById(R.id.fg_activity_level_radio_group);
        weightGoalRadioGroup = view.findViewById(R.id.fg_weight_goal_radio_group);
        poundsPerWeekSpinner = view.findViewById(R.id.fg_pounds_per_week_spinner);
        activeRadioButton = view.findViewById(R.id.radio_active);
        sedentaryRadioButton = view.findViewById(R.id.radio_sedentary);
        loseRadioButton = view.findViewById(R.id.radio_lose);
        gainRadioButton = view.findViewById(R.id.radio_gain);
        maintainRadioButton = view.findViewById(R.id.radio_maintain);

        // Set up gender spinner
        List<String> genders = new ArrayList<>();
        genders.add(0, "Select a sex");
        genders.add("Male");
        genders.add("Female");
        ArrayAdapter<String> genderAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, genders);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(genderAdapter);

        // Set up feet spinner
        List<String> feet = new ArrayList<>();
        feet.add(0, "(feet)");
        feet.add("4");
        feet.add("5");
        feet.add("6");
        feet.add("7");
        ArrayAdapter<String> feetAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, feet);
        feetAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        feetSpinner.setAdapter(feetAdapter);

        // Set up inches spinner
        List<String> inches = new ArrayList<>();
        inches.add(0, "(inches)");
        for (int i = 1; i <= 12; i++)
            inches.add(i + "");
        ArrayAdapter<String> inchesAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, inches);
        inchesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        inchesSpinner.setAdapter(inchesAdapter);

        // Set up weight spinner
        List<String> weights = new ArrayList<>();
        weights.add(0, "(lbs)");
        for (int i = 45; i <= 600; i++)
            weights.add(i + "");
        ArrayAdapter<String> weightAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, weights);
        weightAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        weightSpinner.setAdapter(weightAdapter);

        // Set up age spinner
        List<String> ages = new ArrayList<>();
        ages.add(0, "(years)");
        for (int i = 18; i <= 100; i++)
            ages.add(i + "");
        ArrayAdapter<String> ageAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, ages);
        ageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ageSpinner.setAdapter(ageAdapter);

        // Set up pounds per week spinner
        List<String> pounds = new ArrayList<>();
        pounds.add(0, "(lbs)");
        pounds.add("0");
        pounds.add("1");
        pounds.add("2");
        pounds.add("3");
        pounds.add("4");
        pounds.add("5");
        ArrayAdapter<String> poundsAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, pounds);
        poundsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        poundsPerWeekSpinner.setAdapter(poundsAdapter);

        if (profileData.gender != null) {
            int pos = genderAdapter.getPosition(profileData.gender);
            genderSpinner.setSelection(pos);
        }
        if (profileData.heightInches != null) {
            int pos = inchesAdapter.getPosition(profileData.heightInches);
            inchesSpinner.setSelection(pos);
        }
        if (profileData.heightFeet != null) {
            int pos = feetAdapter.getPosition(profileData.heightFeet);
            feetSpinner.setSelection(pos);
        }
        if (profileData.weight != null) {
            int pos = weightAdapter.getPosition(profileData.weight);
            weightSpinner.setSelection(pos);
        }
        if (profileData.age != null) {
            int pos = ageAdapter.getPosition(profileData.age);
            ageSpinner.setSelection(pos);
        }
        if (profileData.poundsPerWeek != null) {
            int pos = poundsAdapter.getPosition(profileData.poundsPerWeek);
            poundsPerWeekSpinner.setSelection(pos);
        }
        if (profileData.activityLevel != null) {
            if (profileData.activityLevel.equals("Sedentary"))
                sedentaryRadioButton.setChecked(true);
            else if (profileData.activityLevel.equals("Active"))
                activeRadioButton.setChecked(true);
        }
        if (profileData.weightGoal != null) {
            if (profileData.weightGoal.equals("Gain"))
                gainRadioButton.setChecked(true);
            else if (profileData.weightGoal.equals("Lose"))
                loseRadioButton.setChecked(true);
            else if (profileData.weightGoal.equals("Maintain"))
                maintainRadioButton.setChecked(true);
        }
    }

    /**
     * Replaces the current fragment to the main dashboard fragment.
     */
    private void BackToDashboard() {
        DashboardMainFragment dashboardMainFragment = new DashboardMainFragment();
        FragmentTransaction fTrans = getParentFragmentManager().beginTransaction();
        fTrans.replace(R.id.fl_frag_dashboard, dashboardMainFragment, "frag_dashboard");
        fTrans.commit();
    }
}
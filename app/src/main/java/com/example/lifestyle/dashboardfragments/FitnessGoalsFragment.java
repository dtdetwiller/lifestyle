package com.example.lifestyle.dashboardfragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.example.lifestyle.R;
import com.example.lifestyle.MainActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FitnessGoalsFragment extends Fragment {

    private double BMR;
    private int calories;
    private String gender;
    private String heightFeet;
    private String heightInches;
    private String weight;
    private String age;
    private String activityLevel;
    private String weightGoal;
    private String poundsPerWeek;

    private Button submitButton;
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

        SetUpForm(view);

        submitButton = view.findViewById(R.id.fg_submit_button);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gender = genderSpinner.getSelectedItem().toString();
                heightFeet = feetSpinner.getSelectedItem().toString();
                heightInches = inchesSpinner.getSelectedItem().toString();
                weight = weightSpinner.getSelectedItem().toString();
                age = ageSpinner.getSelectedItem().toString();
                poundsPerWeek = poundsPerWeekSpinner.getSelectedItem().toString();

                int checkedID = activityRadioGroup.getCheckedRadioButtonId();
                RadioButton activityRadioButton = view.findViewById(checkedID);
                activityLevel = activityRadioButton.getText().toString();

                checkedID = weightGoalRadioGroup.getCheckedRadioButtonId();
                RadioButton weightGoalRadioButton = view.findViewById(checkedID);
                weightGoal = weightGoalRadioButton.getText().toString();

                SaveFile();

                //trying to get the submit button to go back to dashboard but it wont work
                //((MainActivity)getActivity()).fitnessGoalstoDashBoard();
            }
        });

    }

    private double CalculateBMR(int feet, int inches, int weight, int age) {
        double BMR = 0;

        return BMR;
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
        for (int i = 45; i <= 300; i++)
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
        ArrayAdapter<String> poundsAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, weights);
        poundsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        poundsPerWeekSpinner.setAdapter(poundsAdapter);

        ReadFile();

        if (gender != "") {
            int pos = genderAdapter.getPosition(gender);
            genderSpinner.setSelection(pos);
        }
        if (heightInches != "") {
            int pos = inchesAdapter.getPosition(heightInches);
            inchesSpinner.setSelection(pos);
        }
        if (heightFeet != "") {
            int pos = feetAdapter.getPosition(heightFeet);
            feetSpinner.setSelection(pos);
        }
        if (weight != "") {
            int pos = poundsAdapter.getPosition(weight);
            weightSpinner.setSelection(pos);
        }
        if (age != "") {
            int pos = ageAdapter.getPosition(age);
            ageSpinner.setSelection(pos);
        }
        if (activityLevel != "") {
            if (activityLevel == "Sedentary")
                sedentaryRadioButton.setChecked(true);
            else if (activityLevel == "Active")
                activeRadioButton.setChecked(true);
        }
        if (weightGoal != "") {
            if (weightGoal == "Gain")
                gainRadioButton.setChecked(true);
            else if (weightGoal == "Lose")
                loseRadioButton.setChecked(true);
            else if (weightGoal == "Maintain")
                maintainRadioButton.setChecked(true);
        }

    }

    /**
     * Saves all the fitness goal fields to a file called FitnessGoals
     */
    private void SaveFile() {
        File directory = getActivity().getFilesDir();
        try {
            File file = new File(directory, "FitnessGoals");
            //Toast.makeText(getActivity(), "doesn't exist", Toast.LENGTH_SHORT).show();
            FileOutputStream writer = new FileOutputStream(file);
            String fileString = "gender " + gender + "\n";
            fileString += "heightFeet " + heightFeet + "\n";
            fileString += "heightInches " + heightInches + "\n";
            fileString += "age " + age + "\n";
            fileString += "weight " + weight + "\n";
            fileString += "activityLevel " + activityLevel + "\n";
            fileString += "weightGoal " + weightGoal + "\n";
            fileString += "poundsPerWeek " + poundsPerWeek + "\n";

            writer.write(fileString.getBytes());
            writer.close();

        } catch (Exception e) {

        }
    }

    /**
     * Reads the FitnessGoals file and sets all the values.
     */
    private void ReadFile() {

        File nameFile = new File(getActivity().getFilesDir(), "FitnessGoals");

        if(nameFile.exists()) {
            try {
                Scanner scanner = new Scanner(nameFile);
                int i = 0;
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    String[] words = line.split(" ");
                    if(words[0].equals("gender"))
                        gender = words[1];
                    else if(words[0].equals("heightFeet"))
                        heightFeet = words[1];
                    else if(words[0].equals("heightInches"))
                        heightInches = words[1];
                    else if(words[0].equals("weight"))
                        weight = words[1];
                    else if(words[0].equals("age"))
                        age = words[1];
                    else if(words[0].equals("activityLevel"))
                        activityLevel = words[1];
                    else if(words[0].equals("poundsPerWeek"))
                        poundsPerWeek = words[1];
                    else if(words[0].equals("weightGoal"))
                        weightGoal = words[1];
                }
            } catch (Exception e) {

            }
        }
    }
}
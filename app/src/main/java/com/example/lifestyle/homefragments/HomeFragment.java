package com.example.lifestyle.homefragments;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresPermission;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lifestyle.R;

import java.io.File;
import java.util.Scanner;

public class HomeFragment extends Fragment {


    private ImageView imageView;
    private TextView welcomeText;
    private TextView fitnessGoalText;
    private TextView bmrText;
    private TextView caloriesNeededText;
    private TextView fitnessGoalWarning;
    private TextView calorieWarning;


    private String first_name = "";
    private String activityLevel = "";
    private int caloriesToEat;
    private String weightGoal = "";
    private String gender = "";
    private String weight = "";
    private String heightFeet = "";
    private String heightInches = "";
    private String age = "";
    private String poundsPerWeek = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ReadFile();

        imageView = (ImageView) view.findViewById(R.id.profile_image);
        File imageFile = new File(getActivity().getFilesDir(), "ProfileImage.png");

        if (imageFile.exists()) {
            try {
                imageView.setImageDrawable(Drawable.createFromPath(imageFile.toString()));

            } catch (Exception e) {

            }
        }

        welcomeText = getView().findViewById(R.id.welcome_text);
        welcomeText.setText("Welcome " + first_name);

        fitnessGoalText = view.findViewById(R.id.h_fitness_goal_text);
        bmrText = view.findViewById(R.id.h_BMR_text);
        caloriesNeededText = view.findViewById(R.id.h_calories_text);
        fitnessGoalWarning = view.findViewById(R.id.warning_fitness_goals);
        calorieWarning = view.findViewById(R.id.warning_calories);

        DisplayGoals();
    }

    /**
     * This method displays the users fitness goals on the homepage.
     */
    public void DisplayGoals() {

        double BMR = CalculateBMR();
        CalculateCalories(BMR);
        UpdateFitnessGoal();

        bmrText.setText("BMR: " + BMR);

        if (gender.equals("Male") && caloriesToEat < 1200) {
            calorieWarning.setText("(Eating less than 1200cals/day is dangerous)");
            calorieWarning.setVisibility(View.VISIBLE);
        }
        else if (gender.equals("Female") && caloriesToEat < 1000) {
            calorieWarning.setText("(Eating less than 1000cals/day is dangerous)");
            calorieWarning.setVisibility(View.VISIBLE);
        }
        else{
            calorieWarning.setVisibility(View.INVISIBLE);
        }

        caloriesNeededText.setText("Calories Needed to Meet Goal: " + caloriesToEat + " per day");
    }

    /**
     * Updates the fitness goal on the homepage.
     */
    private void UpdateFitnessGoal() {
        weightGoal = "";
        poundsPerWeek = "";

        ReadFile();;

        if (!weightGoal.equals("") && !poundsPerWeek.equals("")) {
            if (weightGoal.equals("Maintain")) {
                String goal = "Fitness Goal: Maintain current weight";
                fitnessGoalText.setText(goal);
            }
            else {
                String goal = "Fitness Goal: " + weightGoal + " " + poundsPerWeek + "lbs/week";
                fitnessGoalText.setText(goal);
            }
        }
        else {
            String goal = "Fitness Goal: Update your fitness goals on the dashboard.";
            fitnessGoalText.setText(goal);
        }

        if (Integer.valueOf(poundsPerWeek) > 2)
            fitnessGoalWarning.setVisibility(View.VISIBLE);
        else
            fitnessGoalWarning.setVisibility(View.INVISIBLE);

    }

    /**
     * This method calculates the calories the user needs to meet their goal.
     */
    private void CalculateCalories(double BMR) {
        int pounds = Integer.valueOf(poundsPerWeek);

        if (activityLevel.equals("Sedentary")) {
            caloriesToEat = (int)Math.round(BMR * 1.2);
        }
        else if (activityLevel.equals("Active")) {
            caloriesToEat = (int)Math.round(BMR * 1.55);
        }

        if (weightGoal.equals("Lose")) {
            int weeklyCalories = caloriesToEat * 7;
            int calorieDeficit = pounds * 3500;

            weeklyCalories = weeklyCalories - calorieDeficit;
            caloriesToEat = weeklyCalories / 7;
        }

        if (weightGoal.equals("Gain")) {
            int weeklyCalories = caloriesToEat * 7;
            int calorieSurplus = pounds * 3500;

            weeklyCalories = weeklyCalories + calorieSurplus;
            caloriesToEat = weeklyCalories / 7;
        }
    }

    /**
     * This method calculates the users BMR
     * @return
     */
    public double CalculateBMR()
    {
        double bmr = 0.0;
        int totalInches = (Integer.valueOf(heightFeet) * 12) + Integer.valueOf(heightInches);
        int lbs = Integer.valueOf(weight);

        if(gender.equals("Male"))
            bmr = 66 + (6.3 * lbs) + (12.9 * totalInches) - (6.8 * Integer.valueOf(age));
        else if(gender.equals("Female"))
            bmr = 655 + (4.3 * lbs) + (4.7 * totalInches) - (4.7 * Integer.valueOf(age));
        return bmr;
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

            nameFile = new File(getActivity().getFilesDir(), "Profile");

            if(nameFile.exists()) {
                try {
                    Scanner scanner = new Scanner(nameFile);
                    int i = 0;
                    while (scanner.hasNextLine()) {
                        String line = scanner.nextLine();
                        String[] words = line.split(" ");
                        if(words[0].equals("first_name"))
                            first_name = words[1];
                        i++;
                    }
                } catch (Exception e) {

                }
            }
        }
    }
}

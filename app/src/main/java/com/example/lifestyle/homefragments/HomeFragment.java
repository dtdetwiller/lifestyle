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


    private Button mapsHikeButton;
    private ImageView imageView;
    private TextView welcomeText;
    private TextView calorieField;

    private String first_name = "";
    private String last_name = "";
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
        calorieField = getView().findViewById(R.id.caloriesToEatToLoseorGainWeight);
        welcomeText.setText("Welcome " + first_name + " " + last_name);

        DisplayGoals();
    }


    public void DisplayGoals() {

        caloriesToEat = 0;
        double BMR = CalculateBMR();
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

        calorieField.setText("Your BMR is: " + BMR +" According to your fitness goals you want to " + weightGoal +"." +
                "To do this you must eat " + caloriesToEat + " a week.");
    }

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
        }
    }
}

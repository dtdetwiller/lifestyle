package com.example.lifestyle.homefragments;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
    private String caloriestoEat = "";
    private String weightChange = "";
    private String gender = "";
    private String weight = "";
    private String height_feet = "";
    private String height_inches = "";
    private String age = "";

    private File profileFile;
    private File fitnessGoals;

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
        profileFile = new File(getActivity().getFilesDir(), "Profile");
        fitnessGoals = new File(getActivity().getFilesDir(), "FitnessGoals");
        readFilesNecessary();
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
        displayGoalsandCaloriestoEat();
        }


    public void displayGoalsandCaloriestoEat() {
        caloriestoEat = "1000";
        double BMR = calculateBMR();
        calorieField.setText("Your BMR is: " + BMR +" According to your fitness goals you want to " + weightChange +"." +
                "To do this you must eat " + caloriestoEat + " a week.");

    }

    public double calculateBMR()
    {
        double bmr = 0;
        double totalHeight = Integer.valueOf(height_feet)*12 + Integer.valueOf(height_inches);
        //male = 88.362 + (13.397 x weight in kg) + (4.799 x height in cm) – (5.677 x age in years)
        //female = 447.593 + (9.247 x weight in kg) + (3.098 x height in cm) – (4.330 x age in years)
        if(gender.equals("male"))
            bmr = 88.362 + (13.397 * (Integer.valueOf(weight)/2.205)) + (4.799 * (totalHeight*2.54)) - (5.677 *Integer.valueOf(age));
        else if(gender.equals("female"))
            bmr = 447.593 + (9.247 * (Integer.valueOf(weight)/2.205)) + (3.098 * (totalHeight*2.54)) - (4.330 *Integer.valueOf(age));
        return bmr;
    }

    public void readFilesNecessary() {
        if (profileFile.exists()) {
            try {
                Scanner scnr = new Scanner(profileFile);

                while (scnr.hasNextLine()) {
                    String line = scnr.nextLine();
                    String[] tokens = line.split(" ");

                    if (tokens[0].equals("first_name"))
                        first_name = tokens[1];
                    else if (tokens[0].equals("last_name"))
                        last_name = tokens[1];
                    else if(tokens[0].equals("gender"))
                        gender = tokens[1];
                    else if(tokens[0].equals("weight"))
                        weight = tokens[1];
                    else if(tokens[0].equals("height_feet"))
                        height_feet = tokens[1];
                    else if(tokens[0].equals("height_inches"))
                        height_inches = tokens[1];

                }
            } catch (Exception e) {
                System.out.println("There was an error trying to read the Profile file.");
            }
        }

        if (fitnessGoals.exists()) {
            try {
                Scanner scanner = new Scanner(fitnessGoals);
                int i = 0;
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    String[] words = line.split(" ");
                    if (words[0].equals("activityLevel"))
                        activityLevel = words[1];
                    else if (words[0].equals("weightGoal"))
                        weightChange = words[1];
                    else if(words[0].equals("age"))
                        age = words[1];
                }
            } catch (Exception e) {

            }
        }
    }
}

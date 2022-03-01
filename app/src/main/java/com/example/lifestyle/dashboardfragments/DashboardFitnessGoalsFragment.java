package com.example.lifestyle.dashboardfragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.lifestyle.R;

import java.io.File;
import java.util.Scanner;

public class DashboardFitnessGoalsFragment extends Fragment {

    private Button updateGoalsButton;
    private TextView fitnessGoalsText;

    private String weightGoal;
    private String poundsPerWeek;

    public DashboardFitnessGoalsFragment() {
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
        return inflater.inflate(R.layout.fragment_dashboard_fitness_goals, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        updateGoalsButton = view.findViewById(R.id.update_goals_button);
        fitnessGoalsText = view.findViewById(R.id.fitness_goals_text);

        UpdateFitnessGoalOnDashboard();

        updateGoalsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FitnessGoalsFragment fitnessGoalsFragment = new FitnessGoalsFragment();
                FragmentTransaction fTrans = getParentFragmentManager().beginTransaction();
                fTrans.replace(R.id.fl_frag_dashboard, fitnessGoalsFragment);
                fTrans.commit();
            }
        });
    }

    /**
     * Updates the fitness goal text on the dashboard.
     */
    private void UpdateFitnessGoalOnDashboard() {
        weightGoal = "";
        poundsPerWeek = "";

        ReadFile();;

        if (!weightGoal.equals("") && !poundsPerWeek.equals("")) {
            if (weightGoal.equals("Maintain")) {
                String goal = "Goal: Maintain current weight";
                fitnessGoalsText.setText(goal);
            }
            else {
                String goal = "Goal: " + weightGoal + " " + poundsPerWeek + "lbs/week";
                fitnessGoalsText.setText(goal);
            }
        }
        else {
            String goal = "Goal: Update your fitness goals";
            fitnessGoalsText.setText(goal);
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
                    if(words[0].equals("poundsPerWeek"))
                        poundsPerWeek = words[1];
                    else if(words[0].equals("weightGoal"))
                        weightGoal = words[1];
                }
            } catch (Exception e) {

            }
        }
    }
}
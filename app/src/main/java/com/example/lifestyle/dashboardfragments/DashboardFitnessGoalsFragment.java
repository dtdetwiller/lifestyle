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

import com.example.lifestyle.R;

public class DashboardFitnessGoalsFragment extends Fragment {

    private Button updateGoalsButton;

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

        updateGoalsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FitnessGoalsFragment fitnessGoalsFragment = new FitnessGoalsFragment();
                FragmentTransaction fTrans = getParentFragmentManager().beginTransaction();
                fTrans.replace(R.id.fl_frag_dashboard, fitnessGoalsFragment, "frag_fitness_goals");
                fTrans.commit();
            }
        });
    }
}
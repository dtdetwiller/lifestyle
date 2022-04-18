package com.example.lifestyle.signinfragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lifestyle.DashBoard;
import com.example.lifestyle.MainActivity;
import com.example.lifestyle.Profile;
import com.example.lifestyle.R;
import com.example.lifestyle.dashboardfragments.weather.DisplayWeatherFragment;
import com.example.lifestyle.homefragments.HomeFragment;
import com.example.lifestyle.model.ProfileViewModel;
import com.example.lifestyle.profilefragments.ProfileData;
import com.example.lifestyle.profilefragments.ProfileFragement;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Scanner;


public class SignInFragment extends Fragment {


    private Button submit_button;
    private EditText username_view;
    private String username;
    private ProfileViewModel profileViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_in, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        username_view = (EditText) getView().findViewById(R.id.username_text);

        username = readUsername();
        username_view.setText(username);

        submit_button = (Button) getView().findViewById(R.id.signInButton);
        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = username_view.getText().toString();
                if (username.matches("")) {
                    Toast.makeText(getActivity(), "Enter a username first!", Toast.LENGTH_SHORT).show();
                }
                else {
                    writeUsername();
                    ProfileData profileData = profileViewModel.readProfile(getActivity());
                    startActivity(new Intent(getActivity(), MainActivity.class));
                }
            }
        });

    }

    public void writeUsername(){
        File directory = getActivity().getFilesDir();
        try{
            File file  = new File(directory, "currentUser");
            FileOutputStream writer = new FileOutputStream(file);
            String fileString = username;
            writer.write(fileString.getBytes());
            writer.close();

        } catch(Exception e) {

        }
    }

    public String readUsername() {
        File directory = getActivity().getFilesDir();
        File userFile = new File(directory, "currentUser");
        if(userFile.exists()) {
            try{
                Scanner scanner = new Scanner(userFile);

                if(scanner.hasNext()) {
                    username = scanner.next();
                }

            } catch (Exception e) {

            }
        }
        return username;
    }
}
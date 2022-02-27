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
        imageView = (ImageView) view.findViewById(R.id.profile_image);
        File imageFile = new File(getActivity().getFilesDir(), "ProfileImage.png");

        if (imageFile.exists()) {
            try {
                imageView.setImageDrawable(Drawable.createFromPath(imageFile.toString()));

            } catch (Exception e) {

            }
        }

        welcomeText = getView().findViewById(R.id.welcome_text);
        File profileFile = new File(getActivity().getFilesDir(), "Profile");

        String first_name = "";
        String last_name = "";

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
                }
            } catch (Exception e) {
                System.out.println("There was an error tring to read the Profile file.");
            }

            welcomeText.setText("Welcome " + first_name + " " + last_name);
        }
    }
}
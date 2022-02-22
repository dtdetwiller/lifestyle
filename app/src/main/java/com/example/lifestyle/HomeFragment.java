package com.example.lifestyle;

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

import java.io.File;
import java.util.Scanner;

public class HomeFragment extends Fragment {


    private String first_name;
    private String last_name;
    private String gender;
    private String height_feet;
    private String height_inches;
    private String weight;
    private String city;
    private String country;

    private Button mapsHikeButton;
    private ImageView imageView;
    private TextView bmi_text;

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
        bmi_text = (TextView) view.findViewById(R.id.bmi_text);

        File imageFile = new File(getActivity().getFilesDir(), "ProfileImage.png");

        if (imageFile.exists()) {
            try {
                imageView.setImageDrawable(Drawable.createFromPath(imageFile.toString()));

            } catch (Exception e) {

            }
        }

        readFile();

        if(weight != "" && height_feet != "" && height_inches != "") {
            int height = Integer.parseInt(height_feet) * 12 + Integer.parseInt(height_inches);
            height = height * height;
            int bmi = Integer.parseInt(weight) / height;
            bmi = bmi * 705;
            bmi_text.setText("BMI: " + bmi);
        }
    }

    private void readFile() {
        File nameFile = new File(getActivity().getFilesDir(), "ProfileName");

        if(nameFile.exists()) {
            try {
                Scanner scanner = new Scanner(nameFile);
                int i = 0;
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    String[] words = line.split(" ");
                    if(words[0].equals("first_name"))
                        first_name = words[1];
                    else if(words[0].equals("last_name"))
                        last_name = words[1];
                    else if(words[0].equals("gender"))
                        gender = words[1];
                    else if(words[0].equals("height_feet"))
                        height_feet = words[1];
                    else if(words[0].equals("height_inches"))
                        height_inches = words[1];
                    else if(words[0].equals("weight"))
                        weight = words[1];
                    else if(words[0].equals("city"))
                        city = words[1];
                    else if(words[0].equals("country"))
                        country = words[1];

                    i++;
                }
            } catch (Exception e) {

            }
        }
    }

}
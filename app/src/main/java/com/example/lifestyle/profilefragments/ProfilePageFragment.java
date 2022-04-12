package com.example.lifestyle.profilefragments;

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

import com.example.lifestyle.Profile;
import com.example.lifestyle.R;
import com.example.lifestyle.model.profileViewModel;

import java.io.File;
import java.util.Scanner;


public class ProfilePageFragment extends Fragment {

    private String username;
    private profileData profile;
    private profileViewModel profileViewModel;


    private ImageView profile_picture;
    private TextView name_view;
    private TextView body_view;
    private TextView location_view;

    private Button edit_profile;

    private String first_name;
    private String last_name;
    private String gender;
    private String height_feet;
    private String height_inches;
    private String weight;
    private String city;
    private String country;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        profileViewModel = new profileViewModel(this.getActivity().getApplication());
        profile = profileViewModel.readProfile(this.getActivity());


        profile_picture = (ImageView) view.findViewById(R.id.profile_image);
        File imageFile = new File(getActivity().getFilesDir(), "ProfileImage.png");

        if (imageFile.exists()) {
            try {
                profile_picture.setImageDrawable(Drawable.createFromPath(imageFile.toString()));

            } catch (Exception e)  {

            }
        }

        readFile();

        edit_profile = (Button) view.findViewById(R.id.edit_profile);

        name_view = (TextView) view.findViewById(R.id.name_view);
        if(first_name != null) {
            name_view.setText(first_name + " " + last_name + " (" + gender + ")");
        }
        else{
            name_view.setText("Not Signed In");
            edit_profile.setText("Create Profile");
        }
        body_view = (TextView) view.findViewById(R.id.body_view);
        if(height_feet != null) {
            body_view.setText(height_feet + "'" + height_inches + " ");
        }
        location_view = (TextView) view.findViewById(R.id.location);
        if(city != null) {
            location_view.setText(city);
        }



        edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Profile) getActivity()).pageToProfile();
            }
        });

    }

    private void readFile() {

        File nameFile = new File(getActivity().getFilesDir(), "Profile");

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
                    else if(words[0].equals("city")) {
                        city = "";
                        for (int j = 1; j < words.length; j++)
                            city = city + " " + words[j];
                    }
                    else if(words[0].equals("country")) {
                        country = "";
                        for (int j = 1; j < words.length; j++)
                            country = country + " " + words[j];
                    }
                    i++;
                }
            } catch (Exception e) {

            }
        }
    }
}
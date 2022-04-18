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
import com.example.lifestyle.model.ProfileViewModel;

import java.io.File;
import java.util.Scanner;


public class ProfilePageFragment extends Fragment {

    private String username;
    private ProfileData profile;
    private ProfileViewModel profileViewModel;

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

        profileViewModel = new ProfileViewModel(this.getActivity().getApplication());
        profile = profileViewModel.readProfile(this.getActivity());


        profile_picture = (ImageView) view.findViewById(R.id.profile_image);
        File imageFile = new File(getActivity().getFilesDir(), "ProfileImage.png");

        if (imageFile.exists()) {
            try {
                profile_picture.setImageDrawable(Drawable.createFromPath(imageFile.toString()));

            } catch (Exception e)  {

            }
        }

        edit_profile = (Button) view.findViewById(R.id.edit_profile);

        name_view = (TextView) view.findViewById(R.id.name_view);

        profile = profileViewModel.readProfile(getActivity());

        if(profile.firstName != null) {
            name_view.setText(profile.firstName + " " + profile.lastName + " (" + profile.gender + ")");
        }
        else{
            name_view.setText("Not Signed In");
            edit_profile.setText("Create Profile");
        }
        body_view = (TextView) view.findViewById(R.id.body_view);
        if(profile.heightFeet != null) {
            body_view.setText(profile.heightFeet + "'" + profile.heightInches + " ");
        }
        location_view = (TextView) view.findViewById(R.id.location);
        if(profile.city != null) {
            location_view.setText(profile.city);
        }

        edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Profile) getActivity()).pageToProfile();
            }
        });

    }


}
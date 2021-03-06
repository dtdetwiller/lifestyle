package com.example.lifestyle.profilefragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.lifestyle.Profile;
import com.example.lifestyle.R;
import com.example.lifestyle.SignIn;
import com.example.lifestyle.model.ProfileViewModel;
import com.example.lifestyle.model.WeatherViewModel;
import com.example.lifestyle.signinfragments.SignInFragment;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;


public class ProfileFragment extends Fragment {

    private Button submit_button;
    private Button cancel_button;

    private Button picture_button;

    private static final int CAPTURE_IMAGE_REQUEST_CODE = 1888;

    Bitmap thumbnailImage;

    // The ImageView that holds the profile pic
    ImageView mIvPic;

    private EditText first_name_text;
    private EditText last_name_text;
    private Spinner gender_spinner;
    private Spinner height_feet_spinner;
    private Spinner height_inches_spinner;
    private Spinner weight_spinner;
    private EditText city_text;
    private EditText country_text;

    private ProfileViewModel profileViewModel;
    private WeatherViewModel weatherViewModel;

    private String username;
    private ProfileData profile;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        return inflater.inflate(R.layout.fragment_profile_fragement, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        weatherViewModel = new ViewModelProvider(this).get(WeatherViewModel.class);

        profile = profileViewModel.readProfile(this.getActivity());

        first_name_text = view.findViewById(R.id.first_name);
        last_name_text = view.findViewById(R.id.last_name);
        gender_spinner =  view.findViewById(R.id.gender_select);
        height_feet_spinner = view.findViewById(R.id.height_feet);
        height_inches_spinner = view.findViewById(R.id.height_inches);
        weight_spinner = view.findViewById(R.id.weight_select);
        city_text = view.findViewById(R.id.city);
        country_text = view.findViewById(R.id.country);

        //set up sex spinner
        List<String> sexes = new ArrayList<>();
        sexes.add(0, "Select a sex");
        sexes.add("Male");
        sexes.add("Female");
        sexes.add("Other");
        Spinner sexSelector = (Spinner) getView().findViewById(R.id.gender_select);
        ArrayAdapter<String> sex_adapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, sexes);
        sex_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sexSelector.setAdapter(sex_adapter);

        //set up feet spinner
        List<String> feet = new ArrayList<>();
        feet.add(0, "ft");
        feet.add("4");
        feet.add("5");
        feet.add("6");
        feet.add("7");
        Spinner height_feet_selector = (Spinner) getView().findViewById(R.id.height_feet);
        ArrayAdapter<String> feet_adapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, feet);
        feet_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        height_feet_selector.setAdapter(feet_adapter);

        //set up inches spinner
        List<String> inches = new ArrayList<>();
        inches.add(0, "in");
        for (int i = 1; i <= 12; i++)
            inches.add(i + "");
        Spinner height_inches_selector = (Spinner) getView().findViewById(R.id.height_inches);
        ArrayAdapter<String> inches_adapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, inches);
        inches_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        height_inches_selector.setAdapter(inches_adapter);

        //set up weight spinner
        List<String> weightArr = new ArrayList<>();
        weightArr.add(0, "lbs");
        for (int i = 45; i <= 300; i++)
            weightArr.add(i + "");
        Spinner weight_selector = (Spinner) getView().findViewById(R.id.weight_select);
        ArrayAdapter<String> weight_adapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, weightArr);
        weight_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        weight_selector.setAdapter(weight_adapter);


        //readFile();

        if (profile.firstName != "")
            first_name_text.setText(profile.firstName);
        if (profile.lastName != "")
            last_name_text.setText(profile.lastName);
        if(profile.gender != "") {
            int sex_position = sex_adapter.getPosition(profile.gender);
            gender_spinner.setSelection(sex_position);
        }
        if(profile.heightFeet != "") {
            int feet_position = feet_adapter.getPosition(profile.heightFeet);
            height_feet_spinner.setSelection(feet_position);
        }
        if(profile.heightInches != "") {
            int inches_position = inches_adapter.getPosition(profile.heightInches);
            height_inches_spinner.setSelection(inches_position);
        }
        if(profile.weight != "") {
            int weight_postion = weight_adapter.getPosition(profile.weight);
            weight_spinner.setSelection(weight_postion);
        }
        if(profile.city != "") {
            city_text.setText(profile.city);
        }
        if(profile.country != "") {
            country_text.setText(profile.country);
        }

        submit_button = (Button) getView().findViewById(R.id.submit_button);
        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String first_name = first_name_text.getText().toString().trim();
                String last_name = last_name_text.getText().toString().trim();
                String gender = gender_spinner.getSelectedItem().toString().trim();
                String height_feet = height_feet_spinner.getSelectedItem().toString().trim();
                String height_inches = height_inches_spinner.getSelectedItem().toString().trim();
                String weight = weight_spinner.getSelectedItem().toString();
                String city = city_text.getText().toString().trim();
                String country = country_text.getText().toString().trim();

                if (first_name.matches("")) {

                    Toast.makeText(getActivity(), "Enter a first name first!", Toast.LENGTH_SHORT).show();
                }
                else if(last_name.matches("")) {

                    Toast.makeText(getActivity(), "Enter a last name first!", Toast.LENGTH_SHORT).show();
                }
                else {

                    String username = readUsername();
                    ProfileData profileData = new ProfileData(username);
                    profileData.firstName = first_name;
                    profileData.lastName = last_name;
                    profileData.gender = gender;
                    profileData.heightInches = height_inches;
                    profileData.heightFeet = height_feet;
                    profileData.weight = weight;
                    profileData.city = city;
                    profileData.country = country;
                    profileViewModel.writeProfile(profileData);

                    ((Profile) getActivity()).profileToPage();
                }



            }
        });

        cancel_button = (Button) getView().findViewById(R.id.cancel_button);
        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Profile) getActivity()).profileToPage();
            }
        });



        picture_button = (Button) getView().findViewById(R.id.take_picture);
        picture_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAPTURE_IMAGE_REQUEST_CODE);
            }
        });

        ImageView profilePic = (ImageView) view.findViewById(R.id.iv_pic);
        File imageFile = new File(getActivity().getFilesDir(), "ProfileImage.png");

        if (imageFile.exists()) {
            try {
                profilePic.setImageDrawable(Drawable.createFromPath(imageFile.toString()));

            } catch (Exception e) {

            }
        }


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // Gets the thumbnail of the image
            Bitmap bmp = (Bitmap) data.getExtras().get("data");
            thumbnailImage = bmp;

            // Puts the thumbnail on the ImageView
            mIvPic = (ImageView) getView().findViewById(R.id.iv_pic);
            mIvPic.setImageBitmap(thumbnailImage);

            //Open file and write to it
            if (isExternalStorageWritable()) {
                String filePathStr = saveImage(thumbnailImage);
            } else {
                Toast.makeText(getActivity(), "External storage not writable.", Toast.LENGTH_SHORT).show();
            }

        }
    }

    private String saveImage(Bitmap finalBitmap) {
        String root = Environment.getExternalStorageDirectory().toString();
        File dir = getActivity().getFilesDir();
        dir.mkdirs();

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        // String fileName = "Thumbnail_" + timeStamp + ".jpg";

        File file = new File(dir, "ProfileImage.png");
        if (file.exists()) file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
            Toast.makeText(getActivity(), "Picture saved!", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return file.getAbsolutePath();
    }

    private boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }

        return false;
    }


    private String readUsername () {

        String username = "";
        File directory = getActivity().getFilesDir();
        File userFile = new File(directory, "currentUser");
        if (userFile.exists()) {
            try {
                Scanner scanner = new Scanner(userFile);

                if (scanner.hasNext()) {
                    username = scanner.next();
                }

            } catch (Exception e) {

            }
        }
        return username;
    }
}
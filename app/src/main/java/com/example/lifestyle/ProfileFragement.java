package com.example.lifestyle;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

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

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class ProfileFragement extends Fragment {


    private String first_name;
    private String last_name;
    private String gender;
    private String height_feet;
    private String height_inches;
    private String weight;
    private String location;

    private Button submit_button;
    private Button picture_button;

    private static final int CAPTURE_IMAGE_REQUEST_CODE = 1888;

    Bitmap thumbnailImage;

    String[] sexes = {"Male", "Female", "Other"};

    // The ImageView that holds the profile pic
    ImageView mIvPic;


    private EditText first_name_text;
    private EditText last_name_text;
    private Spinner gender_spinner;
    private Spinner height_feet_spinner;
    private Spinner height_inches_spinner;
    private Spinner weight_spinner;
    private EditText location_text;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile_fragement, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        first_name_text = (EditText) view.findViewById(R.id.first_name);
        last_name_text = (EditText) view.findViewById(R.id.last_name);
        gender_spinner = (Spinner) view.findViewById(R.id.gender_select);
        height_feet_spinner = (Spinner) view.findViewById(R.id.height_feet);
        height_inches_spinner = (Spinner) view.findViewById(R.id.height_inches);
        weight_spinner = (Spinner) view.findViewById(R.id.weight_select);
        location_text = (EditText) view.findViewById(R.id.location);

        //set up sex spinner
        List<String> sexes = new ArrayList<>();
        sexes.add(0, "Select a sex");
        sexes.add("Male");
        sexes.add("Female");
        sexes.add("Other");
        Spinner sexSelector = (Spinner) getView().findViewById(R.id.gender_select);
        ArrayAdapter<String> adapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, sexes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sexSelector.setAdapter(adapter);

        //set up sex spinner
        List<String> feet = new ArrayList<>();
        feet.add(0, "feet");
        feet.add("4");
        feet.add("5");
        feet.add("6");
        feet.add("7");
        Spinner height_feet_selector = (Spinner) getView().findViewById(R.id.height_feet);
        ArrayAdapter<String> feet_adapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, feet);
        feet_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        height_feet_selector.setAdapter(feet_adapter);

        readFile();

        if(first_name != "") {
            first_name_text.setText(first_name);
        }
        if(last_name != "") {
            last_name_text.setText(last_name);
        }

        submit_button = (Button) getView().findViewById(R.id.submit_button);
        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                first_name = first_name_text.getText().toString();
                last_name = last_name_text.getText().toString();
                gender = gender_spinner.getSelectedItem().toString();
//                height_feet = height_feet_spinner.getSelectedItem().toString();
//                height_inches = height_inches_spinner.getSelectedItem().toString();
//                height_inches = height_inches_spinner.getSelectedItem().toString();
//                weight = weight_spinner.getSelectedItem().toString();
//                location = location_text.getText().toString();

                if (first_name.matches("")) {
                    Toast.makeText(getActivity(), "Enter a first name first!", Toast.LENGTH_SHORT).show();
                }
                else if(last_name.matches("")) {
                    Toast.makeText(getActivity(), "Enter a last name first!", Toast.LENGTH_SHORT).show();
                }
                else {
                    saveFile(first_name, last_name, gender, height_feet, height_inches, weight, location);
                }
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

    private void saveFile(String first_name, String last_name, String gender, String height_feet, String height_inches, String weight, String location) {
        File directory = getActivity().getFilesDir();
        try {
            File file = new File(directory, "ProfileName");
            //Toast.makeText(getActivity(), "doesn't exist", Toast.LENGTH_SHORT).show();
            FileOutputStream writer = new FileOutputStream(file);
            String fileString = "first_name " + first_name + "\n";
            fileString += "last_name " + last_name + "\n";
            fileString += "gender " + gender + "\n";
            fileString += "height_feet " + height_feet + "\n";
            fileString += "height_inches " + height_inches + "\n";
            fileString += "weight " + weight + "\n";
            fileString += "location " + location + "\n";

            writer.write(fileString.getBytes());
            writer.close();

        } catch (Exception e) {

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
                    else if(words[0].equals("location"))
                        location = words[1];

                    i++;
                }
            } catch (Exception e) {

            }
        }
    }



}
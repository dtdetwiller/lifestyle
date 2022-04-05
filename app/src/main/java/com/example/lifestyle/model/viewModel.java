package com.example.lifestyle.model;

import android.app.Activity;
import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.lifestyle.profilefragments.ProfileFragement;

import org.json.JSONObject;

import java.io.File;
import java.util.Scanner;

public class viewModel extends AndroidViewModel {

    public viewModel(@NonNull Application application) {
        super(application);
    }

    public profileModel readProfile(Activity activity) {



        String username = "";

        File directory = activity.getFilesDir();
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

        profileModel profile = new profileModel(username);

        return profile;
    }

    public void writeProfile(profileModel profile) {
        JSONObject j = profile.getProfileJSON();
    }

    public profileModel readProfile(String username) {
        JSONObject j = new JSONObject(); // this will be changed to pull the json object from server

        profileModel profile = new profileModel(username);
        try {
            profile.firstName = j.get("firstName").toString();
        }
        catch(Exception e) {

        }

        return profile;
    }




}

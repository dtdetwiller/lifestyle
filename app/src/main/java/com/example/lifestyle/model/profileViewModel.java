package com.example.lifestyle.model;

import android.app.Activity;
import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.lifestyle.profilefragments.profileData;

import org.json.JSONObject;

import java.io.File;
import java.util.Scanner;

public class profileViewModel extends AndroidViewModel {

    public profileViewModel(@NonNull Application application) {
        super(application);
    }

    public profileData readProfile(Activity activity) {

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

        profileData profile = new profileData(username);

        return profile;
    }

    public void writeProfile(profileData profile) {
        JSONObject j = profile.getProfileJSON();
    }

    public profileData readProfile(String username) {
        JSONObject j = new JSONObject(); // this will be changed to pull the json object from server

        profileData profile = new profileData(username);
        try {
            profile.firstName = j.get("firstName").toString();
        }
        catch(Exception e) {

        }

        return profile;
    }




}

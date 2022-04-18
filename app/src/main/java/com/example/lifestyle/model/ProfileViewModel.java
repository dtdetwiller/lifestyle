package com.example.lifestyle.model;

import android.app.Activity;
import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.lifestyle.Repository;
import com.example.lifestyle.profilefragments.ProfileData;

import org.json.JSONObject;

import java.io.File;
import java.util.Scanner;

public class ProfileViewModel extends AndroidViewModel {

    private Repository repository;

    public ProfileViewModel(@NonNull Application application) {
        super(application);
        repository = Repository.getInstance(application);
    }

    public ProfileData readProfile(Activity activity) {

        ProfileData profile = repository.readProfileData(activity);

        return profile;
    }

    public void writeProfile(ProfileData profile) {
        repository.insertProfileData(profile);
    }

    public void getAllUsers()
    {
        repository.getAllUsers();
    }

    public String readUsername(Activity activity) {

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
        return username;
    }




}

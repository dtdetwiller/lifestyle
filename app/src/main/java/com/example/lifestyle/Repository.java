package com.example.lifestyle;

import android.app.Activity;
import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.core.os.HandlerCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

// class imports
import com.example.lifestyle.dashboardfragments.weather.JSONWeatherUtility;
import com.example.lifestyle.dashboardfragments.weather.NetworkUtility;
import com.example.lifestyle.dashboardfragments.weather.WeatherDao;
import com.example.lifestyle.dashboardfragments.weather.WeatherData;
import com.example.lifestyle.dashboardfragments.weather.WeatherTable;
import com.example.lifestyle.dashboardfragments.weather.WeatherTableBuilder;
import com.example.lifestyle.profilefragments.ProfileDao;
import com.example.lifestyle.profilefragments.ProfileTable;
import com.example.lifestyle.profilefragments.ProfileData;

// json imports
import org.json.JSONException;

// java imports
import java.io.File;
import java.net.URL;
import java.util.Scanner;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// AWS Server imports
import com.amplifyframework.AmplifyException;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.storage.options.StorageDownloadFileOptions;
import com.amplifyframework.storage.s3.AWSS3StoragePlugin;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class Repository {
    private static Repository repoInstance;
    private final MutableLiveData<WeatherData> liveWeatherData = new MutableLiveData<WeatherData>();
    private String userLocation;
    private String jsonWeatherString;

    private WeatherDao weatherDao;
    private ProfileDao profileDao;
    private ProfileData profileData;

    Application lifestyleApplication;


    private Repository(Application application){
        AppDatabase db = AppDatabase.getDatabase(application);
        weatherDao = db.weatherDao();
        profileDao = db.profileDao();
        lifestyleApplication = application;


        //AWS stuff
        try {
            Amplify.addPlugin(new AWSCognitoAuthPlugin());
            Amplify.addPlugin(new AWSS3StoragePlugin());
            Amplify.configure(application.getApplicationContext());
            Log.i("MyAmplifyApp", "Initialized Amplify");

//            Amplify.Auth.signInWithWebUI(
//                    this,
//                    result -> Log.i("AuthQuickStart", result.toString()),
//                    error -> Log.e("AuthQuickStart", error.toString())
//            );
        } catch (AmplifyException error) {
            Log.e("MyAmplifyApp", "Could not initialize Amplify", error);
        }
    }

    // upload file to AWS server to back it up
    private void uploadFile(){
        String dataFileString = String.valueOf(lifestyleApplication.getApplicationContext().getDatabasePath("app.db"));
        String dataSHMFileString = String.valueOf(lifestyleApplication.getApplicationContext().getDatabasePath("app.db-shm"));
        String dataWALFileString = String.valueOf(lifestyleApplication.getApplicationContext().getDatabasePath("app.db-wal"));

        //System.out.println("Data fetched from AWS : " + dataFileString);
        Log.d("Data fetched" , dataFileString);
        // turn data into files
        File weatherdataFile = new File(dataFileString);
        File profiledataFile = new File(dataFileString);
//        File dataFileSHM = new File(dataSHMFileString);
//        File dataFileWAL = new File(dataWALFileString);

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(weatherdataFile));
            writer.write(jsonWeatherString);
            writer.close();
        } catch (Exception exception) {
            Log.e("MyAmplifyApp", "Upload failed", exception);
        }

        Amplify.Storage.uploadFile(
                "WeatherKey",
                weatherdataFile,
                result -> Log.i("MyAmplifyApp", "Successfully uploaded: " + result.getKey()),
                storageFailure -> Log.e("MyAmplifyApp", "Upload failed", storageFailure)
        );

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(profiledataFile));
            writer.append(profileData.getProfileJSON().toString());
            writer.close();
        } catch (Exception exception) {
            Log.e("MyAmplifyApp", "Upload failed", exception);
        }

        Amplify.Storage.uploadFile(
                "ProfileKey",
                profiledataFile,
                result -> Log.i("MyAmplifyApp", "Successfully uploaded: " + result.getKey()),
                storageFailure -> Log.e("MyAmplifyApp", "Upload failed", storageFailure)
        );
    }

    private void downloadWeatherFile(){
        Amplify.Storage.downloadFile(
                "WeatherKey",
                new File(lifestyleApplication.getApplicationContext().getFilesDir() + "/download.txt"),
                StorageDownloadFileOptions.defaultInstance(),
                progress -> Log.i("MyAmplifyApp", "Fraction completed: " + progress.getFractionCompleted()),
                result -> Log.i("MyAmplifyApp", "Successfully downloaded: " + result.getFile().getName()),
                error -> Log.e("MyAmplifyApp",  "Download Failure", error)
        );
    }

    private void downloadProfileFile(){
        Amplify.Storage.downloadFile(
                "ProfileKey",
                new File(lifestyleApplication.getApplicationContext().getFilesDir() + "/download.txt"),
                StorageDownloadFileOptions.defaultInstance(),
                progress -> Log.i("MyAmplifyApp", "Fraction completed: " + progress.getFractionCompleted()),
                result -> Log.i("MyAmplifyApp", "Successfully downloaded: " + result.getFile().getName()),
                error -> Log.e("MyAmplifyApp",  "Download Failure", error)
        );
    }

    public static synchronized Repository getInstance(Application application){
        if (repoInstance == null){
            repoInstance = new Repository(application);
        }

        return repoInstance;
    }

    public void setLocation(String location){
        userLocation = location;
        loadWeatherData();
        insertWeatherData();
    }

    public boolean updateWeatherData(){
        if (userLocation != null){
            loadWeatherData();

            if (jsonWeatherString != null){
                insertWeatherData();
            }

            else{
                return false;
            }

            return true;
        }

        return false;
    }

    private void insertWeatherData(){
        if(userLocation != null && jsonWeatherString != null){
            WeatherTable weatherTable = new WeatherTableBuilder().setLocation(userLocation).setWeatherJson(jsonWeatherString).createWeatherTable();
            AppDatabase.databaseExecutor.execute(() -> {
                weatherDao.insert(weatherTable);
                uploadFile();
            });
        }
    }

    public void insertProfileData(ProfileData profile){
        ProfileTable profileTable = new ProfileTable(profile.username, profile);
        AppDatabase.databaseExecutor.execute(() -> {
            profileDao.insert(profileTable);
            uploadFile();
        });

    }

    public ProfileData readProfileData(Activity activity)
    {
        String username = readUsername(activity);

        AppDatabase.databaseExecutor.execute(() -> {
            try{
                ProfileTable data = profileDao.readProfile(username);
                profileData = new ProfileData(username);

                if(data.firstName != null)
                    profileData.firstName = data.firstName;
                if(data.lastName != null)
                    profileData.lastName = data.lastName;
                if(data.gender != null)
                    profileData.gender = data.gender;
                if(data.heightFeet != null)
                    profileData.heightFeet = data.heightFeet;
                if(data.heightInches != null)
                    profileData.heightInches = data.heightInches;
                if(data.weight != null)
                    profileData.weight = data.weight;
                if(data.city != null)
                    profileData.city = data.city;
                if(data.country != null)
                    profileData.country = data.country;
                if(data.activityLevel != null)
                    profileData.activityLevel = data.activityLevel;
                if(data.caloriesToEat != null)
                    profileData.caloriesToEat = Integer.parseInt(data.caloriesToEat);
                if(data.weightGoal != null)
                    profileData.weightGoal = data.weightGoal;
                if(data.age != null)
                    profileData.age = data.age;
                if(data.poundsPerWeek != null)
                    profileData.poundsPerWeek = data.poundsPerWeek;
            }
            catch(Exception e) {

            }

        });

        return profileData;
    }

    public void getAllUsers()
    {
        profileDao.getAll();
    }

    public MutableLiveData<WeatherData> getWeatherData(){
        return liveWeatherData;
    }

    private void loadWeatherData(){
        new FetchWeatherTask().execute(userLocation);
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

    private class FetchWeatherTask{
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Handler mainThreadHandler = HandlerCompat.createAsync(Looper.getMainLooper());

        public void execute(String location){
            executorService.execute(() -> {
                String jsonWeatherData;
                URL weatherDataURL = NetworkUtility.buildURLFromString(location);
                jsonWeatherData = null;

                try{
                    jsonWeatherData = NetworkUtility.getDataFromURL(weatherDataURL);

                    if(jsonWeatherData != null) {
                        jsonWeatherString = jsonWeatherData;
                        postWeatherToMainThread(jsonWeatherData);
                    }

                    else{

                    }


                } catch (Exception e) {

                    e.printStackTrace();
                }
            });
        }

        private void postWeatherToMainThread(String retrievedJsonData){
            mainThreadHandler.post(() -> {
                try{
                    liveWeatherData.setValue(JSONWeatherUtility.getWeatherData(retrievedJsonData));
                }
                catch (JSONException e){
                    e.printStackTrace();
                }
            });
        }


    }
}
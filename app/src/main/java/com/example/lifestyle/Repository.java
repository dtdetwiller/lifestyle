package com.example.lifestyle;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import androidx.core.os.HandlerCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.lifestyle.dashboardfragments.weather.JSONWeatherUtility;
import com.example.lifestyle.dashboardfragments.weather.NetworkUtility;
import com.example.lifestyle.dashboardfragments.weather.WeatherDao;
import com.example.lifestyle.dashboardfragments.weather.WeatherData;
import com.example.lifestyle.dashboardfragments.weather.WeatherTable;
import com.example.lifestyle.dashboardfragments.weather.WeatherTableBuilder;
import com.example.lifestyle.model.ProfileViewModel;
import com.example.lifestyle.profilefragments.ProfileDao;
import com.example.lifestyle.profilefragments.ProfileTable;
import com.example.lifestyle.profilefragments.ProfileData;

import org.json.JSONException;

import java.net.URL;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository {
    private static Repository repoInstance;
    private final MutableLiveData<WeatherData> liveWeatherData = new MutableLiveData<WeatherData>();
    private String userLocation;
    private String jsonWeatherString;
    private WeatherDao weatherDao;

    private ProfileDao profileDao;
    private ProfileData profileData;

    private Repository(Application application){
        AppDatabase db = AppDatabase.getDatabase(application);
        weatherDao = db.weatherDao();
        profileDao = db.profileDao();

        if (userLocation != null){
            loadWeatherData();
        }
    }

    public static synchronized Repository getInstance(Application application){
        if (repoInstance == null){
            repoInstance = new Repository(application);
        }

        return repoInstance;
    }

    public void setLocation(String location){
        userLocation = location;
        loadData();
        insertWeatherData();
    }

    private void loadData()
    {
        new FetchWeatherTask().execute(userLocation);
    }

    public void updateWeatherData(){
        if (userLocation != null){
            loadWeatherData();
        }
    }

    private void insertWeatherData(){
        if(userLocation != null && jsonWeatherString != null){
            WeatherTable weatherTable = new WeatherTableBuilder().setLocation(userLocation).setWeatherJson(jsonWeatherString).createWeatherTable();
            AppDatabase.databaseExecutor.execute(() -> {
                weatherDao.insert(weatherTable);
            });
        }
    }

    public void insertProfileData(ProfileData profile){
        ProfileTable profileTable = new ProfileTable(profile.username, profile);
        AppDatabase.databaseExecutor.execute(() -> {
            profileDao.insert(profileTable);
        });

    }

    public ProfileData readProfileData(String username)
    {

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

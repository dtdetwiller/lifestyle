package com.example.lifestyle;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import androidx.core.os.HandlerCompat;
import androidx.lifecycle.MutableLiveData;

import com.example.lifestyle.dashboardfragments.weather.JSONWeatherUtility;
import com.example.lifestyle.dashboardfragments.weather.NetworkUtility;
import com.example.lifestyle.dashboardfragments.weather.WeatherDao;
import com.example.lifestyle.dashboardfragments.weather.WeatherData;
import com.example.lifestyle.dashboardfragments.weather.WeatherTable;
import com.example.lifestyle.dashboardfragments.weather.WeatherTableBuilder;
import com.example.lifestyle.profilefragments.ProfileDao;
import com.example.lifestyle.profilefragments.ProfileTable;
import com.example.lifestyle.profilefragments.ProfileData;

import org.json.JSONException;

import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository {
    private static Repository repoInstance;
    private final MutableLiveData<WeatherData> liveWeatherData = new MutableLiveData<WeatherData>();
    private String userLocation;
    private String jsonWeatherString;
    private WeatherDao weatherDao;
    private ProfileDao profileDao;

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

    private void insertProfileData(ProfileData profile){
        ProfileTable profileTable = new ProfileTable(profile.username, new ProfileData(""));
        AppDatabase.databaseExecutor.execute(() -> {
            profileDao.insert(profileTable);
        });

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
                        insertWeatherData();
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

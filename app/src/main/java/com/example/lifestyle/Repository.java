package com.example.lifestyle;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

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
import com.example.lifestyle.profilefragments.profileData;

import org.json.JSONException;

import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository {
    private static Repository instance;
    private final MutableLiveData<WeatherData> jsonData= new MutableLiveData<WeatherData>();
    // private String mLocation = "Salt&Lake&City,us";
    private String mLocation;
    private String mJsonString;
    private WeatherDao mWeatherDao;
    private ProfileDao mProfileDao;

    private Repository(Application application){
        AppDatabase db = AppDatabase.getDatabase(application);
        mWeatherDao = db.weatherDao();
        mProfileDao = db.profileDao();

        if (mLocation != null){
            loadData();
        }
    }

    public static synchronized Repository getInstance(Application application){
        if (instance == null){
            instance = new Repository(application);
        }

        return instance;
    }

    public void setLocation(String location){
        mLocation = location;
        loadData();
    }

    private void insertWeather(){
        if(mLocation != null && mJsonString != null){
            WeatherTable weatherTable = new WeatherTableBuilder().setLocation(mLocation).setWeatherJson(mJsonString).createWeatherTable();
            AppDatabase.databaseExecutor.execute(() -> {
                mWeatherDao.insert(weatherTable);
            });
        }
    }

    private void insertProfile(profileData profile){
        ProfileTable profileTable = new ProfileTable(profile.username, new profileData(""));
        AppDatabase.databaseExecutor.execute(() -> {
            mProfileDao.insert(profileTable);
        });

    }

    public MutableLiveData<WeatherData> getData(){
        return jsonData;
    }

    private void loadData(){
        new FetchWeatherTask().execute(mLocation);
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
                        mJsonString = jsonWeatherData;
                        insertWeather();
                        postToMainThread(jsonWeatherData);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

        private void postToMainThread(String retrievedJsonData){
            mainThreadHandler.post(() -> {
                try{
                    jsonData.setValue(JSONWeatherUtility.getWeatherData(retrievedJsonData));
                }
                catch (JSONException e){
                    e.printStackTrace();
                }
            });
        }
    }
}

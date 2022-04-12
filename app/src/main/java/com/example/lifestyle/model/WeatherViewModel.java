package com.example.lifestyle.model;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.lifestyle.WeatherRepository;
import com.example.lifestyle.dashboardfragments.weather.WeatherData;

public class WeatherViewModel extends AndroidViewModel {
    private MutableLiveData<WeatherData> jsonData;
    private WeatherRepository mWeatherRepository;

    public WeatherViewModel(Application application){
        super(application);
        mWeatherRepository = WeatherRepository.getInstance(application);
        jsonData = mWeatherRepository.getData();
    }

    public void setLocation(String location){
        Log.i("LoadWeatherData", "ViewModel Firing");
        mWeatherRepository.setLocation(location);
    }

    public LiveData<WeatherData> getData(){
        return jsonData;
    }
}

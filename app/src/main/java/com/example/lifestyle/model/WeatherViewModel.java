package com.example.lifestyle.model;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.lifestyle.Repository;
import com.example.lifestyle.dashboardfragments.weather.WeatherData;

public class WeatherViewModel extends AndroidViewModel {
    private MutableLiveData<WeatherData> jsonWeatherData;
    private Repository repository;

    public WeatherViewModel(Application application){
        super(application);
        repository = Repository.getInstance(application);
        jsonWeatherData = repository.getWeatherData();
    }

    public void setLocation(String location){
        repository.setLocation(location);
    }

    public LiveData<WeatherData> getWeatherData(){
        return jsonWeatherData;
    }

    public void updateWeatherData(){
        repository.updateWeatherData();
    }
}

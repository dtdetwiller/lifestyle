package com.example.lifestyle.model;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.lifestyle.Repository;
import com.example.lifestyle.dashboardfragments.weather.WeatherData;

public class WeatherViewModel extends AndroidViewModel {
    private MutableLiveData<WeatherData> jsonData;
    private Repository mRepository;

    public WeatherViewModel(Application application){
        super(application);
        mRepository = Repository.getInstance(application);
        jsonData = mRepository.getData();
    }

    public void setLocation(String location){
        mRepository.setLocation(location);
    }

    public LiveData<WeatherData> getData(){
        return jsonData;
    }
}

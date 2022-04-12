package com.example.lifestyle.dashboardfragments.weather;

import android.os.Bundle;
import com.example.lifestyle.dashboardfragments.weather.NetworkUtility;
import com.example.lifestyle.dashboardfragments.weather.JSONWeatherUtility;
import com.example.lifestyle.dashboardfragments.weather.WeatherData;
import com.example.lifestyle.dashboardfragments.weather.LocationData;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.os.HandlerCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lifestyle.R;
import com.example.lifestyle.model.WeatherViewModel;

import org.json.JSONException;

import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DisplayWeatherFragment extends Fragment {

    private TextView mTvTemp;
    private TextView mTvPress;
    private TextView mTvHum;

    public DisplayWeatherFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_display_weather, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mTvHum = getView().findViewById(R.id.HumidityView);
        mTvPress = getView().findViewById(R.id.PressureView);
        mTvTemp = getView().findViewById(R.id.TemperatureView);
    }

    public void receiveWeatherData(WeatherData weatherData){
        mTvTemp.setText("Temperature: " + Math.round(weatherData.getTemperature().getTemp() - 273.15) + " C");
        mTvHum.setText("Pressure: " + weatherData.getCurrentCondition().getHumidity() + "%");
        mTvPress.setText("Humidity: " + weatherData.getCurrentCondition().getPressure() + " hPA");
    }
}


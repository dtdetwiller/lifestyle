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
import com.example.lifestyle.model.ProfileViewModel;
import com.example.lifestyle.model.WeatherViewModel;
import com.example.lifestyle.profilefragments.ProfileData;

import org.json.JSONException;

import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DisplayWeatherFragment extends Fragment {

    private TextView mTvTemp;
    private TextView mTvPress;
    private TextView mTvHum;
    private TextView mTvTitle;
    private WeatherViewModel weatherViewModel;

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

        weatherViewModel = new ViewModelProvider(this).get(WeatherViewModel.class);
        (weatherViewModel.getWeatherData()).observe(getViewLifecycleOwner(), weatherObserver);

        mTvHum = getView().findViewById(R.id.HumidityView);
        mTvPress = getView().findViewById(R.id.PressureView);
        mTvTemp = getView().findViewById(R.id.TemperatureView);
        mTvTitle = getView().findViewById(R.id.WeatherTitle);


    }

    /***
     * Retrieves the weather data from weather data.
     * @param weatherData
     */
    public void receiveWeatherData(WeatherData weatherData){
        mTvTitle.setText("Weather in " + weatherData.getLocationData().getCity() + ", " + weatherData.getLocationData().getCountry());
        mTvTemp.setText("Temperature: " + Math.round(weatherData.getTemperature().getTemp() - 273.15) + " C");
        mTvHum.setText("Humidity: " + weatherData.getCurrentCondition().getHumidity() + "%");
        mTvPress.setText("Pressure: " + weatherData.getCurrentCondition().getPressure() + " hPA");
    }

    final Observer<WeatherData> weatherObserver = new Observer<WeatherData>() {
        @Override
        public void onChanged(@Nullable final WeatherData weatherData) {
            //update UI when new weather data is collected
            if (weatherData != null){
                receiveWeatherData(weatherData);
            }
        }
    };
}


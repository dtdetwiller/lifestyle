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

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lifestyle.R;

import org.json.JSONException;

import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DisplayWeatherFragment extends Fragment {

    private TextView mTvTemp;
    private TextView mTvPress;
    private TextView mTvHum;
    private WeatherData mWeatherData;

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
        loadWeatherData("marietta&,GA");
    }

    private void loadWeatherData(String location){
        new FetchWeatherTask().execute(location);
    }

    private class FetchWeatherTask{
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Handler mainThreadHandler = HandlerCompat.createAsync(Looper.getMainLooper());
        public void execute(String location)
        {
            executorService.execute(() -> {
                String jsonWeatherData;
                URL weatherDataURL = NetworkUtility.buildURLFromString(location);
                jsonWeatherData = null;
                try{
                    jsonWeatherData = NetworkUtility.getDataFromURL(weatherDataURL);
                    postToMainThread(jsonWeatherData);
                }catch(Exception e){
                    e.printStackTrace();
                }
            });
        }

        private void postToMainThread(String jsonWeatherData)
        {
            mainThreadHandler.post(() -> {
                if (jsonWeatherData != null) {
                    try {
                        mWeatherData = JSONWeatherUtility.getWeatherData(jsonWeatherData);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (mWeatherData != null) {
                        mTvTemp.setText("" + Math.round(mWeatherData.getTemperature().getTemp() - 273.15) + " C");
                        mTvHum.setText("" + mWeatherData.getCurrentCondition().getHumidity() + "%");
                        mTvPress.setText("" + mWeatherData.getCurrentCondition().getPressure() + " hPa");
                    }
                }
            });
        }
    }
}


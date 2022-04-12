package com.example.lifestyle.dashboardfragments.weather;

public class WeatherTableBuilder {
    private String location;
    private String weatherJson;

    public WeatherTableBuilder setWeatherJson(String weatherJson){
        this.weatherJson = weatherJson;
        return this;
    }

    public WeatherTableBuilder setLocation(String location){
        this.location = location;
        return this;
    }

    public WeatherTable createWeatherTable() {
        return new WeatherTable(location, weatherJson);
    }
}

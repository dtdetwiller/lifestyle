package com.example.lifestyle.dashboardfragments.weather;

public class LocationData {
    private double mLatitude;
    private double mLongitude;
    private String mCountry;
    private String mCity;

    private long mSunset;
    private long mSunrise;

    public double getLatitude() {
        return mLatitude;
    }
    public void setLatitude(double latitude) {
        mLatitude = latitude;
    }

    public double getLongitude() {
        return mLongitude;
    }
    public void setLongitude(double longitude) {
        mLongitude = longitude;
    }


    public long getSunset() {
        return mSunset;
    }
    public void setSunset(long sunset){
        mSunset = sunset;
    }
    public long getSunrise() {
        return mSunrise;
    }
    public void setSunrise(long sunrise) {
        mSunrise = sunrise;
    }
    public String getCountry() {
        return mCountry;
    }
    public void setCountry(String country) {
        mCountry = country;
    }
    public String getCity() {
        return mCity;
    }
    public void setCity(String city) {
        mCity = city;
    }
}

package com.example.lifestyle.dashboardfragments.weather;


public class WeatherData {
    private LocationData mLocationData;
    private CurrentCondition mCurrentCondition = new CurrentCondition();
    private Temperature mTemperature = new Temperature();
    private Wind mWind = new Wind();
    private Rain mRain = new Rain();
    private Snow mSnow = new Snow();
    private Clouds mClouds = new Clouds();


    public  class CurrentCondition {
        private long mWeatherId;
        private String mCondition;
        private String mDescr;
        private String mIcon;


        private double mPressure;
        private double mHumidity;

        public long getWeatherId() {
            return mWeatherId;
        }
        public void setWeatherId(long weatherId) {
            mWeatherId = weatherId;
        }
        public String getCondition() {
            return mCondition;
        }
        public void setCondition(String condition) {
            mCondition = condition;
        }
        public String getDescr() {
            return mDescr;
        }
        public void setDescr(String descr) {
            mDescr = descr;
        }
        public String getIcon() {
            return mIcon;
        }
        public void setIcon(String icon) {
            mIcon = icon;
        }
        public double getPressure() {
            return mPressure;
        }
        public void setPressure(double pressure) {
            mPressure = pressure;
        }
        public double getHumidity() {
            return mHumidity;
        }
        public void setHumidity(double humidity) {
            mHumidity = humidity;
        }
    }

    public class Temperature {
        private double mTemp;
        private double mMinTemp;
        private double mMaxTemp;

        public double getTemp() {
            return mTemp;
        }
        public void setTemp(double temp) {
            mTemp = temp;
        }
        public double getMinTemp() {
            return mMinTemp;
        }
        public void setMinTemp(double minTemp) {
            mMinTemp = minTemp;
        }
        public double getMaxTemp() {
            return mMaxTemp;
        }
        public void setMaxTemp(double maxTemp) {
            mMaxTemp = maxTemp;
        }

    }

    public class Wind {
        private double mSpeed;
        private double mDeg;
        public double getSpeed() {
            return mSpeed;
        }
        public void setSpeed(double speed) {
            mSpeed = speed;
        }
        public double getDeg() {
            return mDeg;
        }
        public void setDeg(double deg) {
            mDeg = deg;
        }
    }

    public class Rain {
        private String mTime;
        private double mAmount;
        public String getTime() {
            return mTime;
        }
        public void setTime(String time) {
            mTime = time;
        }
        public double getAmount() {
            return mAmount;
        }
        public void setAmount(double amount) {
            mAmount = amount;
        }
    }

    public class Snow {
        private String mTime;
        private double mAmount;
        public String getTime() {
            return mTime;
        }
        public void setTime(String time) {
            mTime = time;
        }
        public double getAmount() {
            return mAmount;
        }
        public void setAmount(double amount) {
            mAmount = amount;
        }
    }

    public class Clouds {
        private long mPerc;

        public long getPerc() {
            return mPerc;
        }

        public void setPerc(long perc) {
            mPerc = perc;
        }

    }

    //Setters and Getters
    public void setLocationData(LocationData locationData){
        mLocationData = locationData;
    }
    public LocationData getLocationData(){
        return mLocationData;
    }

    public void setCurrentCondition(CurrentCondition currentCondition){
        mCurrentCondition = currentCondition;
    }
    public CurrentCondition getCurrentCondition(){
        return mCurrentCondition;
    }

    public void setTemperature(Temperature temperature){
        mTemperature = temperature;
    }
    public Temperature getTemperature(){
        return mTemperature;
    }

    public void setWind(Wind wind){
        mWind = wind;
    }
    public Wind getWind(){
        return mWind;
    }

    public void setRain(Rain rain){
        mRain = rain;
    }
    public Rain getRain(){
        return mRain;
    }

    public void setSnow(Snow snow){
        mSnow = snow;
    }
    public Snow getSnow(){
        return mSnow;
    }

    public void setClouds(Clouds clouds){
        mClouds = clouds;
    }
    public Clouds getClouds(){
        return mClouds;
    }
}

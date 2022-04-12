package com.example.lifestyle;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.core.os.HandlerCompat;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.lifestyle.dashboardfragments.weather.WeatherDao;
import com.example.lifestyle.dashboardfragments.weather.WeatherTable;
import com.example.lifestyle.dashboardfragments.weather.WeatherTableBuilder;
import com.example.lifestyle.profilefragments.ProfileDao;
import com.example.lifestyle.profilefragments.ProfileTable;
import com.example.lifestyle.profilefragments.profileData;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {WeatherTable.class, ProfileTable.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static volatile AppDatabase mInstance;
    public abstract WeatherDao weatherDao();
    public abstract ProfileDao profileDao();

    static final ExecutorService databaseExecutor = Executors.newFixedThreadPool(4);

    static synchronized  AppDatabase getDatabase(final Context context){
        if(mInstance == null){
            mInstance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "app.db").addCallback(sRoomDatabaseCallback).build();
        }

        return mInstance;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db){
            super.onCreate(db);
            databaseExecutor.execute(() -> {
                WeatherDao weatherDao = mInstance.weatherDao();
                weatherDao.deleteAll();
                WeatherTable weatherTable = new WeatherTableBuilder().setLocation("dummy_loc").setWeatherJson("dummy_data").createWeatherTable();
                weatherDao.insert(weatherTable);
            });

            databaseExecutor.execute(() -> {
                ProfileDao profileDao = mInstance.profileDao();
                profileDao.deleteAll();
                ProfileTable profileTable = new ProfileTable("", new profileData(""));
                profileDao.insert(profileTable);
            });
        }
    };

    private static RoomDatabase.Callback getsRoomDatabaseCallback2 = new RoomDatabase.Callback(){
        @Override
                public void onOpen(@NonNull SupportSQLiteDatabase db){
            super.onCreate(db);
            new PopulateDbTask(mInstance).execute();
        }
    };

    private static class PopulateDbTask{
        private final WeatherDao mWeatherDao;
        private final ProfileDao mProfileDao;

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Handler mainThreadHandler = HandlerCompat.createAsync(Looper.getMainLooper());
        PopulateDbTask(AppDatabase db){
            mWeatherDao = db.weatherDao();
            mProfileDao = db.profileDao();
        }

        public void execute(){
            executorService.execute(new Runnable(){
                @Override
                public void run(){
                    mWeatherDao.deleteAll();
                    WeatherTable weatherTable = new WeatherTableBuilder().setLocation("dummy_loc").setWeatherJson("dummy_data").createWeatherTable();
                    mWeatherDao.insert(weatherTable);

                    mProfileDao.deleteAll();
                    ProfileTable profileTable = new ProfileTable("", new profileData(""));
                    mProfileDao.insert(profileTable);
                }
            });
        }
    }
}

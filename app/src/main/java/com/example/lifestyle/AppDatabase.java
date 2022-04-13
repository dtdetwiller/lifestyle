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
import com.example.lifestyle.profilefragments.ProfileData;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {WeatherTable.class, ProfileTable.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static volatile AppDatabase databaseInstance;
    public abstract WeatherDao weatherDao();
    public abstract ProfileDao profileDao();

    static final ExecutorService databaseExecutor = Executors.newFixedThreadPool(4);

    static synchronized  AppDatabase getDatabase(final Context context){
        if(databaseInstance == null){
            databaseInstance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "app.db").addCallback(sRoomDatabaseCallback).build();
        }

        return databaseInstance;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db){
            super.onCreate(db);
            databaseExecutor.execute(() -> {
                WeatherDao weatherDao = databaseInstance.weatherDao();
                weatherDao.deleteAll();
                WeatherTable weatherTable = new WeatherTableBuilder().setLocation("dummy_loc").setWeatherJson("dummy_data").createWeatherTable();
                weatherDao.insert(weatherTable);
            });

            databaseExecutor.execute(() -> {
                ProfileDao profileDao = databaseInstance.profileDao();
                profileDao.deleteAll();
                ProfileTable profileTable = new ProfileTable("", new ProfileData(""));
                profileDao.insert(profileTable);
            });
        }
    };

    private static RoomDatabase.Callback getsRoomDatabaseCallback2 = new RoomDatabase.Callback(){
        @Override
                public void onOpen(@NonNull SupportSQLiteDatabase db){
            super.onCreate(db);
            new PopulateDbTask(databaseInstance).execute();
        }
    };

    private static class PopulateDbTask{
        private final WeatherDao weatherDao;
        private final ProfileDao profileDao;

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Handler mainThreadHandler = HandlerCompat.createAsync(Looper.getMainLooper());
        PopulateDbTask(AppDatabase db){
            weatherDao = db.weatherDao();
            profileDao = db.profileDao();
        }

        public void execute(){
            executorService.execute(new Runnable(){
                @Override
                public void run(){
                    weatherDao.deleteAll();
                    WeatherTable weatherTable = new WeatherTableBuilder().setLocation("dummy_loc").setWeatherJson("dummy_data").createWeatherTable();
                    weatherDao.insert(weatherTable);

                    profileDao.deleteAll();
                    ProfileTable profileTable = new ProfileTable("", new ProfileData(""));
                    profileDao.insert(profileTable);
                }
            });
        }
    }
}

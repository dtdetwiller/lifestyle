package com.example.lifestyle.profilefragments;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.RawQuery;
import androidx.sqlite.db.SupportSQLiteQuery;

import com.example.lifestyle.dashboardfragments.weather.WeatherTable;

@Dao
public interface ProfileDao
{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ProfileTable profileTable);

    @Query("DELETE FROM profile_table")
    void deleteAll();

    @Query("SELECT * from profile_table WHERE username = :user")
    ProfileTable readProfile(String user);

    @Query("SELECT * from profile_table ORDER BY username ASC")
    LiveData<ProfileTable> getAll();

    @RawQuery
    int checkpoint(SupportSQLiteQuery supportSQLiteQuery);
}

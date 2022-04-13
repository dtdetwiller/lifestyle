package com.example.lifestyle.profilefragments;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "profile_table")
public class ProfileTable
{
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "username")
    public String username;

    @ColumnInfo(name = "firstName")
    public String firstName;

    @ColumnInfo(name = "lastName")
    public String lastName;

    @ColumnInfo(name = "gender")
    public String gender;

    @ColumnInfo(name = "heightFeet")
    public String heightFeet;

    @ColumnInfo(name = "heightInches")
    public String heightInches;

    @ColumnInfo(name = "weight")
    public String weight;

    @ColumnInfo(name = "city")
    public String city;

    @ColumnInfo(name = "country")
    public String country;

    @ColumnInfo(name = "activityLevel")
    public String activityLevel;

    @ColumnInfo(name = "caloriesToEat")
    public String caloriesToEat;

    @ColumnInfo(name = "weightGoal")
    public String weightGoal;

    @ColumnInfo(name = "age")
    public String age;

    @ColumnInfo(name = "poundsPerWeek")
    public String poundsPerWeek;

    public ProfileTable() {}

    public ProfileTable(@NonNull String username, ProfileData profileData){
        this.username = username;
    }
}

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

    public ProfileTable(@NonNull String username, String firstName, String lastName,
                        String gender, String heightFeet, String heightInches, String weight,
                        String city, String country, String activityLevel, String caloriesToEat,
                        String weightGoal, String age, String poundsPerWeek) {

        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.heightFeet = heightFeet;
        this.heightInches = heightInches;
        this.weight = weight;
        this.city = city;
        this.country = country;
        this.activityLevel = activityLevel;
        this.caloriesToEat = caloriesToEat;
        this.weightGoal = weightGoal;
        this.age = age;
        this.poundsPerWeek = poundsPerWeek;


    }

    public ProfileTable(@NonNull String username, ProfileData profileData){
        this.username = username;
        try{
            this.firstName = profileData.firstName;
            this.lastName = profileData.lastName;
            this.gender = profileData.gender;
            this.weight = profileData.weight;
            this.weightGoal = profileData.weightGoal;
            this.activityLevel = profileData.activityLevel;
            this.city = profileData.city;
            this.country = profileData.country;
            this.age = profileData.age;
            this.poundsPerWeek = profileData.poundsPerWeek;
            this.heightFeet = profileData.heightFeet;
            this.heightInches = profileData.heightInches;
        }
        catch (Exception e)
        {

        }
    }

    public String getUsername() {
        return this.username;
    }

    public String getFirstName() {
        return this.firstName;
    }
}

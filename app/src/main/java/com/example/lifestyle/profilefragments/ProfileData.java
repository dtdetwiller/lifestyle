package com.example.lifestyle.profilefragments;

import org.json.JSONObject;

import java.io.StringWriter;
import java.util.Map;

public class ProfileData {

    public String username;
    public String firstName;
    public String lastName;
    public String gender;
    public String heightFeet;
    public String heightInches;
    public String weight;
    public String city;
    public String country;
    public String activityLevel;
    public int caloriesToEat;
    public String weightGoal;
    public String age;
    public String poundsPerWeek;

    public ProfileData(String newUsername) {
        username = newUsername;
    }

    public JSONObject getProfileJSON() {

        JSONObject j = new JSONObject();
        //j.put("username", username);
        try {
            j.put("username", username);
            j.put("firstName", firstName);
            j.put("lastName",lastName);
            j.put("gender",gender);
            j.put("heightFeet",heightFeet);
            j.put("weight",weight);
            j.put("city",city);
            j.put("country",country);
            j.put("activityLevel",activityLevel);
            j.put("caloriesToEat",caloriesToEat);
            j.put("weightGoal",weightGoal);
            j.put("age",age);
            j.put("poundsPerWeek",poundsPerWeek);


        } catch (Exception e) {

        }

        return j;
    }





}
